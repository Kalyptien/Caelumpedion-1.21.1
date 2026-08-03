package com.kalyptien.caelumpedion.block.entity;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CaelumpedionMod.MOD_ID);

    public static final Supplier<BlockEntityType<BirdFeederBlockEntity>> BIRD_FEEDER_BE =
            BLOCK_ENTITIES.register("bird_feeder_be", () -> BlockEntityType.Builder.of(
                    BirdFeederBlockEntity::new, ModBlocks.BIRD_FEEDER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
