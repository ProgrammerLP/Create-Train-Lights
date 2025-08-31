package net.adeptstack.ctl.blocks.lights.headTailLights.horizontal;

import net.adeptstack.ctl.enums.EBlockZPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VerticalStepHeadTailLightBlock extends VerticalHalfPanelHeadTailLightBlock {

    private static final VoxelShape SHAPE_N_C = Block.box(4,0,8,12,16,16);
    private static final VoxelShape SHAPE_N_N = Block.box(0,0,8,8,16,16);
    private static final VoxelShape SHAPE_N_P = Block.box(8,0,8,16,16,16);

    private static final VoxelShape SHAPE_E_C = Block.box(0,0,4,8,16,12);
    private static final VoxelShape SHAPE_E_N = Block.box(0,0,0,8,16,8);
    private static final VoxelShape SHAPE_E_P = Block.box(0,0,8,8,16,16);

    private static final VoxelShape SHAPE_S_C = Block.box(4,0,0,12,16,8);
    private static final VoxelShape SHAPE_S_N = Block.box(0,0,0,8,16,8);
    private static final VoxelShape SHAPE_S_P = Block.box(8,0,0,16,16,8);

    private static final VoxelShape SHAPE_W_C = Block.box(8,0,4,16,16,12);
    private static final VoxelShape SHAPE_W_N = Block.box(8,0,0,16,16,8);
    private static final VoxelShape SHAPE_W_P = Block.box(8,0,8,16,16,16);

    public VerticalStepHeadTailLightBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(Z_ALIGN) == EBlockZPosition.POSITIVE) {
            return pState.getValue(FACING) == Direction.NORTH ? SHAPE_N_P : pState.getValue(FACING) == Direction.WEST ? SHAPE_W_P : pState.getValue(FACING) == Direction.SOUTH ? SHAPE_S_P : SHAPE_E_P;
        }
        else if (pState.getValue(Z_ALIGN) == EBlockZPosition.NEGATIVE) {
            return pState.getValue(FACING) == Direction.NORTH ? SHAPE_N_N : pState.getValue(FACING) == Direction.WEST ? SHAPE_W_N : pState.getValue(FACING) == Direction.SOUTH ? SHAPE_S_N : SHAPE_E_N;
        }
        else {
            return pState.getValue(FACING) == Direction.NORTH ? SHAPE_N_C : pState.getValue(FACING) == Direction.WEST ? SHAPE_W_C : pState.getValue(FACING) == Direction.SOUTH ? SHAPE_S_C : SHAPE_E_C;
        }
    }
}
