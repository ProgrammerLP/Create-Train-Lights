package net.adeptstack.ctl.blocks.lights.interiorLights.horizontal;

import net.adeptstack.ctl.blocks.lights.interiorLights.HorizontalInteriorLightBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GridInteriorLightBlock extends HorizontalInteriorLightBlock {

    private static final VoxelShape SHAPE = Block.box(0, 13, 0, 16, 16, 16);

    public GridInteriorLightBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
