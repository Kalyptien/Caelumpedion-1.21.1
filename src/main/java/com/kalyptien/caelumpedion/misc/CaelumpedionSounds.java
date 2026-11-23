package com.kalyptien.caelumpedion.misc;

import com.kalyptien.caelumpedion.CaelumpedionMod;
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

    public static final Supplier<SoundEvent> GYPAETUS_BARBATUS_CALL = registerSoundEvent("entity.gypaetus_barbatus.call");

    public static final Supplier<SoundEvent> GYPS_FULVUS_CALL = registerSoundEvent("entity.gyps_fulvus.call");

    // ==========

    public static final Supplier<SoundEvent> ANAS_PLATYRHYNCHOS_CHANT = registerSoundEvent("entity.anas_platyrhynchos.chant");
    public static final Supplier<SoundEvent> ANAS_PLATYRHYNCHOS_CALL = registerSoundEvent("entity.anas_platyrhynchos.call");

    public static final Supplier<SoundEvent> ANSER_ANSER_CHANT = registerSoundEvent("entity.anser_anser.chant");
    public static final Supplier<SoundEvent> ANSER_ANSER_CALL = registerSoundEvent("entity.anser_anser.call");

    public static final Supplier<SoundEvent> CYGNUS_ATRATUS_CALL = registerSoundEvent("entity.cygnus_atratus.call");

    public static final Supplier<SoundEvent> CYGNUS_OLOR_CHANT = registerSoundEvent("entity.cygnus_olor.chant");
    public static final Supplier<SoundEvent> CYGNUS_OLOR_CALL = registerSoundEvent("entity.cygnus_olor.call");

    // ==========

    public static final Supplier<SoundEvent> ARCHILOCHUS_COLUBRIS_CHANT = registerSoundEvent("entity.archilochus_colubris.chant");
    public static final Supplier<SoundEvent> ARCHILOCHUS_COLUBRIS_CALL = registerSoundEvent("entity.archilochus_colubris.call");

    public static final Supplier<SoundEvent> COLIBRI_CORUSCANS_CHANT = registerSoundEvent("entity.colibri_coruscans.chant");
    public static final Supplier<SoundEvent> COLIBRI_CORUSCANS_CALL = registerSoundEvent("entity.colibri_coruscans.call");

    public static final Supplier<SoundEvent> MELLISUGA_HELENAE_CHANT = registerSoundEvent("entity.mellisuga_helenae.chant");
    public static final Supplier<SoundEvent> MELLISUGA_HELENAE_CALL = registerSoundEvent("entity.mellisuga_helenae.call");

    // ==========

    public static final Supplier<SoundEvent> COLUMBA_LIVIA_CHANT = registerSoundEvent("entity.columba_livia.chant");
    public static final Supplier<SoundEvent> COLUMBA_LIVIA_CALL = registerSoundEvent("entity.columba_livia.call");

    public static final Supplier<SoundEvent> COLUMBA_PALUMBUS_CHANT = registerSoundEvent("entity.columba_palumbus.chant");

    public static final Supplier<SoundEvent> STREPTOPELIA_DECAOCTO_CHANT = registerSoundEvent("entity.streptopelia_decaocto.chant");

    public static final Supplier<SoundEvent> ZENAIDA_MACROURA_CHANT = registerSoundEvent("entity.zenaida_macroura.chant");
    public static final Supplier<SoundEvent> ZENAIDA_MACROURA_CALL = registerSoundEvent("entity.zenaida_macroura.call");

    // ==========

    public static final Supplier<SoundEvent> ANTIGONE_ANTIGONE_CHANT = registerSoundEvent("entity.antigone_antigone.chant");
    public static final Supplier<SoundEvent> ANTIGONE_ANTIGONE_CALL = registerSoundEvent("entity.antigone_antigone.call");

    public static final Supplier<SoundEvent> ANTIGONE_CANADENSIS_CHANT = registerSoundEvent("entity.antigone_canadensis.chant");
    public static final Supplier<SoundEvent> ANTIGONE_CANADENSIS_CALL = registerSoundEvent("entity.antigone_canadensis.call");

    public static final Supplier<SoundEvent> GRUS_JAPONENSIS_CALL = registerSoundEvent("entity.grus_japonensis.call");

    // ==========

    public static final Supplier<SoundEvent> PICA_PICA_CHANT = registerSoundEvent("entity.pica_pica.chant");
    public static final Supplier<SoundEvent> PICA_PICA_CALL = registerSoundEvent("entity.pica_pica.call");

    public static final Supplier<SoundEvent> CORVUS_CORVAX_CHANT = registerSoundEvent("entity.corvus_corax.chant");
    public static final Supplier<SoundEvent> CORVUS_CORVAX_CALL = registerSoundEvent("entity.corvus_corax.call");

    public static final Supplier<SoundEvent> CORVUS_MELLORI_CHANT = registerSoundEvent("entity.corvus_mellori.chant");
    public static final Supplier<SoundEvent> CORVUS_MELLORI_CALL = registerSoundEvent("entity.corvus_mellori.call");

    public static final Supplier<SoundEvent> TURDUS_MERULA_CHANT = registerSoundEvent("entity.turdus_merula.chant");
    public static final Supplier<SoundEvent> TURDUS_MERULA_CALL = registerSoundEvent("entity.turdus_merula.call");

    public static final Supplier<SoundEvent> CYANOCITTA_CRISTATA_CHANT = registerSoundEvent("entity.cyanocitta_cristata.chant");
    public static final Supplier<SoundEvent> CYANOCITTA_CRISTATA_CALL = registerSoundEvent("entity.cyanocitta_cristata.call");

    // ==========

    public static final Supplier<SoundEvent> CARDINALIS_CARDINALIS_CHANT = registerSoundEvent("entity.cardinalis_cardinalis.chant");
    public static final Supplier<SoundEvent> CARDINALIS_CARDINALIS_CALL = registerSoundEvent("entity.cardinalis_cardinalis.call");

    public static final Supplier<SoundEvent> CYANISTES_CAERULEUS_CHANT = registerSoundEvent("entity.cyanistes_caeruleus.chant");
    public static final Supplier<SoundEvent> CYANISTES_CAERULEUS_CALL = registerSoundEvent("entity.cyanistes_caeruleus.call");

    public static final Supplier<SoundEvent> ERITHACUS_RUBECULA_CHANT = registerSoundEvent("entity.erithacus_rubecula.chant");
    public static final Supplier<SoundEvent> ERITHACUS_RUBECULA_CALL = registerSoundEvent("entity.erithacus_rubecula.call");

    public static final Supplier<SoundEvent> LOPHOPHANES_CRISTATUS_CHANT = registerSoundEvent("entity.lophophanes_cristatus.chant");
    public static final Supplier<SoundEvent> LOPHOPHANES_CRISTATUS_CALL = registerSoundEvent("entity.lophophanes_cristatus.call");

    public static final Supplier<SoundEvent> PASSER_DOMESTICUS_CHANT = registerSoundEvent("entity.passer_domesticus.chant");
    public static final Supplier<SoundEvent> PASSER_DOMESTICUS_CALL = registerSoundEvent("entity.passer_domesticus.call");

    public static final Supplier<SoundEvent> PERIPARUS_ATER_CHANT = registerSoundEvent("entity.periparus_ater.chant");
    public static final Supplier<SoundEvent> PERIPARUS_ATER_CALL = registerSoundEvent("entity.periparus_ater.call");

    public static final Supplier<SoundEvent> PHOENICURUS_OCHRUROS_CHANT = registerSoundEvent("entity.phoenicurus_ochruros.chant");
    public static final Supplier<SoundEvent> PHOENICURUS_OCHRUROS_CALL = registerSoundEvent("entity.phoenicurus_ochruros.call");

    // ==========

    public static final Supplier<SoundEvent> DELICHON_URBICUM_CALL = registerSoundEvent("entity.delichon_urbicum.call");

    public static final Supplier<SoundEvent> HIRUNDO_RUSTICA_CALL = registerSoundEvent("entity.hirundo_rustica.call");

    // ==========

    public static final Supplier<SoundEvent> COLAPTES_AURATUS_CHANT = registerSoundEvent("entity.colaptes_auratus.chant");
    public static final Supplier<SoundEvent> COLAPTES_AURATUS_CALL = registerSoundEvent("entity.colaptes_auratus.call");

    public static final Supplier<SoundEvent> DENDROCOPOS_MAJOR_CALL = registerSoundEvent("entity.dendrocopos_major.call");

    public static final Supplier<SoundEvent> DENDROCOPTES_MEDIUS_CHANT = registerSoundEvent("entity.dendrocoptes_medius.chant");
    public static final Supplier<SoundEvent> DENDROCOPTES_MEDIUS_CALL = registerSoundEvent("entity.dendrocoptes_medius.call");

    public static final Supplier<SoundEvent> PICUS_PHANTASIA_TROGLODYTA_CHANT = registerSoundEvent("entity.picus_phantasia_troglodyta.chant");
    public static final Supplier<SoundEvent> PICUS_PHANTASIA_TROGLODYTA_CALL = registerSoundEvent("entity.picus_phantasia_troglodyta.call");

    public static final Supplier<SoundEvent> PICUS_VIRIDIS_CHANT = registerSoundEvent("entity.picus_viridis.chant");
    public static final Supplier<SoundEvent> PICUS_VIRIDIS_CALL = registerSoundEvent("entity.picus_viridis.call");

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
