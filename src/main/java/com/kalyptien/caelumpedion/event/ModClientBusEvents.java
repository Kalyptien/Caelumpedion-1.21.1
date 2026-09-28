package com.kalyptien.caelumpedion.event;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.item.ModItems;
import com.kalyptien.caelumpedion.item.custom.FeatherItem;
import com.kalyptien.caelumpedion.util.FeatherColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = CaelumpedionMod.MOD_ID)
public class ModClientBusEvents {

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event){
        // POTIONS
        event.register(FeatherItem::getFeatherColor,
                ModItems.LIGHT_GRAY_FEATHER.get(),
                ModItems.GRAY_FEATHER.get(),
                ModItems.BLACK_FEATHER.get(),
                ModItems.BROWN_FEATHER.get(),
                ModItems.RED_FEATHER.get(),
                ModItems.ORANGE_FEATHER.get(),
                ModItems.YELLOW_FEATHER.get(),
                ModItems.LIME_FEATHER.get(),
                ModItems.GREEN_FEATHER.get(),
                ModItems.LIGHT_BLUE_FEATHER.get(),
                ModItems.CYAN_FEATHER.get(),
                ModItems.BLUE_FEATHER.get(),
                ModItems.PURPLE_FEATHER.get(),
                ModItems.MAGENTA_FEATHER.get(),
                ModItems.PINK_FEATHER.get()
        );
    }
}
