package com.kalyptien.caelumpedion.item;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.ModEntities;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CaelumpedionMod.MOD_ID);

    // MISC

    public static final DeferredItem<Item> PASSERIFORME_SPAWN_EGG = ITEMS.register("passeriforme_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.PASSERIFORME, 0x453517, 0xcca152,
                    new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
