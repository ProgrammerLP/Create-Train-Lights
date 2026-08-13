package net.adeptstack.ctl.behaviours.interaction;

import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.behaviour.SimpleBlockMovingInteraction;
import net.adeptstack.ctl.blocks.lights.LightBlockBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class InteriorLightMovingInteraction extends SimpleBlockMovingInteraction {

    @Override
    protected BlockState handle(Player player, Contraption contraption, BlockPos pos, BlockState currentState) {
        if (!(currentState.getBlock() instanceof LightBlockBase))
            return currentState;

        return currentState.cycle(LightBlockBase.LIT);
    }

    @Override
    protected boolean updateColliders() {
        // LIT does not affect getShape/getCollisionShape, so the contraption's
        // collider does not change - rebuilding it would re-join the VoxelShapes
        // of every block on the contraption for nothing.
        return false;
    }
}
