package com.kalyptien.caelumpedion.event;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.ModEntities;
import com.kalyptien.caelumpedion.entity.client.accipitridae.AccipitridaeModel;
import com.kalyptien.caelumpedion.entity.client.anseriforme.AnseriformeModel;
import com.kalyptien.caelumpedion.entity.client.columbiforme.ColumbiformeModel;
import com.kalyptien.caelumpedion.entity.client.corvidae.CorvidaeModel;
import com.kalyptien.caelumpedion.entity.client.gruiforme.GruiformeModel;
import com.kalyptien.caelumpedion.entity.client.hirundininae.HirundininaeModel;
import com.kalyptien.caelumpedion.entity.client.passeriforme.PasseriformeModel;
import com.kalyptien.caelumpedion.entity.client.ramphastidae.RamphastidaeModel;
import com.kalyptien.caelumpedion.entity.client.trochilidae.TrochilidaeModel;
import com.kalyptien.caelumpedion.entity.custom.bird.accipitriforme.AccipitridaeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.anseriforme.AnseriformeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.columbiforme.ColumbiformeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.gruiforme.GruiformeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.apodiforme.TrochilidaeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.CorvidaeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.HirundininaeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.PasseriformeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.piciforme.RamphastidaeEntity;
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
        event.registerLayerDefinition(CorvidaeModel.LAYER_LOCATION, CorvidaeModel::createBodyLayer);

        event.registerLayerDefinition(AnseriformeModel.LAYER_LOCATION, AnseriformeModel::createBodyLayer);

        event.registerLayerDefinition(AccipitridaeModel.LAYER_LOCATION, AccipitridaeModel::createBodyLayer);

        event.registerLayerDefinition(RamphastidaeModel.LAYER_LOCATION, RamphastidaeModel::createBodyLayer);

        event.registerLayerDefinition(GruiformeModel.LAYER_LOCATION, GruiformeModel::createBodyLayer);

        event.registerLayerDefinition(TrochilidaeModel.LAYER_LOCATION, TrochilidaeModel::createBodyLayer);

        event.registerLayerDefinition(ColumbiformeModel.LAYER_LOCATION, ColumbiformeModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.PASSERIFORME.get(), PasseriformeEntity.createAttributes().build());
        event.put(ModEntities.HIRUNDININAE.get(), HirundininaeEntity.createAttributes().build());
        event.put(ModEntities.CORVIDAE.get(), CorvidaeEntity.createAttributes().build());

        event.put(ModEntities.ANSERIFORME.get(), AnseriformeEntity.createAttributes().build());

        event.put(ModEntities.ACCIPITRIFORME.get(), AccipitridaeEntity.createAttributes().build());

        event.put(ModEntities.RAMPHASTIDAE.get(), RamphastidaeEntity.createAttributes().build());

        event.put(ModEntities.GRUIFORME.get(), GruiformeEntity.createAttributes().build());

        event.put(ModEntities.TROCHILIDAE.get(), TrochilidaeEntity.createAttributes().build());

        event.put(ModEntities.COLUMBIFORME.get(), ColumbiformeEntity.createAttributes().build());
    }
}
