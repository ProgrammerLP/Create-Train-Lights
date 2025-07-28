package net.adeptstack.ctl.blocks.lights.interiorLights.fulldirectional;

import net.adeptstack.ctl.EBlockZPosition;
import net.adeptstack.ctl.blocks.lights.interiorLights.FullDirectionalInteriorLightBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HalfPanelInteriorLightBlock extends FullDirectionalInteriorLightBlock {
    public static final EnumProperty<EBlockZPosition> Z_ALIGN = EnumProperty.create("z_align", EBlockZPosition.class);
    public static final DirectionProperty H_FACING = DirectionProperty.create("h_facing", Direction.Plane.HORIZONTAL);

    //Down
    private static final VoxelShape SHAPE_D_C_SN = Block.box(0,0,4,16,3,12);
    private static final VoxelShape SHAPE_D_C_EW = Block.box(4,0,0,12,3,16);
    private static final VoxelShape SHAPE_D_N_SN = Block.box(0,0,0,16,3,8);
    private static final VoxelShape SHAPE_D_N_EW = Block.box(0,0,0,8,3,16);
    private static final VoxelShape SHAPE_D_P_SN = Block.box(0,0,8,16,3,16);
    private static final VoxelShape SHAPE_D_P_EW = Block.box(8,0,0,16,3,16);

    //Up
    private static final VoxelShape SHAPE_U_C_SN = Block.box(0,13,4,16,16,12);
    private static final VoxelShape SHAPE_U_C_EW = Block.box(4,13,0,12,16,16);
    private static final VoxelShape SHAPE_U_N_SN = Block.box(0,13,0,16,16,8);
    private static final VoxelShape SHAPE_U_N_EW = Block.box(0,13,0,8,16,16);
    private static final VoxelShape SHAPE_U_P_SN = Block.box(0,13,8,16,16,16);
    private static final VoxelShape SHAPE_U_P_EW = Block.box(8,13,0,16,16,16);

    //Other
    private static final VoxelShape SHAPE_N_C = Block.box(0,4,13,16,12,16);
    private static final VoxelShape SHAPE_N_N = Block.box(0,0,13,16,8,16);
    private static final VoxelShape SHAPE_N_P = Block.box(0,8,13,16,16,16);

    private static final VoxelShape SHAPE_E_C = Block.box(0,4,0,3,12,16);
    private static final VoxelShape SHAPE_E_N = Block.box(0,0,0,3,8,16);
    private static final VoxelShape SHAPE_E_P = Block.box(0,8,0,3,16,16);

    private static final VoxelShape SHAPE_S_C = Block.box(0,4,0,16,12,3);
    private static final VoxelShape SHAPE_S_N = Block.box(0,0,0,16,8,3);
    private static final VoxelShape SHAPE_S_P = Block.box(0,8,0,16,16,3);

    private static final VoxelShape SHAPE_W_C = Block.box(13,4,0,16,12,16);
    private static final VoxelShape SHAPE_W_N = Block.box(13,0,0,16,8,16);
    private static final VoxelShape SHAPE_W_P = Block.box(13,8,0,16,16,16);


    public HalfPanelInteriorLightBlock(BlockBehaviour.Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(Z_ALIGN, EBlockZPosition.CENTER)
                .setValue(H_FACING, Direction.NORTH));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(Z_ALIGN).add(H_FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getDefaultPlacementState(context);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(FACING) == Direction.UP) {
            if (pState.getValue(Z_ALIGN) == EBlockZPosition.POSITIVE) {
                return pState.getValue(H_FACING) == Direction.NORTH || pState.getValue(H_FACING) == Direction.SOUTH ? SHAPE_U_P_SN : SHAPE_U_P_EW;
            }
            else if (pState.getValue(Z_ALIGN) == EBlockZPosition.CENTER) {
                return pState.getValue(H_FACING) == Direction.NORTH || pState.getValue(H_FACING) == Direction.SOUTH ? SHAPE_U_C_SN : SHAPE_U_C_EW;
            }
            else if (pState.getValue(Z_ALIGN) == EBlockZPosition.NEGATIVE) {
                return pState.getValue(H_FACING) == Direction.NORTH || pState.getValue(H_FACING) == Direction.SOUTH ? SHAPE_U_N_SN : SHAPE_U_N_EW;
            }
        }
        else if (pState.getValue(FACING) == Direction.DOWN) {
            if (pState.getValue(Z_ALIGN) == EBlockZPosition.POSITIVE) {
                return pState.getValue(H_FACING) == Direction.NORTH || pState.getValue(H_FACING) == Direction.SOUTH ? SHAPE_D_P_SN : SHAPE_D_P_EW;
            }
            else if (pState.getValue(Z_ALIGN) == EBlockZPosition.CENTER) {
                return pState.getValue(H_FACING) == Direction.NORTH || pState.getValue(H_FACING) == Direction.SOUTH ? SHAPE_D_C_SN : SHAPE_D_C_EW;
            }
            else if (pState.getValue(Z_ALIGN) == EBlockZPosition.NEGATIVE) {
                return pState.getValue(H_FACING) == Direction.NORTH || pState.getValue(H_FACING) == Direction.SOUTH ? SHAPE_D_N_SN : SHAPE_D_N_EW;
            }
        }
        else if (pState.getValue(Z_ALIGN) == EBlockZPosition.POSITIVE) {
            return pState.getValue(FACING) == Direction.NORTH ? SHAPE_N_P : pState.getValue(FACING) == Direction.WEST ? SHAPE_W_P : pState.getValue(FACING) == Direction.SOUTH ? SHAPE_S_P : SHAPE_E_P;
        }
        else if (pState.getValue(Z_ALIGN) == EBlockZPosition.NEGATIVE) {
            return pState.getValue(FACING) == Direction.NORTH ? SHAPE_N_N : pState.getValue(FACING) == Direction.WEST ? SHAPE_W_N : pState.getValue(FACING) == Direction.SOUTH ? SHAPE_S_N : SHAPE_E_N;
        }
        else {
            return pState.getValue(FACING) == Direction.NORTH ? SHAPE_N_C : pState.getValue(FACING) == Direction.WEST ? SHAPE_W_C : pState.getValue(FACING) == Direction.SOUTH ? SHAPE_S_C : SHAPE_E_C;
        }
        return SHAPE_N_C;
    }

    public BlockState getDefaultPlacementState(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        Direction horizontalFacing = context.getHorizontalDirection().getOpposite();
        BlockPos blockPos = context.getClickedPos();
        BlockState baseState = this.defaultBlockState().setValue(FACING, clickedFace);

        if (clickedFace == Direction.UP || clickedFace == Direction.DOWN) {
            baseState = baseState.setValue(H_FACING, horizontalFacing);
        }

        double clickY = context.getClickLocation().y - blockPos.getY();
        double clickZ = context.getClickLocation().z - blockPos.getZ();
        double clickX = context.getClickLocation().x - blockPos.getX();

        EBlockZPosition zAlign;

        if (clickedFace.getAxis().isVertical()) {
            if (horizontalFacing.getAxis() == Direction.Axis.Z) {
                zAlign = clickZ < 0.33 ? EBlockZPosition.NEGATIVE : (clickZ > 0.66 ? EBlockZPosition.POSITIVE : EBlockZPosition.CENTER);
            } else {
                zAlign = clickX < 0.33 ? EBlockZPosition.NEGATIVE : (clickX > 0.66 ? EBlockZPosition.POSITIVE : EBlockZPosition.CENTER);
            }
        }
        else {
            zAlign = clickY < 0.33 ? EBlockZPosition.NEGATIVE : (clickY > 0.66 ? EBlockZPosition.POSITIVE : EBlockZPosition.CENTER);
        }

        return baseState.setValue(Z_ALIGN, zAlign)
                .setValue(FACING, clickedFace == Direction.UP || clickedFace == Direction.DOWN ? clickedFace.getOpposite() : clickedFace)
                .setValue(LIT, false)
                .setValue(H_FACING, horizontalFacing);
    }
}