package net.adeptstack.ctl.behaviours.movement;

import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.behaviour.MovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.elevator.ElevatorContraption;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.adeptstack.ctl.blocks.lights.HeadTailLightBlockBase;
import net.adeptstack.ctl.blocks.lights.LightBlockBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;

public class HeadTailLightMovementBehaviour implements MovementBehaviour {

    @Override
    public void tick(MovementContext context) {
        StructureTemplate.StructureBlockInfo structureBlockInfo = context.contraption.getBlocks()
                .get(context.localPos);
        if (structureBlockInfo == null)
            return;

        boolean open = structureBlockInfo.state().getValue(LightBlockBase.LIT);
        if (!context.world.isClientSide())
            tickLIT(context, open);

        BlockPos pos = context.localPos;
        if (context.contraption.entity instanceof CarriageContraptionEntity cce && context.contraption instanceof CarriageContraption cc) {
            Direction assemblyDirection = cc.getAssemblyDirection();
            if (assemblyDirection == Direction.UP || assemblyDirection == Direction.DOWN) {
                return;
            }

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

            int localXZ;
            Vec3 value;
            if (assemblyDirection == Direction.EAST || assemblyDirection == Direction.WEST) {
                localXZ = pos.getX();
                Vec3 localP = new Vec3(1, 0, 0);
                Vec3 localN = new Vec3(-1, 0, 0);
                Vec3 globalP = cce.toGlobalVector(localP, 1f);
                Vec3 globalN = cce.toGlobalVector(localN, 1f);
                value = globalP.subtract(globalN);
            }
            else {
                localXZ = pos.getZ();
                Vec3 localP = new Vec3(0, 0, 1);
                Vec3 localN = new Vec3(0, 0, -1);
                Vec3 globalP = cce.toGlobalVector(localP, 1f);
                Vec3 globalN = cce.toGlobalVector(localN, 1f);
                value = globalP.subtract(globalN);
            }

            System.out.println(context.localPos);

            if (direction == Direction.NORTH) {
                if (value.z > 0 && localXZ > 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 1);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                } else if (value.z > 0 && localXZ < 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 0);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                } else if (value.z < 0 && localXZ > 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 0);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                } else if (value.z < 0 && localXZ < 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 1);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                }
            } else if (direction == Direction.EAST) {
                if (value.x > 0 && localXZ > 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 0);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                } else if (value.x > 0 && localXZ < 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 1);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                } else if (value.x < 0 && localXZ > 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 1);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                } else if (value.x < 0 && localXZ < 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 0);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                }
            } else if (direction == Direction.SOUTH) {
                if (value.z > 0 && localXZ > 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 0);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                } else if (value.z > 0 && localXZ < 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 1);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                } else if (value.z < 0 && localXZ > 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 1);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                } else if (value.z < 0 && localXZ < 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 0);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                }
            } else if (direction == Direction.WEST) {
                if (value.x > 0 && localXZ > 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 1);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                } else if (value.x > 0 && localXZ < 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 0);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                } else if (value.x < 0 && localXZ > 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 0);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                } else if (value.x < 0 && localXZ < 0) {
                    context.state = context.state .setValue(HeadTailLightBlockBase.LIGHT_MODE, 1);
                    context.contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(pos, context.state, structureBlockInfo.nbt()));
                }
            }
        }
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

        if (info != null && info.state().
                hasProperty(LightBlockBase.LIT)) {
            newState = info.state().cycle(LightBlockBase.LIT);
            contraption.invalidateColliders();

            boolean open = newState.getValue(LightBlockBase.LIT);

            if (!open)
                contraption.getContraptionWorld().playLocalSound(pos.getX(), pos.getY(), pos.getZ(),
                        SoundEvents.IRON_DOOR_CLOSE, SoundSource.BLOCKS, .125f, 1, false);
            else {
                contraption.getContraptionWorld().playLocalSound(pos.getX(), pos.getY(), pos.getZ(),
                        SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, .125f, 1, false);
            }
        }
    }

    protected boolean shouldUpdate(MovementContext context, boolean shouldLIT) {
        if (context.firstMovement && shouldLIT)
            return false;
        if (!context.data.contains("Open")) {
            context.data.putBoolean("Open", shouldLIT);
            return true;
        }
        boolean wasOpen = context.data.getBoolean("Open");
        context.data.putBoolean("Open", shouldLIT);
        return wasOpen != shouldLIT;
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
    public boolean renderAsNormalBlockEntity() {
        return true;
    }

    @Override
    public boolean mustTickWhileDisabled() {
        return true;
    }
}
