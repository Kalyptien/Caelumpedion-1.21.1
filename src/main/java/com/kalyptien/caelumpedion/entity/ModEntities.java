package com.kalyptien.caelumpedion.entity;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.custom.AnatidaeEntity;
import com.kalyptien.caelumpedion.entity.custom.PasseriformeEntity;
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

    public static final Supplier<EntityType<AnatidaeEntity>> ANATIDAE =
            ENTITY_TYPES.register("anatidae", () -> {
                return EntityType.Builder.of(AnatidaeEntity::new, MobCategory.CREATURE)
                        .sized(0.6f, 0.7f).build("anatidae");
            });


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
