package net.adeptstack.ctl.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import static net.adeptstack.ctl.Main.MOD_ID;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(MOD_ID, Registries.SOUND_EVENT);

    private static RegistrySupplier<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(new ResourceLocation(MOD_ID, name), () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, name)));
    }
}
