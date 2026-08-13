package net.adeptstack.ctl.blocks.lights;

import com.simibubi.create.AllItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class HeadTailLightBlockBase extends LightBlockBase {

    public static final IntegerProperty LIGHT_MODE = IntegerProperty.create("lightmode", 0, 1);
    public static final BooleanProperty IS_LOCKED = BooleanProperty.create("is_locked");

    public HeadTailLightBlockBase(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LIGHT_MODE, 0)
                .setValue(IS_LOCKED, false)
        );
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(LIGHT_MODE).add(IS_LOCKED);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        // Only the server changes the block. Doing it on the client too triggered a
        // light engine relight that the server update overwrites moments later.
        boolean serverSide = !level.isClientSide;

        if (player.isHolding(AllItems.WRENCH.asItem())) {
            BlockState newState = state.cycle(IS_LOCKED);
            if (serverSide)
                level.setBlockAndUpdate(pos, newState);
            level.playSound(player, pos, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.BLOCKS, 1F, 0.5f);

            if (serverSide) {
                String key = newState.getValue(IS_LOCKED) ? "text.ctl.htlb.locked" : "text.ctl.htlb.unlocked";
                player.displayClientMessage(Component.translatable(key), true);
            }
        }
        else if (!player.isShiftKeyDown()) {
            if (serverSide)
                level.setBlockAndUpdate(pos, state.cycle(LIT));
            level.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 1F, 0.5f);
        }
        else {
            if (state.getValue(IS_LOCKED)) {
                if (serverSide)
                    player.displayClientMessage(Component.translatable("text.ctl.htlb.islocked"), true);
                level.playSound(player, pos, SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1F, 1.5f);
                return InteractionResult.PASS;
            }

            if (serverSide)
                level.setBlockAndUpdate(pos, state.cycle(LIGHT_MODE));
            level.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 1F, 0.6f);
        }
        return InteractionResult.SUCCESS;
    }
}
