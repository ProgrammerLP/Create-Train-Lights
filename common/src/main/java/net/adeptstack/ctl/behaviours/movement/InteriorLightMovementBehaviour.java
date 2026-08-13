package net.adeptstack.ctl.behaviours.movement;

import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.elevator.ElevatorContraption;
import net.adeptstack.ctl.blocks.lights.LightBlockBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class InteriorLightMovementBehaviour implements MovementBehaviour {

    @Override
    public void tick(MovementContext context) {
        // Everything below only ever ran server side - bail out before doing any
        // map lookups on the client.
        if (context.world.isClientSide())
            return;

        tickLIT(context);
    }

    protected void tickLIT(MovementContext context) {
        boolean shouldLIT = shouldLIT(context);
        if (!shouldUpdate(context, shouldLIT))
            return;

        BlockPos pos = context.localPos;
        Contraption contraption = context.contraption;

        StructureTemplate.StructureBlockInfo info = contraption.getBlocks()
                .get(pos);
        if (info == null || !info.state().hasProperty(LightBlockBase.LIT))
            return;
        if (info.state().getValue(LightBlockBase.LIT) == shouldLIT)
            return;

        toggleLight(pos, contraption, info);
    }

    private void toggleLight(BlockPos pos, Contraption contraption, StructureTemplate.StructureBlockInfo info) {
        BlockState newState = info.state().cycle(LightBlockBase.LIT);
        contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(info.pos(), newState, info.nbt()));

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
        // Only write when the value actually changes - this used to touch the
        // actor's NBT on every tick for every lamp on every carriage.
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

    @Override
    public boolean mustTickWhileDisabled() {
        return true;
    }
}
