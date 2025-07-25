package net.adeptstack.ctl.blocks.lights.interiorLights.horizontal;

import net.adeptstack.ctl.blocks.lights.interiorLights.HorizontalInteriorLightBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SlopedInteriorLightBlock extends HorizontalInteriorLightBlock {
    private static final VoxelShape SHAPE_N = Block.box(0, 12, 7, 16, 16, 16);
    private static final VoxelShape SHAPE_E = Block.box(0, 12, 0, 9, 16, 16);
    private static final VoxelShape SHAPE_S = Block.box(0, 12, 0, 16, 16, 9);
    private static final VoxelShape SHAPE_W = Block.box(7, 12, 0, 16, 16, 16);

    public SlopedInteriorLightBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction facing = pState.getValue(FACING);
        return facing == Direction.NORTH ? SHAPE_N : facing == Direction.EAST ? SHAPE_E : facing == Direction.SOUTH ? SHAPE_S : SHAPE_W;
    }
}
