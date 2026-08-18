package com.kalyptien.caelumpedion.event;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.ModEntities;
import com.kalyptien.caelumpedion.entity.client.anseriforme.AnseriformeModel;
import com.kalyptien.caelumpedion.entity.client.passeriforme.PasseriformeModel;
import com.kalyptien.caelumpedion.entity.custom.AnseriformeEntity;
import com.kalyptien.caelumpedion.entity.custom.PasseriformeEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = CaelumpedionMod.MOD_ID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PasseriformeModel.LAYER_LOCATION, PasseriformeModel::createBodyLayer);
        event.registerLayerDefinition(AnseriformeModel.LAYER_LOCATION, AnseriformeModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.PASSERIFORME.get(), PasseriformeEntity.createAttributes().build());
        event.put(ModEntities.ANSERIFORME.get(), AnseriformeEntity.createAttributes().build());
    }
}
