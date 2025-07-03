package net.adeptstack.ctl.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static net.adeptstack.ctl.Main.MOD_ID;

public class ModTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> CTL_TAB = CREATIVE_MODE_TABS.register(new ResourceLocation(MOD_ID, "ctl_tab"),
            () -> CreativeTabRegistry.create(
                    Component.translatable("itemGroup." + MOD_ID + ".ctl_tab"), // Tab Name
                    () -> new ItemStack(ModBlocks.TEST_LIGHT.asItem()) // Icon
            ));
}