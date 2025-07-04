package net.adeptstack.ctl.registry;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.adeptstack.ctl.Main;
import net.minecraft.world.item.Item;

public class ModItems {

    public static final ItemEntry<Item> SPEAKER_MEMBRANE =
            item("speaker_membrane");

    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_SOUND_UNIT =
            sequencedAssemblyItem("incomplete_sound_unit");


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
