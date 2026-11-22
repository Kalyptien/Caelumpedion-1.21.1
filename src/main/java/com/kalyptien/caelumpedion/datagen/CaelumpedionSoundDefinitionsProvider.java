package com.kalyptien.caelumpedion.datagen;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.misc.CaelumpedionSounds;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class CaelumpedionSoundDefinitionsProvider extends SoundDefinitionsProvider {

    public CaelumpedionSoundDefinitionsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CaelumpedionMod.MOD_ID, existingFileHelper);
    }

    @Override
    public void registerSounds() {
        // ========== ACCIPITRIFORME ==========
        add(CaelumpedionSounds.GYPAETUS_BARBATUS_CALL, SoundDefinition.definition()
                .with(
                    sound("caelumpedion:entity/accipitriforme/gypaetus_barbatus_call_0").attenuationDistance(32),
                    sound("caelumpedion:entity/accipitriforme/gypaetus_barbatus_call_1").attenuationDistance(32),
                    sound("caelumpedion:entity/accipitriforme/gypaetus_barbatus_call_2").attenuationDistance(32),
                    sound("caelumpedion:entity/accipitriforme/gypaetus_barbatus_call_3").attenuationDistance(32),
                    sound("caelumpedion:entity/accipitriforme/gypaetus_barbatus_call_4").attenuationDistance(32),
                    sound("caelumpedion:entity/accipitriforme/gypaetus_barbatus_call_5").attenuationDistance(32)

                )
                .subtitle("sound.subtitle.accipitriforme.call")
        );
        //===
        add(CaelumpedionSounds.GYPS_FULVUS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/accipitriforme/gyps_fulvus_call_0").attenuationDistance(32),
                        sound("caelumpedion:entity/accipitriforme/gyps_fulvus_call_1").attenuationDistance(32),
                        sound("caelumpedion:entity/accipitriforme/gyps_fulvus_call_2").attenuationDistance(32)
                )
                .subtitle("sound.subtitle.accipitriforme.call")
        );

        // ========== ANSERIFORME ==========

        // ===== DUCK =====
        add(CaelumpedionSounds.ANAS_PLATYRHYNCHOS_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/anseriforme/anas_platyrhynchos_chant_0").attenuationDistance(32),
                        sound("caelumpedion:entity/anseriforme/anas_platyrhynchos_chant_1").attenuationDistance(32)
                )
                .subtitle("sound.subtitle.anseriforme.duck.chant")
        );
        add(CaelumpedionSounds.ANAS_PLATYRHYNCHOS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/anseriforme/anas_platyrhynchos_call_0"),
                        sound("caelumpedion:entity/anseriforme/anas_platyrhynchos_call_1"),
                        sound("caelumpedion:entity/anseriforme/anas_platyrhynchos_call_2"),
                        sound("caelumpedion:entity/anseriforme/anas_platyrhynchos_call_3"),
                        sound("caelumpedion:entity/anseriforme/anas_platyrhynchos_call_4")
                )
                .subtitle("sound.subtitle.anseriforme.duck.call")
        );

        // ===== GOOSE =====
        add(CaelumpedionSounds.ANSER_ANSER_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/anseriforme/anser_anser_chant_0").attenuationDistance(32),
                        sound("caelumpedion:entity/anseriforme/anser_anser_chant_1").attenuationDistance(32)
                )
                .subtitle("sound.subtitle.anseriforme.goose.chant")
        );
        add(CaelumpedionSounds.ANSER_ANSER_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/anseriforme/anser_anser_call_0"),
                        sound("caelumpedion:entity/anseriforme/anser_anser_call_1"),
                        sound("caelumpedion:entity/anseriforme/anser_anser_call_2"),
                        sound("caelumpedion:entity/anseriforme/anser_anser_call_3")
                )
                .subtitle("sound.subtitle.anseriforme.goose.call")
        );

        // ===== SWAN =====
        add(CaelumpedionSounds.CYGNUS_ATRATUS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/anseriforme/cygnus_atratus_call_0"),
                        sound("caelumpedion:entity/anseriforme/cygnus_atratus_call_1"),
                        sound("caelumpedion:entity/anseriforme/cygnus_atratus_call_2"),
                        sound("caelumpedion:entity/anseriforme/cygnus_atratus_call_3")
                )
                .subtitle("sound.subtitle.anseriforme.swan.call")
        );
        //===
        add(CaelumpedionSounds.CYGNUS_OLOR_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/anseriforme/cygnus_olor_chant_0").attenuationDistance(32),
                        sound("caelumpedion:entity/anseriforme/cygnus_olor_chant_1").attenuationDistance(32),
                        sound("caelumpedion:entity/anseriforme/cygnus_olor_chant_2").attenuationDistance(32)
                )
                .subtitle("sound.subtitle.anseriforme.swan.chant")
        );
        add(CaelumpedionSounds.CYGNUS_OLOR_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/anseriforme/cygnus_olor_call_0"),
                        sound("caelumpedion:entity/anseriforme/cygnus_olor_call_1"),
                        sound("caelumpedion:entity/anseriforme/cygnus_olor_call_2"),
                        sound("caelumpedion:entity/anseriforme/cygnus_olor_call_3")
                )
                .subtitle("sound.subtitle.anseriforme.swan.call")
        );

        // ========== TROCHILIDAE ==========

        add(CaelumpedionSounds.ARCHILOCHUS_COLUBRIS_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/trochilidae/archilochus_colubris_call_0"),
                        sound("caelumpedion:entity/trochilidae/archilochus_colubris_call_1")
                )
                .subtitle("sound.subtitle.trochilidae.chant")
        );
        add(CaelumpedionSounds.ARCHILOCHUS_COLUBRIS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/trochilidae/archilochus_colubris_call_0"),
                        sound("caelumpedion:entity/trochilidae/archilochus_colubris_call_1"),
                        sound("caelumpedion:entity/trochilidae/archilochus_colubris_call_2"),
                        sound("caelumpedion:entity/trochilidae/archilochus_colubris_call_3")
                )
                .subtitle("sound.subtitle.trochilidae.call")
        );
        //===
        add(CaelumpedionSounds.COLIBRI_CORUSCANS_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/trochilidae/colibri_coruscans_call_0"),
                        sound("caelumpedion:entity/trochilidae/colibri_coruscans_call_1")
                )
                .subtitle("sound.subtitle.trochilidae.chant")
        );
        add(CaelumpedionSounds.COLIBRI_CORUSCANS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/trochilidae/colibri_coruscans_call_0"),
                        sound("caelumpedion:entity/trochilidae/colibri_coruscans_call_1"),
                        sound("caelumpedion:entity/trochilidae/colibri_coruscans_call_2"),
                        sound("caelumpedion:entity/trochilidae/colibri_coruscans_call_3")
                )
                .subtitle("sound.subtitle.trochilidae.call")
        );
        //===
        add(CaelumpedionSounds.MELLISUGA_HELENAE_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/trochilidae/mellisuga_helenae_call_0"),
                        sound("caelumpedion:entity/trochilidae/mellisuga_helenae_call_1")
                )
                .subtitle("sound.subtitle.trochilidae.chant")
        );
        add(CaelumpedionSounds.MELLISUGA_HELENAE_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/trochilidae/mellisuga_helenae_call_0"),
                        sound("caelumpedion:entity/trochilidae/mellisuga_helenae_call_1"),
                        sound("caelumpedion:entity/trochilidae/mellisuga_helenae_call_2"),
                        sound("caelumpedion:entity/trochilidae/mellisuga_helenae_call_3")
                )
                .subtitle("sound.subtitle.trochilidae.call")
        );

        // ========== COLUMBIFORME ==========

        add(CaelumpedionSounds.COLUMBA_LIVIA_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/columbiforme/columba_livia_chant_0"),
                        sound("caelumpedion:entity/columbiforme/columba_livia_chant_1")
                )
                .subtitle("sound.subtitle.columbiforme.chant")
        );
        add(CaelumpedionSounds.COLUMBA_LIVIA_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/columbiforme/columba_livia_call_0"),
                        sound("caelumpedion:entity/columbiforme/columba_livia_call_1"),
                        sound("caelumpedion:entity/columbiforme/columba_livia_call_2"),
                        sound("caelumpedion:entity/columbiforme/columba_livia_call_3")
                )
                .subtitle("sound.subtitle.columbiforme.call")
        );
        //===
        add(CaelumpedionSounds.COLUMBA_PALUMBUS_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/columbiforme/columba_palumbus_chant_0").attenuationDistance(32),
                        sound("caelumpedion:entity/columbiforme/columba_palumbus_chant_1").attenuationDistance(32)
                )
                .subtitle("sound.subtitle.columbiforme.chant")
        );
        //===
        add(CaelumpedionSounds.STREPTOPELIA_DECAOCTO_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/columbiforme/streptopelia_decaocto_chant_0").attenuationDistance(32),
                        sound("caelumpedion:entity/columbiforme/streptopelia_decaocto_chant_1").attenuationDistance(32),
                        sound("caelumpedion:entity/columbiforme/streptopelia_decaocto_chant_2").attenuationDistance(32)
                )
                .subtitle("sound.subtitle.columbiforme.chant")
        );
        //===
        add(CaelumpedionSounds.ZENAIDA_MACROURA_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/columbiforme/zenaida_macroura_chant_0").attenuationDistance(32),
                        sound("caelumpedion:entity/columbiforme/zenaida_macroura_chant_1").attenuationDistance(32),
                        sound("caelumpedion:entity/columbiforme/zenaida_macroura_chant_2").attenuationDistance(32)
                )
                .subtitle("sound.subtitle.columbiforme.chant")
        );
        add(CaelumpedionSounds.ZENAIDA_MACROURA_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/columbiforme/zenaida_macroura_call_0"),
                        sound("caelumpedion:entity/columbiforme/zenaida_macroura_call_1"),
                        sound("caelumpedion:entity/columbiforme/zenaida_macroura_call_2"),
                        sound("caelumpedion:entity/columbiforme/zenaida_macroura_call_3")
                )
                .subtitle("sound.subtitle.columbiforme.call")
        );

        // ========== GRUIFORME ==========

        add(CaelumpedionSounds.ANTIGONE_ANTIGONE_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/gruiforme/antigone_antigone_chant_0").attenuationDistance(48),
                        sound("caelumpedion:entity/gruiforme/antigone_antigone_chant_1").attenuationDistance(48)
                )
                .subtitle("sound.subtitle.gruiforme.chant")
        );
        add(CaelumpedionSounds.ANTIGONE_ANTIGONE_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/gruiforme/antigone_antigone_call_0").attenuationDistance(48),
                        sound("caelumpedion:entity/gruiforme/antigone_antigone_call_1").attenuationDistance(48),
                        sound("caelumpedion:entity/gruiforme/antigone_antigone_call_2").attenuationDistance(48),
                        sound("caelumpedion:entity/gruiforme/antigone_antigone_call_3").attenuationDistance(48),
                        sound("caelumpedion:entity/gruiforme/antigone_antigone_call_4").attenuationDistance(48)
                )
                .subtitle("sound.subtitle.gruiforme.call")
        );
        //===
        add(CaelumpedionSounds.ANTIGONE_CANADENSIS_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/gruiforme/antigone_canadensis_chant_0").attenuationDistance(48),
                        sound("caelumpedion:entity/gruiforme/antigone_canadensis_chant_1").attenuationDistance(48)
                )
                .subtitle("sound.subtitle.gruiforme.chant")
        );
        add(CaelumpedionSounds.ANTIGONE_CANADENSIS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/gruiforme/antigone_canadensis_call_0").attenuationDistance(48),
                        sound("caelumpedion:entity/gruiforme/antigone_canadensis_call_1").attenuationDistance(48),
                        sound("caelumpedion:entity/gruiforme/antigone_canadensis_call_2").attenuationDistance(48),
                        sound("caelumpedion:entity/gruiforme/antigone_canadensis_call_3").attenuationDistance(48),
                        sound("caelumpedion:entity/gruiforme/antigone_canadensis_call_4").attenuationDistance(48)
                )
                .subtitle("sound.subtitle.gruiforme.call")
        );
        //===
        add(CaelumpedionSounds.GRUS_JAPONENSIS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/gruiforme/grus_japonensis_call_0").attenuationDistance(48),
                        sound("caelumpedion:entity/gruiforme/grus_japonensis_call_1").attenuationDistance(48),
                        sound("caelumpedion:entity/gruiforme/grus_japonensis_call_2").attenuationDistance(48),
                        sound("caelumpedion:entity/gruiforme/grus_japonensis_call_3").attenuationDistance(48),
                        sound("caelumpedion:entity/gruiforme/grus_japonensis_call_4").attenuationDistance(48)
                )
                .subtitle("sound.subtitle.gruiforme.call")
        );

        // ========== CORVIDAE ==========

        add(CaelumpedionSounds.PICA_PICA_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/corvidae/pica_pica_chant_0").attenuationDistance(48),
                        sound("caelumpedion:entity/corvidae/pica_pica_chant_1").attenuationDistance(48),
                        sound("caelumpedion:entity/corvidae/pica_pica_chant_2").attenuationDistance(48),
                        sound("caelumpedion:entity/corvidae/pica_pica_chant_3").attenuationDistance(48)
                )
                .subtitle("sound.subtitle.corvidae.chant")
        );
        add(CaelumpedionSounds.PICA_PICA_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/corvidae/pica_pica_call_0"),
                        sound("caelumpedion:entity/corvidae/pica_pica_call_1"),
                        sound("caelumpedion:entity/corvidae/pica_pica_call_2"),
                        sound("caelumpedion:entity/corvidae/pica_pica_call_3"),
                        sound("caelumpedion:entity/corvidae/pica_pica_call_4")
                )
                .subtitle("sound.subtitle.corvidae.call")
        );
        //===
        add(CaelumpedionSounds.CORVUS_CORVAX_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/corvidae/corvus_corax_chant_0").attenuationDistance(48),
                        sound("caelumpedion:entity/corvidae/corvus_corax_chant_1").attenuationDistance(48),
                        sound("caelumpedion:entity/corvidae/corvus_corax_chant_2").attenuationDistance(48),
                        sound("caelumpedion:entity/corvidae/corvus_corax_chant_3").attenuationDistance(48)
                )
                .subtitle("sound.subtitle.corvidae.chant")
        );
        add(CaelumpedionSounds.CORVUS_CORVAX_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/corvidae/corvus_corax_call_0"),
                        sound("caelumpedion:entity/corvidae/corvus_corax_call_1"),
                        sound("caelumpedion:entity/corvidae/corvus_corax_call_2"),
                        sound("caelumpedion:entity/corvidae/corvus_corax_call_3"),
                        sound("caelumpedion:entity/corvidae/corvus_corax_call_4"),
                        sound("caelumpedion:entity/corvidae/corvus_corax_call_5")
                )
                .subtitle("sound.subtitle.corvidae.call")
        );
        //===
        add(CaelumpedionSounds.CORVUS_MELLORI_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/corvidae/corvus_mellori_chant_0").attenuationDistance(48),
                        sound("caelumpedion:entity/corvidae/corvus_mellori_chant_1").attenuationDistance(48),
                        sound("caelumpedion:entity/corvidae/corvus_mellori_chant_2").attenuationDistance(48)
                )
                .subtitle("sound.subtitle.corvidae.chant")
        );
        add(CaelumpedionSounds.CORVUS_MELLORI_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/corvidae/corvus_mellori_call_0"),
                        sound("caelumpedion:entity/corvidae/corvus_mellori_call_1"),
                        sound("caelumpedion:entity/corvidae/corvus_mellori_call_2"),
                        sound("caelumpedion:entity/corvidae/corvus_mellori_call_3"),
                        sound("caelumpedion:entity/corvidae/corvus_mellori_call_4"),
                        sound("caelumpedion:entity/corvidae/corvus_mellori_call_5")
                )
                .subtitle("sound.subtitle.corvidae.call")
        );
        //===
        add(CaelumpedionSounds.TURDUS_MERULA_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/corvidae/turdus_merula_chant_0").attenuationDistance(48),
                        sound("caelumpedion:entity/corvidae/turdus_merula_chant_1").attenuationDistance(48),
                        sound("caelumpedion:entity/corvidae/turdus_merula_chant_2").attenuationDistance(48),
                        sound("caelumpedion:entity/corvidae/turdus_merula_chant_3").attenuationDistance(48)
                )
                .subtitle("sound.subtitle.corvidae.chant")
        );
        add(CaelumpedionSounds.TURDUS_MERULA_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/corvidae/turdus_merula_call_0"),
                        sound("caelumpedion:entity/corvidae/turdus_merula_call_1"),
                        sound("caelumpedion:entity/corvidae/turdus_merula_call_2"),
                        sound("caelumpedion:entity/corvidae/turdus_merula_call_3"),
                        sound("caelumpedion:entity/corvidae/turdus_merula_call_4"),
                        sound("caelumpedion:entity/corvidae/turdus_merula_call_5")
                )
                .subtitle("sound.subtitle.corvidae.call")
        );

        // ========== HIRUNDININAE ==========

        add(CaelumpedionSounds.DELICHON_URBICUM_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/hirundininae/delichon_urbicum_call_0").attenuationDistance(48),
                        sound("caelumpedion:entity/hirundininae/delichon_urbicum_call_1").attenuationDistance(48),
                        sound("caelumpedion:entity/hirundininae/delichon_urbicum_call_2").attenuationDistance(48),
                        sound("caelumpedion:entity/hirundininae/delichon_urbicum_call_3").attenuationDistance(48),
                        sound("caelumpedion:entity/hirundininae/delichon_urbicum_call_4").attenuationDistance(48),
                        sound("caelumpedion:entity/hirundininae/delichon_urbicum_call_5").attenuationDistance(48),
                        sound("caelumpedion:entity/hirundininae/delichon_urbicum_call_6").attenuationDistance(48),
                        sound("caelumpedion:entity/hirundininae/delichon_urbicum_call_7").attenuationDistance(48),
                        sound("caelumpedion:entity/hirundininae/delichon_urbicum_call_8").attenuationDistance(48)
                )
                .subtitle("sound.subtitle.hirundininae.call")
        );
        //===
        add(CaelumpedionSounds.HIRUNDO_RUSTICA_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/hirundininae/hirundo_rustica_call_0").attenuationDistance(48),
                        sound("caelumpedion:entity/hirundininae/hirundo_rustica_call_1").attenuationDistance(48),
                        sound("caelumpedion:entity/hirundininae/hirundo_rustica_call_2").attenuationDistance(48),
                        sound("caelumpedion:entity/hirundininae/hirundo_rustica_call_3").attenuationDistance(48),
                        sound("caelumpedion:entity/hirundininae/hirundo_rustica_call_4").attenuationDistance(48),
                        sound("caelumpedion:entity/hirundininae/hirundo_rustica_call_5").attenuationDistance(48),
                        sound("caelumpedion:entity/hirundininae/hirundo_rustica_call_6").attenuationDistance(48)
                )
                .subtitle("sound.subtitle.hirundininae.call")
        );

        // ========== PASSERIFORME ==========

        add(CaelumpedionSounds.CARDINALIS_CARDINALIS_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/passeriforme/cardinalis_cardinalis_chant_0"),
                        sound("caelumpedion:entity/passeriforme/cardinalis_cardinalis_chant_1"),
                        sound("caelumpedion:entity/passeriforme/cardinalis_cardinalis_chant_2")
                )
                .subtitle("sound.subtitle.passeriforme.chant")
        );
        add(CaelumpedionSounds.CARDINALIS_CARDINALIS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/passeriforme/cardinalis_cardinalis_call_0"),
                        sound("caelumpedion:entity/passeriforme/cardinalis_cardinalis_call_1"),
                        sound("caelumpedion:entity/passeriforme/cardinalis_cardinalis_call_2"),
                        sound("caelumpedion:entity/passeriforme/cardinalis_cardinalis_call_3")
                )
                .subtitle("sound.subtitle.passeriforme.call")
        );
        //===
        add(CaelumpedionSounds.CYANISTES_CAERULEUS_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/passeriforme/cyanistes_caeruleus_chant_0"),
                        sound("caelumpedion:entity/passeriforme/cyanistes_caeruleus_chant_1"),
                        sound("caelumpedion:entity/passeriforme/cyanistes_caeruleus_chant_2")
                )
                .subtitle("sound.subtitle.passeriforme.chant")
        );
        add(CaelumpedionSounds.CYANISTES_CAERULEUS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/passeriforme/cyanistes_caeruleus_call_0"),
                        sound("caelumpedion:entity/passeriforme/cyanistes_caeruleus_call_1"),
                        sound("caelumpedion:entity/passeriforme/cyanistes_caeruleus_call_2"),
                        sound("caelumpedion:entity/passeriforme/cyanistes_caeruleus_call_3")
                )
                .subtitle("sound.subtitle.passeriforme.call")
        );
        //===
        add(CaelumpedionSounds.ERITHACUS_RUBECULA_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/passeriforme/erithacus_rubecula_chant_0"),
                        sound("caelumpedion:entity/passeriforme/erithacus_rubecula_chant_1"),
                        sound("caelumpedion:entity/passeriforme/erithacus_rubecula_chant_2"),
                        sound("caelumpedion:entity/passeriforme/erithacus_rubecula_chant_3")
                )
                .subtitle("sound.subtitle.passeriforme.chant")
        );
        add(CaelumpedionSounds.ERITHACUS_RUBECULA_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/passeriforme/erithacus_rubecula_call_0"),
                        sound("caelumpedion:entity/passeriforme/erithacus_rubecula_call_1"),
                        sound("caelumpedion:entity/passeriforme/erithacus_rubecula_call_2"),
                        sound("caelumpedion:entity/passeriforme/erithacus_rubecula_call_3")
                )
                .subtitle("sound.subtitle.passeriforme.call")
        );
        //===
        add(CaelumpedionSounds.LOPHOPHANES_CRISTATUS_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/passeriforme/lophophanes_cristatus_chant_0"),
                        sound("caelumpedion:entity/passeriforme/lophophanes_cristatus_chant_1"),
                        sound("caelumpedion:entity/passeriforme/lophophanes_cristatus_chant_2")
                )
                .subtitle("sound.subtitle.passeriforme.chant")
        );
        add(CaelumpedionSounds.LOPHOPHANES_CRISTATUS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/passeriforme/lophophanes_cristatus_call_0"),
                        sound("caelumpedion:entity/passeriforme/lophophanes_cristatus_call_1"),
                        sound("caelumpedion:entity/passeriforme/lophophanes_cristatus_call_2"),
                        sound("caelumpedion:entity/passeriforme/lophophanes_cristatus_call_3"),
                        sound("caelumpedion:entity/passeriforme/lophophanes_cristatus_call_4")
                )
                .subtitle("sound.subtitle.passeriforme.call")
        );
        //===
        add(CaelumpedionSounds.PASSER_DOMESTICUS_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/passeriforme/passer_domesticus_chant_0"),
                        sound("caelumpedion:entity/passeriforme/passer_domesticus_chant_1"),
                        sound("caelumpedion:entity/passeriforme/passer_domesticus_chant_2")
                )
                .subtitle("sound.subtitle.passeriforme.chant")
        );
        add(CaelumpedionSounds.PASSER_DOMESTICUS_CALL, SoundDefinition.definition()
                .with(
                    sound("caelumpedion:entity/passeriforme/passer_domesticus_call_0"),
                    sound("caelumpedion:entity/passeriforme/passer_domesticus_call_1"),
                    sound("caelumpedion:entity/passeriforme/passer_domesticus_call_2"),
                    sound("caelumpedion:entity/passeriforme/passer_domesticus_call_3"),
                    sound("caelumpedion:entity/passeriforme/passer_domesticus_call_4")
                )
                .subtitle("sound.subtitle.passeriforme.call")
        );
        //===
        add(CaelumpedionSounds.PERIPARUS_ATER_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/passeriforme/periparus_ater_chant_0"),
                        sound("caelumpedion:entity/passeriforme/periparus_ater_chant_1"),
                        sound("caelumpedion:entity/passeriforme/periparus_ater_chant_2")
                )
                .subtitle("sound.subtitle.passeriforme.chant")
        );
        add(CaelumpedionSounds.PERIPARUS_ATER_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/passeriforme/periparus_ater_call_0"),
                        sound("caelumpedion:entity/passeriforme/periparus_ater_call_1"),
                        sound("caelumpedion:entity/passeriforme/periparus_ater_call_2"),
                        sound("caelumpedion:entity/passeriforme/periparus_ater_call_3"),
                        sound("caelumpedion:entity/passeriforme/periparus_ater_call_4")
                )
                .subtitle("sound.subtitle.passeriforme.call")
        );
        //===
        add(CaelumpedionSounds.PHOENICURUS_OCHRUROS_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/passeriforme/phoenicurus_ochruros_chant_0"),
                        sound("caelumpedion:entity/passeriforme/phoenicurus_ochruros_chant_1"),
                        sound("caelumpedion:entity/passeriforme/phoenicurus_ochruros_chant_2"),
                        sound("caelumpedion:entity/passeriforme/phoenicurus_ochruros_chant_3"),
                        sound("caelumpedion:entity/passeriforme/phoenicurus_ochruros_chant_4")
                )
                .subtitle("sound.subtitle.passeriforme.chant")
        );
        add(CaelumpedionSounds.PHOENICURUS_OCHRUROS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/passeriforme/phoenicurus_ochruros_call_0"),
                        sound("caelumpedion:entity/passeriforme/phoenicurus_ochruros_call_1"),
                        sound("caelumpedion:entity/passeriforme/phoenicurus_ochruros_call_2"),
                        sound("caelumpedion:entity/passeriforme/phoenicurus_ochruros_call_3"),
                        sound("caelumpedion:entity/passeriforme/phoenicurus_ochruros_call_4")
                )
                .subtitle("sound.subtitle.passeriforme.call")
        );

        // ========== PICIFORME ==========

        add(CaelumpedionSounds.COLAPTES_AURATUS_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/piciforme/colaptes_auratus_call_0"),
                        sound("caelumpedion:entity/piciforme/colaptes_auratus_call_1"),
                        sound("caelumpedion:entity/piciforme/colaptes_auratus_call_2")
                )
                .subtitle("sound.subtitle.piciforme.chant")
        );
        add(CaelumpedionSounds.COLAPTES_AURATUS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/piciforme/colaptes_auratus_call_0"),
                        sound("caelumpedion:entity/piciforme/colaptes_auratus_call_1"),
                        sound("caelumpedion:entity/piciforme/colaptes_auratus_call_2"),
                        sound("caelumpedion:entity/piciforme/colaptes_auratus_call_3")
                )
                .subtitle("sound.subtitle.piciforme.call")
        );
        //===
        add(CaelumpedionSounds.DENDROCOPTES_MEDIUS_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/piciforme/dendrocoptes_medius_call_0"),
                        sound("caelumpedion:entity/piciforme/dendrocoptes_medius_call_1"),
                        sound("caelumpedion:entity/piciforme/dendrocoptes_medius_call_2")
                )
                .subtitle("sound.subtitle.piciforme.chant")
        );
        add(CaelumpedionSounds.DENDROCOPTES_MEDIUS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/piciforme/dendrocoptes_medius_call_0"),
                        sound("caelumpedion:entity/piciforme/dendrocoptes_medius_call_1"),
                        sound("caelumpedion:entity/piciforme/dendrocoptes_medius_call_2"),
                        sound("caelumpedion:entity/piciforme/dendrocoptes_medius_call_3")
                )
                .subtitle("sound.subtitle.piciforme.call")
        );
        //===
        add(CaelumpedionSounds.DENDROCOPOS_MAJOR_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/piciforme/dendrocopos_major_call_0"),
                        sound("caelumpedion:entity/piciforme/dendrocopos_major_call_1"),
                        sound("caelumpedion:entity/piciforme/dendrocopos_major_call_2")
                )
                .subtitle("sound.subtitle.piciforme.call")
        );
        //===
        add(CaelumpedionSounds.PICUS_PHANTASIA_TROGLODYTA_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/piciforme/picus_phantasia_troglodyta_call_0"),
                        sound("caelumpedion:entity/piciforme/picus_phantasia_troglodyta_call_1"),
                        sound("caelumpedion:entity/piciforme/picus_phantasia_troglodyta_call_2")
                )
                .subtitle("sound.subtitle.piciforme.chant")
        );
        add(CaelumpedionSounds.PICUS_PHANTASIA_TROGLODYTA_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/piciforme/picus_phantasia_troglodyta_call_0"),
                        sound("caelumpedion:entity/piciforme/picus_phantasia_troglodyta_call_1"),
                        sound("caelumpedion:entity/piciforme/picus_phantasia_troglodyta_call_2")
                )
                .subtitle("sound.subtitle.piciforme.call")
        );
        //===
        add(CaelumpedionSounds.PICUS_VIRIDIS_CHANT, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/piciforme/picus_viridis_call_0"),
                        sound("caelumpedion:entity/piciforme/picus_viridis_call_1"),
                        sound("caelumpedion:entity/piciforme/picus_viridis_call_2")
                )
                .subtitle("sound.subtitle.piciforme.chant")
        );
        add(CaelumpedionSounds.PICUS_VIRIDIS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/piciforme/picus_viridis_call_0"),
                        sound("caelumpedion:entity/piciforme/picus_viridis_call_1"),
                        sound("caelumpedion:entity/piciforme/picus_viridis_call_2")
                )
                .subtitle("sound.subtitle.piciforme.call")
        );

        // ========== RAMPHASTIDAE ==========

        add(CaelumpedionSounds.RAMPHASTOS_TOCO_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/ramphastidae/ramphastos_toco_call_0"),
                        sound("caelumpedion:entity/ramphastidae/ramphastos_toco_call_1"),
                        sound("caelumpedion:entity/ramphastidae/ramphastos_toco_call_2"),
                        sound("caelumpedion:entity/ramphastidae/ramphastos_toco_call_3")
                )
                .subtitle("sound.subtitle.ramphastidae.call")
        );
        //===
        add(CaelumpedionSounds.RAMPHASTOS_SULFURATUS_CALL, SoundDefinition.definition()
                .with(
                        sound("caelumpedion:entity/ramphastidae/ramphastos_sulfuratus_call_0"),
                        sound("caelumpedion:entity/ramphastidae/ramphastos_sulfuratus_call_1"),
                        sound("caelumpedion:entity/ramphastidae/ramphastos_sulfuratus_call_2")
                )
                .subtitle("sound.subtitle.ramphastidae.call")
        );
    }
}
