package net.adeptstack.ctl.blocks.lights.interiorLights.fulldirectional;

import net.adeptstack.ctl.enums.EBlockZPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StepInteriorLightBlock extends HalfPanelInteriorLightBlock {

    //Down
    private static final VoxelShape SHAPE_D_C_SN = Block.box(0,0,4,16,8,12);
    private static final VoxelShape SHAPE_D_C_EW = Block.box(4,0,0,12,8,16);
    private static final VoxelShape SHAPE_D_N_SN = Block.box(0,0,0,16,8,8);
    private static final VoxelShape SHAPE_D_N_EW = Block.box(0,0,0,8,8,16);
    private static final VoxelShape SHAPE_D_P_SN = Block.box(0,0,8,16,8,16);
    private static final VoxelShape SHAPE_D_P_EW = Block.box(8,0,0,16,8,16);

    //Up
    private static final VoxelShape SHAPE_U_C_SN = Block.box(0,8,4,16,16,12);
    private static final VoxelShape SHAPE_U_C_EW = Block.box(4,8,0,12,16,16);
    private static final VoxelShape SHAPE_U_N_SN = Block.box(0,8,0,16,16,8);
    private static final VoxelShape SHAPE_U_N_EW = Block.box(0,8,0,8,16,16);
    private static final VoxelShape SHAPE_U_P_SN = Block.box(0,8,8,16,16,16);
    private static final VoxelShape SHAPE_U_P_EW = Block.box(8,8,0,16,16,16);

    //Other
    private static final VoxelShape SHAPE_N_C = Block.box(0,4,8,16,12,16);
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

    public StepInteriorLightBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction facing = pState.getValue(FACING);
        Direction hFacing = pState.getValue(H_FACING);
        EBlockZPosition zAlign = pState.getValue(Z_ALIGN);
        if (facing == Direction.UP) {
            if (zAlign == EBlockZPosition.POSITIVE) {
                return hFacing == Direction.NORTH || hFacing == Direction.SOUTH ? SHAPE_U_P_SN : SHAPE_U_P_EW;
            }
            else if (zAlign == EBlockZPosition.CENTER) {
                return hFacing == Direction.NORTH || hFacing == Direction.SOUTH ? SHAPE_U_C_SN : SHAPE_U_C_EW;
            }
            else if (zAlign == EBlockZPosition.NEGATIVE) {
                return hFacing == Direction.NORTH || hFacing == Direction.SOUTH ? SHAPE_U_N_SN : SHAPE_U_N_EW;
            }
        }
        else if (facing == Direction.DOWN) {
            if (zAlign == EBlockZPosition.POSITIVE) {
                return hFacing == Direction.NORTH || hFacing == Direction.SOUTH ? SHAPE_D_P_SN : SHAPE_D_P_EW;
            }
            else if (zAlign == EBlockZPosition.CENTER) {
                return hFacing == Direction.NORTH || hFacing == Direction.SOUTH ? SHAPE_D_C_SN : SHAPE_D_C_EW;
            }
            else if (zAlign == EBlockZPosition.NEGATIVE) {
                return hFacing == Direction.NORTH || hFacing == Direction.SOUTH ? SHAPE_D_N_SN : SHAPE_D_N_EW;
            }
        }
        else if (zAlign == EBlockZPosition.POSITIVE) {
            return facing == Direction.NORTH ? SHAPE_N_P : facing == Direction.WEST ? SHAPE_W_P : facing == Direction.SOUTH ? SHAPE_S_P : SHAPE_E_P;
        }
        else if (zAlign == EBlockZPosition.NEGATIVE) {
            return facing == Direction.NORTH ? SHAPE_N_N : facing == Direction.WEST ? SHAPE_W_N : facing == Direction.SOUTH ? SHAPE_S_N : SHAPE_E_N;
        }
        else {
            return facing == Direction.NORTH ? SHAPE_N_C : facing == Direction.WEST ? SHAPE_W_C : facing == Direction.SOUTH ? SHAPE_S_C : SHAPE_E_C;
        }
        return SHAPE_N_C;
    }
}
