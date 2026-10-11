package com.kalyptien.caelumpedion.event;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.item.CaelumpedionItems;
import com.kalyptien.caelumpedion.item.custom.FeatherItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = CaelumpedionMod.MOD_ID)
public class CaelumpedionClientBusEvents {

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event){
        // POTIONS
        event.register(FeatherItem::getFeatherColor,
                CaelumpedionItems.LIGHT_GRAY_FEATHER.get(),
                CaelumpedionItems.GRAY_FEATHER.get(),
                CaelumpedionItems.BLACK_FEATHER.get(),
                CaelumpedionItems.BROWN_FEATHER.get(),
                CaelumpedionItems.RED_FEATHER.get(),
                CaelumpedionItems.ORANGE_FEATHER.get(),
                CaelumpedionItems.YELLOW_FEATHER.get(),
                CaelumpedionItems.LIME_FEATHER.get(),
                CaelumpedionItems.GREEN_FEATHER.get(),
                CaelumpedionItems.LIGHT_BLUE_FEATHER.get(),
                CaelumpedionItems.CYAN_FEATHER.get(),
                CaelumpedionItems.BLUE_FEATHER.get(),
                CaelumpedionItems.PURPLE_FEATHER.get(),
                CaelumpedionItems.MAGENTA_FEATHER.get(),
                CaelumpedionItems.PINK_FEATHER.get()
        );
    }
}
