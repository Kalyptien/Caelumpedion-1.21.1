package com.kalyptien.caelumpedion.entity;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.bird.accipitriforme.AccipitridaeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.anseriforme.AnseriformeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.columbiforme.ColumbiformeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.gruiforme.GruiformeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.apodiforme.TrochilidaeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.CorvidaeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.HirundininaeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.PasseriformeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.piciforme.RamphastidaeEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, CaelumpedionMod.MOD_ID);

    public static final Supplier<EntityType<PasseriformeEntity>> PASSERIFORME =
            ENTITY_TYPES.register("passeriforme", () -> {
                return EntityType.Builder.of(PasseriformeEntity::new, MobCategory.CREATURE)
                        .sized(0.4f, 0.4f).build("passeriforme");
            });

    public static final Supplier<EntityType<HirundininaeEntity>> HIRUNDININAE =
            ENTITY_TYPES.register("hirundininae", () -> {
                return EntityType.Builder.of(HirundininaeEntity::new, MobCategory.CREATURE)
                        .sized(0.4f, 0.4f).build("hirundininae");
            });

    public static final Supplier<EntityType<CorvidaeEntity>> CORVIDAE =
            ENTITY_TYPES.register("corvidae", () -> {
                return EntityType.Builder.of(CorvidaeEntity::new, MobCategory.CREATURE)
                        .sized(0.6f, 0.6f).build("corvidae");
            });

    // =====

    public static final Supplier<EntityType<RamphastidaeEntity>> RAMPHASTIDAE =
            ENTITY_TYPES.register("ramphastidae", () -> {
                return EntityType.Builder.of(RamphastidaeEntity::new, MobCategory.CREATURE)
                        .sized(0.6f, 0.7f).build("ramphastidae");
            });

    // =====

    public static final Supplier<EntityType<AnseriformeEntity>> ANSERIFORME =
            ENTITY_TYPES.register("anseriforme", () -> {
                return EntityType.Builder.of(AnseriformeEntity::new, MobCategory.CREATURE)
                        .sized(0.6f, 0.7f).build("anseriforme");
            });

    // =====

    public static final Supplier<EntityType<AccipitridaeEntity>> ACCIPITRIFORME =
            ENTITY_TYPES.register("accipitriforme", () -> {
                return EntityType.Builder.of(AccipitridaeEntity::new, MobCategory.CREATURE)
                        .sized(0.9f, 1f).build("accipitriforme");
            });

    // =====

    public static final Supplier<EntityType<GruiformeEntity>> GRUIFORME =
            ENTITY_TYPES.register("gruiforme", () -> {
                return EntityType.Builder.of(GruiformeEntity::new, MobCategory.CREATURE)
                        .sized(0.6f, 1.7f).build("gruiforme");
            });

    // =====

    public static final Supplier<EntityType<TrochilidaeEntity>> TROCHILIDAE =
            ENTITY_TYPES.register("trochilidae", () -> {
                return EntityType.Builder.of(TrochilidaeEntity::new, MobCategory.CREATURE)
                        .sized(0.4f, 0.4f).build("trochilidae");
            });

    // =====

    public static final Supplier<EntityType<ColumbiformeEntity>> COLUMBIFORME =
            ENTITY_TYPES.register("columbiforme", () -> {
                return EntityType.Builder.of(ColumbiformeEntity::new, MobCategory.CREATURE)
                        .sized(0.6f, 0.6f).build("columbiforme");
            });

    // =====


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
