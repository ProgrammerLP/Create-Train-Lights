package net.adeptstack.ctl.blocks.lights.interiorLights.fulldirectional;

import net.adeptstack.ctl.EBlockZPosition;
import net.adeptstack.ctl.blocks.lights.interiorLights.FullDirectionalInteriorLightBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PanelInteriorLightBlock extends FullDirectionalInteriorLightBlock {
    public static final EnumProperty<EBlockZPosition> Z_ALIGN = EnumProperty.create("z_align", EBlockZPosition.class);

    private static final VoxelShape SHAPE_CT_SN = Block.box(0, 0, 6.5, 16, 16, 9.5);
    private static final VoxelShape SHAPE_CT_EW = Block.box(6.5, 0, 0, 9.5, 16, 16);

    private static final VoxelShape SHAPE_S = Block.box(0, 0, 0, 16, 16, 3);
    private static final VoxelShape SHAPE_E = Block.box(0, 0, 0, 3, 16, 16);

    private static final VoxelShape SHAPE_N = Block.box(0, 0, 13, 16, 16, 16);
    private static final VoxelShape SHAPE_W = Block.box(13, 0, 0, 16, 16, 16);

    private static final VoxelShape SHAPE_U = Block.box(0, 13, 0, 16, 16, 16);
    private static final VoxelShape SHAPE_D = Block.box(0, 0, 0, 16, 3, 16);

    public PanelInteriorLightBlock(BlockBehaviour.Properties properties) {
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
        if (pState.getValue(FACING) == Direction.UP) {
            return SHAPE_U;
        }
        else if (pState.getValue(FACING) == Direction.DOWN) {
            return  SHAPE_D;
        }
        else if (pState.getValue(Z_ALIGN) == EBlockZPosition.POSITIVE) {
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

        if (direction == context.getPlayer().getDirection().getOpposite() || (axisDirection == Direction.AxisDirection.POSITIVE ? xzPos > 0.66666666D : xzPos < 0.33333333D)) {
            zAlign = EBlockZPosition.POSITIVE;
        }  else if (direction == context.getPlayer().getDirection() || (axisDirection == Direction.AxisDirection.POSITIVE ? xzPos < 0.33333333D : xzPos > 0.66666666D)) {
            zAlign = EBlockZPosition.NEGATIVE;
        }

        return stateForPlacement
                .setValue(FACING, context.getNearestLookingDirection() == Direction.UP || context.getNearestLookingDirection() == Direction.DOWN ? context.getNearestLookingDirection() : context.getNearestLookingDirection().getOpposite())
                .setValue(Z_ALIGN, zAlign);
    }
}
