package net.adeptstack.ctl.registry;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.adeptstack.ctl.Main;
import net.minecraft.world.item.Item;

public class ModItems {

    public static final ItemEntry<SequencedAssemblyItem> IC_HTLB_BLOCK =
            sequencedAssemblyItem("ic_htlb_block");

    public static final ItemEntry<SequencedAssemblyItem> IC_ILB_BLOCK =
            sequencedAssemblyItem("ic_ilb_block");


    private static ItemEntry<Item> item(String name) {
        return Main.REGISTRATE.item(name, Item::new)
                .tab(ModTabs.CTL_TAB.getKey())
                .register();
    }

    private static ItemEntry<SequencedAssemblyItem> sequencedAssemblyItem(String name) {
        return Main.REGISTRATE.item(name, SequencedAssemblyItem::new)
                .tab(ModTabs.CTL_TAB.getKey())
                .register();
    }

    public static void register() {}
}
