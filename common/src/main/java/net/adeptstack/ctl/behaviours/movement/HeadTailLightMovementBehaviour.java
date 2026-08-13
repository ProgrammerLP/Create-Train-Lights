package net.adeptstack.ctl.behaviours.movement;

import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.elevator.ElevatorContraption;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.adeptstack.ctl.blocks.lights.HeadTailLightBlockBase;
import net.adeptstack.ctl.blocks.lights.LightBlockBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;

public class HeadTailLightMovementBehaviour implements MovementBehaviour {

    private static final Vec3 LOCAL_X_POSITIVE = new Vec3(1, 0, 0);
    private static final Vec3 LOCAL_X_NEGATIVE = new Vec3(-1, 0, 0);
    private static final Vec3 LOCAL_Z_POSITIVE = new Vec3(0, 0, 1);
    private static final Vec3 LOCAL_Z_NEGATIVE = new Vec3(0, 0, -1);

    @SuppressWarnings("D")
    @Override
    public void tick(MovementContext context) {
        StructureTemplate.StructureBlockInfo structureBlockInfo = context.contraption.getBlocks()
                .get(context.localPos);
        if (structureBlockInfo == null)
            return;

        boolean open = structureBlockInfo.state().getValue(LightBlockBase.LIT);
        boolean locked = structureBlockInfo.state().getValue(HeadTailLightBlockBase.IS_LOCKED);
        if (!context.world.isClientSide())
            tickLIT(context, open);

        if (!open && !locked) {
            return;
        }

        int ticksOpen = context.data.getInt("OpenTicks") + 1;
        if (ticksOpen <= 20) {
            context.data.putInt("OpenTicks", ticksOpen);
            return;
        }

        // Reset before running the update so that every early exit below keeps the
        // intended 20 tick interval instead of re-running this on every single tick.
        context.data.putInt("OpenTicks", 0);
        updateLightMode(context, locked);
    }

    private void updateLightMode(MovementContext context, boolean locked) {
        if (!(context.contraption.entity instanceof CarriageContraptionEntity cce)
                || !(context.contraption instanceof CarriageContraption cc))
            return;

        Direction assemblyDirection = cc.getAssemblyDirection();
        if (assemblyDirection == Direction.UP || assemblyDirection == Direction.DOWN)
            return;

        Vec3 now = cce.position();
        Vec3 last = cce.getPrevPositionVec();
        Vec3 motion = now.subtract(last);

        Direction direction = vecToDirection(motion);
        if (direction == null)
            return;
        if (Math.abs(motion.x) <= 0.0001 && (direction == Direction.EAST || direction == Direction.WEST))
            return;
        if (Math.abs(motion.z) <= 0.0001 && (direction == Direction.NORTH || direction == Direction.SOUTH))
            return;
        if (Math.abs(motion.y) <= 0.0005 && Math.abs(motion.y) != 0)
            return;

        BlockPos pos = context.localPos;
        int oldLightMode = context.state.getValue(HeadTailLightBlockBase.LIGHT_MODE);
        int newLightMode = oldLightMode;

        int localXZ;
        Vec3 value;
        if (assemblyDirection == Direction.EAST || assemblyDirection == Direction.WEST) {
            localXZ = pos.getX();
            value = cce.toGlobalVector(LOCAL_X_POSITIVE, 1f)
                    .subtract(cce.toGlobalVector(LOCAL_X_NEGATIVE, 1f));
        }
        else {
            localXZ = pos.getZ();
            value = cce.toGlobalVector(LOCAL_Z_POSITIVE, 1f)
                    .subtract(cce.toGlobalVector(LOCAL_Z_NEGATIVE, 1f));
        }

        if (direction == Direction.NORTH) {
            if (value.z > 0 && localXZ > 0) {
                newLightMode = 1;
            } else if (value.z > 0 && localXZ < 0) {
                newLightMode = 0;
            } else if (value.z < 0 && localXZ > 0) {
                newLightMode = 0;
            } else if (value.z < 0 && localXZ < 0) {
                newLightMode = 1;
            }
        } else if (direction == Direction.EAST) {
            if (value.x > 0 && localXZ > 0) {
                newLightMode = 0;
            } else if (value.x > 0 && localXZ < 0) {
                newLightMode = 1;
            } else if (value.x < 0 && localXZ > 0) {
                newLightMode = 1;
            } else if (value.x < 0 && localXZ < 0) {
                newLightMode = 0;
            }
        } else if (direction == Direction.SOUTH) {
            if (value.z > 0 && localXZ > 0) {
                newLightMode = 0;
            } else if (value.z > 0 && localXZ < 0) {
                newLightMode = 1;
            } else if (value.z < 0 && localXZ > 0) {
                newLightMode = 1;
            } else if (value.z < 0 && localXZ < 0) {
                newLightMode = 0;
            }
        } else if (direction == Direction.WEST) {
            if (value.x > 0 && localXZ > 0) {
                newLightMode = 1;
            } else if (value.x > 0 && localXZ < 0) {
                newLightMode = 0;
            } else if (value.x < 0 && localXZ > 0) {
                newLightMode = 0;
            } else if (value.x < 0 && localXZ < 0) {
                newLightMode = 1;
            }
        }

        BlockState newState = context.state.setValue(HeadTailLightBlockBase.LIGHT_MODE, newLightMode);

        if (locked) {
            if (newLightMode != oldLightMode) {
                newState = context.state.setValue(HeadTailLightBlockBase.LIGHT_MODE, oldLightMode)
                        .setValue(HeadTailLightBlockBase.LIT, false);
            } else {
                newState = newState.setValue(HeadTailLightBlockBase.LIT, true);
            }
        }

        context.state = newState;

        // Re-read instead of reusing the snapshot taken at the start of the tick,
        // since tickLIT may have written to the contraption in between.
        StructureTemplate.StructureBlockInfo current = context.contraption.getBlocks()
                .get(pos);
        if (current == null)
            return;

        // BlockStates are interned, so this only syncs when the contraption really
        // holds a different state - previously every tick sent a packet to all trackers.
        if (newState != current.state())
            context.contraption.entity.setBlock(pos,
                    new StructureTemplate.StructureBlockInfo(pos, newState, current.nbt()));
    }

