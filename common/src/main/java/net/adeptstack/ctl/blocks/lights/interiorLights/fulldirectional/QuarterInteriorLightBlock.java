package net.adeptstack.ctl.blocks.lights.interiorLights.fulldirectional;

import net.adeptstack.ctl.EBlockPlacePosition;
import net.adeptstack.ctl.EBlockZPosition;
import net.adeptstack.ctl.blocks.lights.interiorLights.FullDirectionalInteriorLightBlock;
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
import org.jetbrains.annotations.NotNull;

public class QuarterInteriorLightBlock extends FullDirectionalInteriorLightBlock {

    public static final EnumProperty<EBlockPlacePosition> IBP = EnumProperty.create("ibp", EBlockPlacePosition.class);

    //Down
    private static final VoxelShape SHAPE_D_C = Block.box(4,0,4,12,3,12);
    private static final VoxelShape SHAPE_D_TL = Block.box(0,0,0,8,3,8);
    private static final VoxelShape SHAPE_D_TR = Block.box(8,0,0,16,3,8);
    private static final VoxelShape SHAPE_D_BL = Block.box(0,0,8,8,3,16);
    private static final VoxelShape SHAPE_D_BR = Block.box(8,0,8,16,3,16);

    //Up
    private static final VoxelShape SHAPE_U_C = Block.box(4,13,4,12,16,12);
    private static final VoxelShape SHAPE_U_TL = Block.box(0,13,0,8,16,8);
    private static final VoxelShape SHAPE_U_TR = Block.box(8,13,0,16,16,8);
    private static final VoxelShape SHAPE_U_BL = Block.box(0,13,8,8,16,16);
    private static final VoxelShape SHAPE_U_BR = Block.box(8,13,8,16,16,16);

    //Other
    private static final VoxelShape SHAPE_N_C = Block.box(4,4,13,12,12,16);
    private static final VoxelShape SHAPE_N_TL = Block.box(8,8,13,16,16,16);
    private static final VoxelShape SHAPE_N_TR = Block.box(0,8,13,8,16,16);
    private static final VoxelShape SHAPE_N_BL = Block.box(8,0,13,16,8,16);
    private static final VoxelShape SHAPE_N_BR = Block.box(0,0,13,8,8,16);

    private static final VoxelShape SHAPE_E_C = Block.box(0,4,4,3,12,12);
    private static final VoxelShape SHAPE_E_TL = Block.box(0,8,8,3,16,16);
    private static final VoxelShape SHAPE_E_TR = Block.box(0,8,0,3,16,8);
    private static final VoxelShape SHAPE_E_BL = Block.box(0,0,8,3,8,16);
    private static final VoxelShape SHAPE_E_BR = Block.box(0,0,0,3,8,8);

    private static final VoxelShape SHAPE_S_C = Block.box(4,4,0,12,12,3);
    private static final VoxelShape SHAPE_S_TL = Block.box(0,8,0,8,16,3);
    private static final VoxelShape SHAPE_S_TR = Block.box(8,8,0,16,16,3);
    private static final VoxelShape SHAPE_S_BL = Block.box(0,0,0,8,8,3);
    private static final VoxelShape SHAPE_S_BR = Block.box(8,0,0,16,8,3);

    private static final VoxelShape SHAPE_W_C = Block.box(13,4,4,16,12,12);
    private static final VoxelShape SHAPE_W_TL = Block.box(13,8,0,16,16,8);
    private static final VoxelShape SHAPE_W_TR = Block.box(13,8,8,16,16,16);
    private static final VoxelShape SHAPE_W_BL = Block.box(13,0,0,16,8,8);
    private static final VoxelShape SHAPE_W_BR = Block.box(13,0,8,16,8,16);

