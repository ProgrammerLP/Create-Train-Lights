package net.adeptstack.ctl.blocks.lights.interiorLights.horizontal;

import net.adeptstack.ctl.EBlockZPosition;
import net.adeptstack.ctl.blocks.lights.interiorLights.HorizontalInteriorLightBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VerticalHalfPanelInteriorLightBlock extends HorizontalInteriorLightBlock {
    public static final EnumProperty<EBlockZPosition> Z_ALIGN = EnumProperty.create("z_align", EBlockZPosition.class);

    private static final VoxelShape SHAPE_N_C = Block.box(4,0,13,12,16,16);
    private static final VoxelShape SHAPE_N_N = Block.box(0,0,13,8,16,16);
    private static final VoxelShape SHAPE_N_P = Block.box(8,0,13,16,16,16);

    private static final VoxelShape SHAPE_E_C = Block.box(0,0,4,3,16,12);
    private static final VoxelShape SHAPE_E_N = Block.box(0,0,0,3,16,8);
    private static final VoxelShape SHAPE_E_P = Block.box(0,0,8,3,16,16);

    private static final VoxelShape SHAPE_S_C = Block.box(4,0,0,12,16,3);
    private static final VoxelShape SHAPE_S_N = Block.box(0,0,0,8,16,3);
    private static final VoxelShape SHAPE_S_P = Block.box(8,0,0,16,16,3);

    private static final VoxelShape SHAPE_W_C = Block.box(13,0,4,16,16,12);
    private static final VoxelShape SHAPE_W_N = Block.box(13,0,0,16,16,8);
    private static final VoxelShape SHAPE_W_P = Block.box(13,0,8,16,16,16);

    public VerticalHalfPanelInteriorLightBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(Z_ALIGN, EBlockZPosition.CENTER));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(Z_ALIGN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getDefaultPlacementState(context);
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

    public BlockState getDefaultPlacementState(BlockPlaceContext context) {
        BlockState stateForPlacement = super.getStateForPlacement(context);
        Direction direction = context.getHorizontalDirection().getOpposite();
        EBlockZPosition zAlign = EBlockZPosition.CENTER;

        double localOffset = getLocalOffset(context, direction);

        if (localOffset > 0.6666666) {
            zAlign = EBlockZPosition.POSITIVE;
        } else if (localOffset < 0.3333333) {
            zAlign = EBlockZPosition.NEGATIVE;
        }

        return stateForPlacement
                .setValue(FACING, direction)
                .setValue(LIT, false)
                .setValue(Z_ALIGN, zAlign);
    }

    private static double getLocalOffset(BlockPlaceContext context, Direction clickedFace) {
        double localOffset;

        if (clickedFace.getAxis().isHorizontal()) {
            if (clickedFace.getAxis() == Direction.Axis.X) {
                localOffset = context.getClickLocation().z - context.getClickedPos().getZ(); // vorne/hinten bei Wand
            } else {
                localOffset = context.getClickLocation().x - context.getClickedPos().getX();
            }
        }
        else {
            if (context.getHorizontalDirection().getAxis() == Direction.Axis.X) {
                localOffset = context.getClickLocation().x - context.getClickedPos().getX();
            } else {
                localOffset = context.getClickLocation().z - context.getClickedPos().getZ();
            }
        }
        return localOffset;
    }
}
