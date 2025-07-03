package net.adeptstack.ctl;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.adeptstack.ctl.blocks.lights.LightBlockBase;
import net.adeptstack.ctl.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public final class Main {
    public static final String MOD_ID = "ctl";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

    public static void init() {
        // Write common init code here.
        ModTabs.CREATIVE_MODE_TABS.register();
        ModBlocks.register();
        ModItems.register();
        ModSounds.SOUND_EVENTS.register();
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @Nullable
    public static KineticStats create(Item item) {
        if (item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block instanceof LightBlockBase) {
                return new KineticStats(block);
            }
            // ...
        }
        return null;
    }

    static {
        REGISTRATE.setTooltipModifierFactory(item -> {
            return new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE).andThen(TooltipModifier.mapNull(create(item)));
        });
    }
}