package net.adeptstack.ctl.blocks.lights.headTailLights.horizontal;

import net.adeptstack.ctl.enums.EBlockZPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StepHeadTailLightBlock extends HalfPanelHeadTailLightBlock {

    private static final VoxelShape SHAPE_N_C = Block.box(0,4,8, 16,12,16);
    private static final VoxelShape SHAPE_N_N = Block.box(0,0,8,16,8,16);
    private static final VoxelShape SHAPE_N_P = Block.box(0,8,8,16,16,16);

    private static final VoxelShape SHAPE_E_C = Block.box(0,4,0,8,12,16);
    private static final VoxelShape SHAPE_E_N = Block.box(0,0,0,8,8,16);
    private static final VoxelShape SHAPE_E_P = Block.box(0,8,0,8,16,16);

    private static final VoxelShape SHAPE_S_C = Block.box(0,4,0,16,12,8);
    private static final VoxelShape SHAPE_S_N = Block.box(0,0,0,16,8,8);
    private static final VoxelShape SHAPE_S_P = Block.box(0,8,0,16,16,8);

    private static final VoxelShape SHAPE_W_C = Block.box(8,4,0,16,12,16);
    private static final VoxelShape SHAPE_W_N = Block.box(8,0,0,16,8,16);
    private static final VoxelShape SHAPE_W_P = Block.box(8,8,0,16,16,16);

    public StepHeadTailLightBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction facing = pState.getValue(FACING);
        EBlockZPosition zAlign = pState.getValue(Z_ALIGN);
        if (zAlign == EBlockZPosition.POSITIVE) {
            return facing == Direction.NORTH ? SHAPE_N_P : facing == Direction.WEST ? SHAPE_W_P : facing == Direction.SOUTH ? SHAPE_S_P : SHAPE_E_P;
        }
        else if (zAlign == EBlockZPosition.NEGATIVE) {
            return facing == Direction.NORTH ? SHAPE_N_N : facing == Direction.WEST ? SHAPE_W_N : facing == Direction.SOUTH ? SHAPE_S_N : SHAPE_E_N;
        }
        else {
            return facing == Direction.NORTH ? SHAPE_N_C : facing == Direction.WEST ? SHAPE_W_C : facing == Direction.SOUTH ? SHAPE_S_C : SHAPE_E_C;
        }
    }
}
