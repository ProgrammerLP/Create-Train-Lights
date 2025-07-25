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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HalfPanelInteriorLightBlock extends FullDirectionalInteriorLightBlock {
    public static final EnumProperty<EBlockZPosition> Z_ALIGN = EnumProperty.create("z_align", EBlockZPosition.class);
    public static final DirectionProperty H_FACING = DirectionProperty.create("h_facing");

    private static final VoxelShape SHAPE_CT_SN = Block.box(0, 0, 6.5, 16, 16, 9.5);
    private static final VoxelShape SHAPE_CT_EW = Block.box(6.5, 0, 0, 9.5, 16, 16);

    private static final VoxelShape SHAPE_S = Block.box(0, 0, 0, 16, 16, 3);
    private static final VoxelShape SHAPE_E = Block.box(0, 0, 0, 3, 16, 16);

    private static final VoxelShape SHAPE_N = Block.box(0, 0, 13, 16, 16, 16);
    private static final VoxelShape SHAPE_W = Block.box(13, 0, 0, 16, 16, 16);

    private static final VoxelShape SHAPE_U = Block.box(0, 13, 0, 16, 16, 16);
    private static final VoxelShape SHAPE_D = Block.box(0, 0, 0, 16, 3, 16);

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

    public BlockState getDefaultPlacementState(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        Direction horizontalFacing = context.getHorizontalDirection().getOpposite();
        BlockPos blockPos = context.getClickedPos();
        BlockState baseState = this.defaultBlockState().setValue(FACING, clickedFace);

        // Optional: horizontale Blickrichtung bei Decke/Boden
        if (clickedFace == Direction.UP || clickedFace == Direction.DOWN) {
            baseState = baseState.setValue(H_FACING, horizontalFacing);
        }

        // z_align bestimmen
        double clickY = context.getClickLocation().y - blockPos.getY();
        double clickZ = context.getClickLocation().z - blockPos.getZ();
        double clickX = context.getClickLocation().x - blockPos.getX();

        EBlockZPosition zAlign = EBlockZPosition.CENTER;

        if (clickedFace.getAxis().isVertical()) {
            // Decke oder Boden: z_align über Blickrichtung
            if (horizontalFacing.getAxis() == Direction.Axis.Z) {
                zAlign = clickZ < 0.33 ? EBlockZPosition.NEGATIVE : (clickZ > 0.66 ? EBlockZPosition.POSITIVE : EBlockZPosition.CENTER);
            } else {
                zAlign = clickX < 0.33 ? EBlockZPosition.NEGATIVE : (clickX > 0.66 ? EBlockZPosition.POSITIVE : EBlockZPosition.CENTER);
            }
        } else {
            // Wandplatzierung: z_align über y-Achse
            zAlign = clickY < 0.33 ? EBlockZPosition.NEGATIVE : (clickY > 0.66 ? EBlockZPosition.POSITIVE : EBlockZPosition.CENTER);
        }

        return baseState.setValue(Z_ALIGN, zAlign)
                .setValue(FACING, clickedFace == Direction.UP || clickedFace == Direction.DOWN ? clickedFace.getOpposite() : clickedFace)
                .setValue(H_FACING, horizontalFacing);
    }


}
