package net.adeptstack.ctl.registry;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.adeptstack.ctl.blocks.lights.HeadTailLightBlock;
import net.adeptstack.ctl.blocks.lights.LightBlockBase;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {

    public static final BlockEntry<LightBlockBase> TEST_LIGHT =
            CTLBuilderTransformers.InteriorLightBlock("test_light", MapColor.COLOR_BLACK);

    public static final BlockEntry<HeadTailLightBlock> TEST_HEADTAIL_LIGHT =
            CTLBuilderTransformers.HeadTailLightBlock("test_light_two", MapColor.COLOR_BLACK);

    public static void register() { }
}