    public QuarterInteriorLightBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(IBP, EBlockPlacePosition.CENTER));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(IBP);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getDefaultPlacementState(context);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(FACING) == Direction.UP) {
            if (pState.getValue(IBP) == EBlockPlacePosition.TOP_LEFT) {
                return SHAPE_U_TL;
            }
            else if (pState.getValue(IBP) == EBlockPlacePosition.TOP_RIGHT) {
                return SHAPE_U_TR;
            }
            else if (pState.getValue(IBP) == EBlockPlacePosition.CENTER) {
                return SHAPE_U_C;
            }
            else if (pState.getValue(IBP) == EBlockPlacePosition.BOTTOM_LEFT) {
                return SHAPE_U_BL;
            }
            else if (pState.getValue(IBP) == EBlockPlacePosition.BOTTOM_RIGHT) {
                return SHAPE_U_BR;
            }
        }
        else if (pState.getValue(FACING) == Direction.DOWN) {
            if (pState.getValue(IBP) == EBlockPlacePosition.TOP_LEFT) {
                return SHAPE_D_TL;
            }
            else if (pState.getValue(IBP) == EBlockPlacePosition.TOP_RIGHT) {
                return SHAPE_D_TR;
            }
            else if (pState.getValue(IBP) == EBlockPlacePosition.CENTER) {
                return SHAPE_D_C;
            }
            else if (pState.getValue(IBP) == EBlockPlacePosition.BOTTOM_LEFT) {
                return SHAPE_D_BL;
            }
            else if (pState.getValue(IBP) == EBlockPlacePosition.BOTTOM_RIGHT) {
                return SHAPE_D_BR;
            }
        }
        else if (pState.getValue(IBP) == EBlockPlacePosition.TOP_LEFT) {
            return pState.getValue(FACING) == Direction.NORTH ? SHAPE_N_TL : pState.getValue(FACING) == Direction.WEST ? SHAPE_W_TL : pState.getValue(FACING) == Direction.SOUTH ? SHAPE_S_TL : SHAPE_E_TL;
        }
        else if (pState.getValue(IBP) == EBlockPlacePosition.TOP_RIGHT) {
            return pState.getValue(FACING) == Direction.NORTH ? SHAPE_N_TR : pState.getValue(FACING) == Direction.WEST ? SHAPE_W_TR : pState.getValue(FACING) == Direction.SOUTH ? SHAPE_S_TR : SHAPE_E_TR;
        }
        else if (pState.getValue(IBP) == EBlockPlacePosition.BOTTOM_LEFT) {
            return pState.getValue(FACING) == Direction.NORTH ? SHAPE_N_BL : pState.getValue(FACING) == Direction.WEST ? SHAPE_W_BL : pState.getValue(FACING) == Direction.SOUTH ? SHAPE_S_BL : SHAPE_E_BL;
        }
        else if (pState.getValue(IBP) == EBlockPlacePosition.BOTTOM_RIGHT) {
            return pState.getValue(FACING) == Direction.NORTH ? SHAPE_N_BR : pState.getValue(FACING) == Direction.WEST ? SHAPE_W_BR : pState.getValue(FACING) == Direction.SOUTH ? SHAPE_S_BR : SHAPE_E_BR;
        }
        else {
            return pState.getValue(FACING) == Direction.NORTH ? SHAPE_N_C : pState.getValue(FACING) == Direction.WEST ? SHAPE_W_C : pState.getValue(FACING) == Direction.SOUTH ? SHAPE_S_C : SHAPE_E_C;
        }
        return SHAPE_N_C;
    }

    public BlockState getDefaultPlacementState(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        BlockPos blockPos = context.getClickedPos();

        double relX = context.getClickLocation().x - blockPos.getX();
        double relY = context.getClickLocation().y - blockPos.getY();
        double relZ = context.getClickLocation().z - blockPos.getZ();

        Direction finalFacing = (clickedFace == Direction.UP || clickedFace == Direction.DOWN) ? clickedFace.getOpposite() : clickedFace;

        double u = 0.5;
        double v = 0.5;

        switch (clickedFace) {
            case UP:
            case DOWN:
                u = relX;
                v = relZ;
                break;
            case NORTH:
                u = 1 - relX;
                v = 1 - relY;
                break;
            case SOUTH:
                u = relX;
                v = 1 - relY;
                break;
            case WEST:
                u = relZ;
                v = 1 - relY;
                break;
            case EAST:
                u = 1 - relZ;
                v = 1 - relY;
                break;
        }

        EBlockPlacePosition placePosition = getEBlockPlacePosition(u, v);

        return this.defaultBlockState()
                .setValue(FACING, finalFacing)
                .setValue(LIT, false)
                .setValue(IBP, placePosition);
    }

    private static @NotNull EBlockPlacePosition getEBlockPlacePosition(double u, double v) {
        EBlockPlacePosition placePosition = EBlockPlacePosition.CENTER;
        if (u > 0.66) {
            if (v > 0.66) placePosition = EBlockPlacePosition.BOTTOM_RIGHT;
            else if (v < 0.33) placePosition = EBlockPlacePosition.TOP_RIGHT;
            else placePosition = EBlockPlacePosition.CENTER;
        }
        else if (u < 0.33) {
            if (v > 0.66) placePosition = EBlockPlacePosition.BOTTOM_LEFT;
            else if (v < 0.33) placePosition = EBlockPlacePosition.TOP_LEFT;
            else placePosition = EBlockPlacePosition.CENTER;
        }
        else {
            if (v > 0.66) {
                if (u > 0.66) placePosition = EBlockPlacePosition.BOTTOM_RIGHT;
                else if (u < 0.33) placePosition = EBlockPlacePosition.TOP_RIGHT;
                else placePosition = EBlockPlacePosition.CENTER;
            }
            else if (v < 0.33) {
                if (u > 0.66) placePosition = EBlockPlacePosition.BOTTOM_LEFT;
                else if (u < 0.33) placePosition = EBlockPlacePosition.TOP_LEFT;
                else placePosition = EBlockPlacePosition.CENTER;
            }
        }
        return placePosition;
    }
}