    protected void tickLIT(MovementContext context, boolean currentlyOpen) {
        boolean shouldLIT = shouldLIT(context);
        if (!shouldUpdate(context, shouldLIT))
            return;
        if (currentlyOpen == shouldLIT)
            return;

        BlockPos pos = context.localPos;
        Contraption contraption = context.contraption;

        StructureTemplate.StructureBlockInfo info = contraption.getBlocks()
                .get(pos);
        if (info == null || !info.state().hasProperty(LightBlockBase.LIT))
            return;

        toggleLight(context, pos, contraption, info);
    }

    private void toggleLight(MovementContext context, BlockPos pos, Contraption contraption, StructureTemplate.StructureBlockInfo info) {
        BlockState currentState = context.state;
        if (!currentState.hasProperty(LightBlockBase.LIT)) return;

        BlockState newState = currentState.cycle(LightBlockBase.LIT);
        context.state = newState;
        contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, newState, info.nbt()));

        // No collider invalidation here: LIT does not affect getShape/getCollisionShape
        // of any light block, but invalidateColliders() re-joins the VoxelShapes of the
        // whole contraption - once per lamp - whenever a train stalls.
    }

    protected boolean shouldUpdate(MovementContext context, boolean shouldLIT) {
        if (context.firstMovement && shouldLIT)
            return false;
        if (!context.data.contains("Open")) {
            context.data.putBoolean("Open", shouldLIT);
            return true;
        }
        if (context.data.getBoolean("Open") == shouldLIT)
            return false;
        context.data.putBoolean("Open", shouldLIT);
        return true;
    }

    protected boolean shouldLIT(MovementContext context) {
        if (context.disabled)
            return false;
        Contraption contraption = context.contraption;
        boolean canOpen = !contraption.entity.isStalled()
                || contraption instanceof ElevatorContraption ec && ec.arrived;

        if (!canOpen) {
            context.temporaryData = null;
            return false;
        }
        return true;
    }

    private Direction vecToDirection(Vec3 movement) {
        double angle = Math.atan2(movement.z, movement.x);
        float degrees = (float) Math.toDegrees(angle);

        if (degrees < 0)
            degrees += 360;

        if (movement.x == 0 && movement.z == 0)
            return null;

        if (degrees >= 45 && degrees < 135) {
            return Direction.SOUTH;
        } else if (degrees >= 135 && degrees < 225) {
            return Direction.WEST;
        } else if (degrees >= 225 && degrees < 315) {
            return Direction.NORTH;
        } else {
            return Direction.EAST;
        }
    }

    @Override
    public boolean mustTickWhileDisabled() {
        return true;
    }
}
