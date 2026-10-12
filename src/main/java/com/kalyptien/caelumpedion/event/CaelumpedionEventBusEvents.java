package com.kalyptien.caelumpedion.event;

import com.kalyptien.caelumpedion.CaelumpedionMod;
import com.kalyptien.caelumpedion.entity.CaelumpedionEntities;
import com.kalyptien.caelumpedion.entity.client.accipitridae.AccipitridaeModel;
import com.kalyptien.caelumpedion.entity.client.anseriforme.AnseriformeModel;
import com.kalyptien.caelumpedion.entity.client.columbiforme.ColumbiformeModel;
import com.kalyptien.caelumpedion.entity.client.corvidae.CorvidaeModel;
import com.kalyptien.caelumpedion.entity.client.gruiforme.GruiformeModel;
import com.kalyptien.caelumpedion.entity.client.hirundininae.HirundininaeModel;
import com.kalyptien.caelumpedion.entity.client.passeriforme.PasseriformeModel;
import com.kalyptien.caelumpedion.entity.client.piciforme.PiciformeModel;
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
import com.kalyptien.caelumpedion.entity.custom.bird.piciforme.PiciformeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.piciforme.RamphastidaeEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = CaelumpedionMod.MOD_ID)
public class CaelumpedionEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PasseriformeModel.LAYER_LOCATION, PasseriformeModel::createBodyLayer);
        event.registerLayerDefinition(HirundininaeModel.LAYER_LOCATION, HirundininaeModel::createBodyLayer);
        event.registerLayerDefinition(CorvidaeModel.LAYER_LOCATION, CorvidaeModel::createBodyLayer);

        event.registerLayerDefinition(AnseriformeModel.LAYER_LOCATION, AnseriformeModel::createBodyLayer);

        event.registerLayerDefinition(AccipitridaeModel.LAYER_LOCATION, AccipitridaeModel::createBodyLayer);

        event.registerLayerDefinition(PiciformeModel.LAYER_LOCATION, PiciformeModel::createBodyLayer);
        event.registerLayerDefinition(RamphastidaeModel.LAYER_LOCATION, RamphastidaeModel::createBodyLayer);

        event.registerLayerDefinition(GruiformeModel.LAYER_LOCATION, GruiformeModel::createBodyLayer);

        event.registerLayerDefinition(TrochilidaeModel.LAYER_LOCATION, TrochilidaeModel::createBodyLayer);

        event.registerLayerDefinition(ColumbiformeModel.LAYER_LOCATION, ColumbiformeModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(CaelumpedionEntities.PASSERIFORME.get(), PasseriformeEntity.createAttributes().build());
        event.put(CaelumpedionEntities.HIRUNDININAE.get(), HirundininaeEntity.createAttributes().build());
        event.put(CaelumpedionEntities.CORVIDAE.get(), CorvidaeEntity.createAttributes().build());

        event.put(CaelumpedionEntities.ANSERIFORME.get(), AnseriformeEntity.createAttributes().build());

        event.put(CaelumpedionEntities.ACCIPITRIFORME.get(), AccipitridaeEntity.createAttributes().build());

        event.put(CaelumpedionEntities.PICIFORME.get(), PiciformeEntity.createAttributes().build());
        event.put(CaelumpedionEntities.RAMPHASTIDAE.get(), RamphastidaeEntity.createAttributes().build());

        event.put(CaelumpedionEntities.GRUIFORME.get(), GruiformeEntity.createAttributes().build());

        event.put(CaelumpedionEntities.TROCHILIDAE.get(), TrochilidaeEntity.createAttributes().build());

        event.put(CaelumpedionEntities.COLUMBIFORME.get(), ColumbiformeEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(CaelumpedionEntities.PASSERIFORME.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CaelumpedionEntities.HIRUNDININAE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CaelumpedionEntities.CORVIDAE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(CaelumpedionEntities.ANSERIFORME.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(CaelumpedionEntities.ACCIPITRIFORME.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(CaelumpedionEntities.PICIFORME.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CaelumpedionEntities.RAMPHASTIDAE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(CaelumpedionEntities.GRUIFORME.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(CaelumpedionEntities.TROCHILIDAE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(CaelumpedionEntities.COLUMBIFORME.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
