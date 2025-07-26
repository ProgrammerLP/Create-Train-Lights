package net.adeptstack.ctl.registry;

import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.adeptstack.ctl.behaviours.movement.HeadTailLightMovementBehaviour;
import net.adeptstack.ctl.behaviours.interaction.HeadTailLightMovingInteraction;
import net.adeptstack.ctl.behaviours.movement.InteriorLightMovementBehaviour;
import net.adeptstack.ctl.behaviours.interaction.InteriorLightMovingInteraction;
import net.adeptstack.ctl.blocks.lights.HeadTailLightBlockBase;
import net.adeptstack.ctl.blocks.lights.LightBlockBase;
import net.adeptstack.ctl.blocks.lights.interiorLights.fulldirectional.HalfPanelInteriorLightBlock;
import net.adeptstack.ctl.blocks.lights.interiorLights.fulldirectional.VerticalHalfPanelInteriorLightBlock;
import net.adeptstack.ctl.blocks.lights.interiorLights.horizontal.GridInteriorLightBlock;
import net.adeptstack.ctl.blocks.lights.interiorLights.fulldirectional.PanelInteriorLightBlock;
import net.adeptstack.ctl.blocks.lights.interiorLights.horizontal.SlopedInteriorLightBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.AllInteractionBehaviours.interactionBehaviour;
import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static net.adeptstack.ctl.Main.REGISTRATE;

@SuppressWarnings({"unused","removal"})
public class CTLBuilderTransformers {

    public static <B extends LightBlockBase, P> NonNullUnaryOperator<BlockBuilder<B, P>> interiorLightBlock() {
        return b -> b.initialProperties(() -> Blocks.REDSTONE_LAMP) // for villager AI..
                .properties(p -> p.strength(3.0F, 6.0F))
                .addLayer(() -> RenderType::cutout)
                .transform(pickaxeOnly())
                .onRegister(interactionBehaviour(new InteriorLightMovingInteraction()))
                .onRegister(movementBehaviour(new InteriorLightMovementBehaviour()))
                .item()
                .tab(ModTabs.CTL_TAB.getKey())
                .build();
    }

    public static BlockEntry<LightBlockBase> InteriorLightBlock(String id, MapColor color) {
        return REGISTRATE
                .block(id, LightBlockBase::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.mapColor(color)
                        .sound(SoundType.AMETHYST_CLUSTER)
                        .lightLevel(state -> state.getValue(LightBlockBase.LIT) ? 15 : 0))
                .transform(interiorLightBlock())
                .register();
    }

    public static BlockEntry<SlopedInteriorLightBlock> SlopedInteriorLightBlock(String id, MapColor color) {
        return REGISTRATE
                .block(id, SlopedInteriorLightBlock::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.mapColor(color)
                        .sound(SoundType.GLASS)
                        .lightLevel(state -> state.getValue(LightBlockBase.LIT) ? 15 : 0))
                .transform(interiorLightBlock())
                .register();
    }

    public static BlockEntry<GridInteriorLightBlock> GridInteriorLightBlock(String id, MapColor color) {
        return REGISTRATE
                .block(id, GridInteriorLightBlock::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.mapColor(color)
                        .sound(SoundType.GLASS)
                        .lightLevel(state -> state.getValue(LightBlockBase.LIT) ? 15 : 0))
                .transform(interiorLightBlock())
                .register();
    }

    public static BlockEntry<PanelInteriorLightBlock> PanelInteriorLightBlock(String id, MapColor color) {
        return REGISTRATE
                .block(id, PanelInteriorLightBlock::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.mapColor(color)
                        .sound(SoundType.GLASS)
                        .lightLevel(state -> state.getValue(LightBlockBase.LIT) ? 15 : 0))
                .transform(interiorLightBlock())
                .register();
    }

    public static BlockEntry<HalfPanelInteriorLightBlock> HalfPanelInteriorLightBlock(String id, MapColor color) {
        return REGISTRATE
                .block(id, HalfPanelInteriorLightBlock::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.mapColor(color)
                        .sound(SoundType.GLASS)
                        .lightLevel(state -> state.getValue(LightBlockBase.LIT) ? 15 : 0))
                .transform(interiorLightBlock())
                .register();
    }

    public static BlockEntry<VerticalHalfPanelInteriorLightBlock> VerticalHalfPanelInteriorLightBlock(String id, MapColor color) {
        return REGISTRATE
                .block(id, VerticalHalfPanelInteriorLightBlock::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.mapColor(color)
                        .sound(SoundType.GLASS)
                        .lightLevel(state -> state.getValue(LightBlockBase.LIT) ? 15 : 0))
                .transform(interiorLightBlock())
                .register();
    }

    public static <B extends HeadTailLightBlockBase, P> NonNullUnaryOperator<BlockBuilder<B, P>> htLightBlock() {
        return b -> b.initialProperties(() -> Blocks.REDSTONE_LAMP) // for villager AI..
                .properties(p -> p.strength(3.0F, 6.0F))
                .addLayer(() -> RenderType::cutout)
                .transform(pickaxeOnly())
                .onRegister(interactionBehaviour(new HeadTailLightMovingInteraction()))
                .onRegister(movementBehaviour(new HeadTailLightMovementBehaviour()))
                .item()
                .tab(ModTabs.CTL_TAB.getKey())
                .build();
    }

    public static BlockEntry<HeadTailLightBlockBase> HeadTailLightBlock(String id, MapColor color) {
        return REGISTRATE
                .block(id, HeadTailLightBlockBase::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.mapColor(color)
                        .sound(SoundType.AMETHYST_CLUSTER)
                        .lightLevel(state -> state.getValue(LightBlockBase.LIT) ? state.getValue(HeadTailLightBlockBase.LIGHT_MODE) == 0 ? 15 : 10 : 0))
                .transform(htLightBlock())
                .register();
    }

}
