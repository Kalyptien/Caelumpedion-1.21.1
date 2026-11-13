package com.kalyptien.caelumpedion.misc;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CaelumpedionSounds {

    // Assuming that your mod id is examplemod
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, CaelumpedionMod.MOD_ID);

    public static final Supplier<SoundEvent> COLAPTES_AURATUS_CHANT = registerSoundEvent("entity.colaptes_auratus.chant");
    public static final Supplier<SoundEvent> COLAPTES_AURATUS_CALL = registerSoundEvent("entity.colaptes_auratus.call");

    public static final Supplier<SoundEvent> DENDROCOPOS_MAJOR_CALL = registerSoundEvent("entity.dendrocopos_major.call");

    public static final Supplier<SoundEvent> DENDROCOPTES_MEDIUS_CHANT = registerSoundEvent("entity.dendrocoptes_medius.chant");
    public static final Supplier<SoundEvent> DENDROCOPTES_MEDIUS_CALL = registerSoundEvent("entity.dendrocoptes_medius.call");

    public static final Supplier<SoundEvent> PICUS_PHANTASIA_TROGLODYTA_CHANT = registerSoundEvent("entity.picus_phantasia_troglodyta.chant");
    public static final Supplier<SoundEvent> PICUS_PHANTASIA_TROGLODYTA_CALL = registerSoundEvent("entity.picus_phantasia_troglodyta.call");

    public static final Supplier<SoundEvent> PICUS_VIRIDS_CHANT = registerSoundEvent("entity.picus_viridis.chant");
    public static final Supplier<SoundEvent> PICUS_VIRIDS_CALL = registerSoundEvent("entity.picus_viridis.call");

    // ==========

    public static final Supplier<SoundEvent> RAMPHASTOS_TOCO_CALL = registerSoundEvent("entity.ramphastos_toco.call");

    public static final Supplier<SoundEvent> RAMPHASTOS_SULFURATUS_CALL = registerSoundEvent("entity.ramphastos_sulfuratus.call");

    // ==========

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(CaelumpedionMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
