package com.kalyptien.caelumpedion.event;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.ModEntities;
import com.kalyptien.caelumpedion.entity.client.accipitriforme.AccipitriformeModel;
import com.kalyptien.caelumpedion.entity.client.anseriforme.AnseriformeModel;
import com.kalyptien.caelumpedion.entity.client.gruiforme.GruiformeModel;
import com.kalyptien.caelumpedion.entity.client.hirundininae.HirundininaeModel;
import com.kalyptien.caelumpedion.entity.client.passeriforme.PasseriformeModel;
import com.kalyptien.caelumpedion.entity.client.ramphastidae.RamphastidaeModel;
import com.kalyptien.caelumpedion.entity.custom.AccipitriformeEntity;
import com.kalyptien.caelumpedion.entity.custom.AnseriformeEntity;
import com.kalyptien.caelumpedion.entity.custom.GruiformeEntity;
import com.kalyptien.caelumpedion.entity.custom.passeriforme.PasseriformeEntity;
import com.kalyptien.caelumpedion.entity.custom.piciforme.RamphastidaeEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = CaelumpedionMod.MOD_ID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PasseriformeModel.LAYER_LOCATION, PasseriformeModel::createBodyLayer);
        event.registerLayerDefinition(HirundininaeModel.LAYER_LOCATION, HirundininaeModel::createBodyLayer);
        event.registerLayerDefinition(AnseriformeModel.LAYER_LOCATION, AnseriformeModel::createBodyLayer);
        event.registerLayerDefinition(AccipitriformeModel.LAYER_LOCATION, AccipitriformeModel::createBodyLayer);
        event.registerLayerDefinition(RamphastidaeModel.LAYER_LOCATION, RamphastidaeModel::createBodyLayer);
        event.registerLayerDefinition(GruiformeModel.LAYER_LOCATION, GruiformeModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.PASSERIFORME.get(), PasseriformeEntity.createAttributes().build());
        event.put(ModEntities.HIRUNDININAE.get(), PasseriformeEntity.createAttributes().build());
        event.put(ModEntities.ANSERIFORME.get(), AnseriformeEntity.createAttributes().build());
        event.put(ModEntities.ACCIPITRIFORME.get(), AccipitriformeEntity.createAttributes().build());
        event.put(ModEntities.RAMPHASTIDAE.get(), RamphastidaeEntity.createAttributes().build());
        event.put(ModEntities.GRUIFORME.get(), GruiformeEntity.createAttributes().build());
    }
}
