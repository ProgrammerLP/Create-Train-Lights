package net.adeptstack.ctl.blocks.lights.headTailLights.horizontal;

import net.adeptstack.ctl.blocks.lights.headTailLights.HorizontalHeadTailLightBlock;
import net.adeptstack.ctl.enums.EBlockZPosition;
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

public class VerticalSlabHeadTailLightBlock extends HorizontalHeadTailLightBlock {
    public static final EnumProperty<EBlockZPosition> Z_ALIGN = EnumProperty.create("z_align", EBlockZPosition.class);

    private static final VoxelShape SHAPE_CT_SN = Block.box(0, 0, 4, 16, 16, 12);
    private static final VoxelShape SHAPE_CT_EW = Block.box(4, 0, 0, 12, 16, 16);

    private static final VoxelShape SHAPE_S = Block.box(0, 0, 0, 16, 16, 8);
    private static final VoxelShape SHAPE_E = Block.box(0, 0, 0, 8, 16, 16);

    private static final VoxelShape SHAPE_N = Block.box(0, 0, 8, 16, 16, 16);
    private static final VoxelShape SHAPE_W = Block.box(8, 0, 0, 16, 16, 16);

    public VerticalSlabHeadTailLightBlock(Properties properties) {
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
            return pState.getValue(FACING) == Direction.NORTH ? SHAPE_N : pState.getValue(FACING) == Direction.WEST ? SHAPE_W : pState.getValue(FACING) == Direction.SOUTH ? SHAPE_S : SHAPE_E;
        }
        else if (pState.getValue(Z_ALIGN) == EBlockZPosition.NEGATIVE) {
            return pState.getValue(FACING) == Direction.NORTH ? SHAPE_S : pState.getValue(FACING) == Direction.WEST ? SHAPE_E : pState.getValue(FACING) == Direction.SOUTH ? SHAPE_N : SHAPE_W;
        }
        else {
            return pState.getValue(FACING) == Direction.NORTH || pState.getValue(FACING) == Direction.SOUTH ? SHAPE_CT_SN : SHAPE_CT_EW;
        }
    }

    public BlockState getDefaultPlacementState(BlockPlaceContext context)  {
        BlockState stateForPlacement = super.getStateForPlacement(context);
        Direction direction = context.getClickedFace();
        Direction looking = context.getHorizontalDirection();
        Direction.Axis axis = looking.getAxis();
        Direction.AxisDirection axisDirection = looking.getAxisDirection();

        double xzPos = 0.5f;
        if (axis == Direction.Axis.X) {
            xzPos = context.getClickLocation().x - context.getClickedPos().getX();
        } else if (axis == Direction.Axis.Z) {
            xzPos = context.getClickLocation().z - context.getClickedPos().getZ();
        }

        EBlockZPosition zAlign = EBlockZPosition.CENTER;

        if (direction == context.getPlayer().getDirection().getOpposite() || (axisDirection == Direction.AxisDirection.POSITIVE ? xzPos > 0.5D : xzPos < 0.5D)) {
            zAlign = EBlockZPosition.POSITIVE;
        }  else if (direction == context.getPlayer().getDirection() || (axisDirection == Direction.AxisDirection.POSITIVE ? xzPos < 0.5D : xzPos > 0.5D)) {
            zAlign = EBlockZPosition.NEGATIVE;
        }

        return stateForPlacement
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(LIT, false)
                .setValue(Z_ALIGN, zAlign);
    }
}