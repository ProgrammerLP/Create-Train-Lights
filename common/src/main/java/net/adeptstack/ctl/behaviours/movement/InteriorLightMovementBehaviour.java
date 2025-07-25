package net.adeptstack.ctl.behaviours.movement;

import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.behaviour.MovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.elevator.ElevatorContraption;
import net.adeptstack.ctl.blocks.lights.LightBlockBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;

public class InteriorLightMovementBehaviour implements MovementBehaviour {

    @Override
    public void tick(MovementContext context) {
        StructureTemplate.StructureBlockInfo structureBlockInfo = context.contraption.getBlocks()
                .get(context.localPos);
        if (structureBlockInfo == null)
            return;
        boolean open = structureBlockInfo.state().getValue(LightBlockBase.LIT);

        if (!context.world.isClientSide())
            tickLIT(context, open);

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

        toggleLight(pos, contraption, info);
    }

    private void toggleLight(BlockPos pos, Contraption contraption, StructureTemplate.StructureBlockInfo info) {
        BlockState newState = info.state().cycle(LightBlockBase.LIT);
        contraption.entity.setBlock(pos, new StructureTemplate.StructureBlockInfo(info.pos(), newState, info.nbt()));

        if (info != null && info.state().hasProperty(LightBlockBase.LIT)) {
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

    @Override
    public boolean renderAsNormalBlockEntity() {
        return true;
    }

    @Override
    public boolean mustTickWhileDisabled() {
        return true;
    }
}
