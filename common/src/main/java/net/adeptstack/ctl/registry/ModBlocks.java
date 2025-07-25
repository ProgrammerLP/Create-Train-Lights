package net.adeptstack.ctl.registry;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.adeptstack.ctl.blocks.lights.HeadTailLightBlockBase;
import net.adeptstack.ctl.blocks.lights.LightBlockBase;
import net.adeptstack.ctl.blocks.lights.interiorLights.GridInteriorLightBlock;
import net.adeptstack.ctl.blocks.lights.interiorLights.HorizontalInteriorLightBlock;
import net.adeptstack.ctl.blocks.lights.interiorLights.SlopedInteriorLightBlock;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {

    public static final BlockEntry<LightBlockBase> ILB_BLOCK =
            CTLBuilderTransformers.InteriorLightBlock("ilb_block", MapColor.COLOR_BLACK);

    public static final BlockEntry<HeadTailLightBlockBase> HTLB_BLOCK =
            CTLBuilderTransformers.HeadTailLightBlock("htlb_block", MapColor.COLOR_BLACK);

    public static final BlockEntry<SlopedInteriorLightBlock> SLOPED_ILB =
            CTLBuilderTransformers.SlopedInteriorLightBlock("sloped_ilb", MapColor.COLOR_GRAY);

    public static final BlockEntry<GridInteriorLightBlock> GRID_ILB =
            CTLBuilderTransformers.GridInteriorLightBlock("grid_ilb", MapColor.COLOR_GRAY);

    public static void register() { }
}
