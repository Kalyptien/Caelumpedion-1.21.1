package com.kalyptien.caelumpedion.entity.custom;

import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import net.minecraft.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;

public class AnseriformeEntity extends SocialFlyingBirdEntity {

    public AnseriformeEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

        this.setFlyingBirdType(FlyingBirdType.WALKER);
        this.setAquaticBirdType(AquaticBirdType.FULL);
        this.setBOIDBirdType(BOIDType.FORMATION);
        this.setFlyPathType(FlyPathType.NEAR_GROUND);

        this.flyRange = 150;
        this.flyHeight = 40;
        this.flySpeed = 3f;

        this.viewRange = 16;

        this.maxSchoolSize = 10;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 12d)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.ARMOR, 0d)
                .add(Attributes.FOLLOW_RANGE, 12D);
    }

    //Food

    @Override
    public boolean isFood(ItemStack itemStack) {
        return super.isFood(itemStack) || itemStack.is(Tags.Items.FOODS_RAW_FISH) || itemStack.is(Tags.Items.FOODS_COOKED_FISH);
    }

    //Getter / Setter

    public int getIdVariant() {
        return this.entityData.get(VARIANT);
    }

    public AnseriformeEntity.AnseriformeVariant getVariant() {
        return AnseriformeEntity.AnseriformeVariant.byId(this.getIdVariant());
    }

    public void setVariant(AnseriformeEntity.AnseriformeVariant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    // SPAWN

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        //Variants
        if(spawnType == MobSpawnType.SPAWN_EGG){
            AnseriformeEntity.AnseriformeVariant variant = Util.getRandom(AnseriformeEntity.AnseriformeVariant.values(), this.random);
            this.setVariant(variant);
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    // Variant

    public static enum AnseriformeVariant {
        // Duck
        AnasPlatyrhynchos(0, "anas_platyrhynchos", 1f),
        CairinaMoschata(1, "cairina_moschata", 1f),
        AnasErythrorhyncha(2, "anas_erythrorhyncha", 1f),
        PhantasiaParvusAlbus(3,"phantasia_parvus_albus", 0.7f),

        // Goose
        BrantaHutchinsii(4, "branta_hutchinsii", 1.3f),
        AnserAnser(5, "anser_anser", 1.3f),
        //AnserCaerulescens(6, "anser_caerulescens", 1.3f),
        //AnserCanagicus(7, "anser_canagicus", 1.3f),
        //BrantaRuficollis(8, "branta_ruficollis", 1.3f),

        // Swan
        CygnusOlor(6, "cygnus_olor", 1.5f),
        //CygnusBuccinator(10, "cygnus_buccinator", 1.5f),
        CygnusAtratus(7,"cygnus_atratus", 1.5f)
        ;

        private static final AnseriformeEntity.AnseriformeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(AnseriformeEntity.AnseriformeVariant::getId)).toArray(AnseriformeEntity.AnseriformeVariant[]::new);
        private final int id;
        private final String fileName;
        private final float sizeModifier;

        AnseriformeVariant(int id, String fileName, float sizeModifier) {
            this.id = id;
            this.fileName = fileName;
            this.sizeModifier = sizeModifier;
        }

        public int getId() {
            return id;
        }

        public String getFileName(){
            return fileName;
        }

        public float getSizeModifier(){
            return sizeModifier;
        }

        public static AnseriformeEntity.AnseriformeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static int lenght(){
            return BY_ID.length;
        }
    }
}
