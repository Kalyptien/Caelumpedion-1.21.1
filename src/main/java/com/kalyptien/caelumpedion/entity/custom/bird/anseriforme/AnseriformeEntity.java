package com.kalyptien.caelumpedion.entity.custom.bird.anseriforme;

import com.kalyptien.caelumpedion.entity.custom.bird.passeriforme.PasseriformeEntity;
import com.kalyptien.caelumpedion.entity.custom.bird.piciforme.RamphastidaeEntity;
import com.kalyptien.caelumpedion.entity.custom.common.SocialFlyingBirdEntity;
import com.kalyptien.caelumpedion.util.IRLRegion;
import com.kalyptien.caelumpedion.util.FeatherColor;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.TagKey;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;

public class AnseriformeEntity extends SocialFlyingBirdEntity implements NeutralMob {

    //Anger Var

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME =
            SynchedEntityData.defineId(AnseriformeEntity.class, EntityDataSerializers.INT);;
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);;
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;

    public AnseriformeEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

        this.setFlyingBirdType(FlyingBirdType.WALKER);
        this.setAquaticBirdType(AquaticBirdType.FULL);
        this.setBOIDBirdType(BOIDType.FORMATION);
        this.setFlyPathType(FlyPathType.NORMAL);

        this.flyRange = 150;
        this.flyHeight = 40;

        this.maxSchoolSize = 10;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 12d)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FLYING_SPEED, 3.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0d)
                .add(Attributes.ARMOR, 0d)
                .add(Attributes.FOLLOW_RANGE, 16D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        //Goal

        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.5, true));

        // Target

        this.targetSelector.addGoal(4, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,
                (target) -> {
                    return this.isAngryAt((LivingEntity) target);
                }));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_REMAINING_ANGER_TIME, 0);
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

    protected void setRandomVariant(){
        AnseriformeVariant variant = Util.getRandom(AnseriformeVariant.values(), this.random);

        this.setVariant(variant);
    }

    protected boolean setVariantFromIrlRegionBiome(Holder<Biome> holder, IRLRegion IrlRegionKey, int randomInt){
        AnseriformeVariant variant = AnseriformeVariant.byIrlRegionBiome(holder, IrlRegionKey, randomInt);

        if(variant != null){
            this.setVariant(variant);
            return true;
        }
        else{
            return false;
        }
    };

    protected boolean setVariantFromBiome(Holder<Biome> holder, int randomInt){
        AnseriformeVariant variant = AnseriformeVariant.byBiome(holder, randomInt);

        if(variant != null){
            this.setVariant(variant);
            return true;
        }
        else{
            return false;
        }
    };

    protected boolean setVariantFromIrlRegion(IRLRegion IrlRegionKey, int randomInt){
        AnseriformeVariant variant = AnseriformeVariant.byRegion(IrlRegionKey, randomInt);

        if(variant != null){
            this.setVariant(variant);
            return true;
        }
        else{
            return false;
        }
    }

    protected void setVariantFromId(int id){
        AnseriformeVariant variant = AnseriformeVariant.byId(id);

        this.setVariant(variant);
    }

    protected int getVariantLength(){
        return AnseriformeVariant.lenght();
    };

    //ANGER

    public int getRemainingPersistentAngerTime() {
        return (Integer)this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int time) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, time);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(this.PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @javax.annotation.Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@javax.annotation.Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return super.canAttack(target) && !this.isFlying();
    }

    public int getAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setAngerTime(int time) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, time);
    }

    //Data

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.addPersistentAngerSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.readPersistentAngerSaveData(this.level(), compound);
    }

    // Variant

    public static enum AnseriformeVariant implements BirdVariant {
        // Duck
        AnasErythrorhyncha(0, "anas_erythrorhyncha", 1.0f, false, new FeatherColor[]{FeatherColor.Brown, FeatherColor.White}, FeatherColor.Brown, new IRLRegion[]{IRLRegion.SouthAfrica}, null),
        AnasPlatyrhynchos(1, "anas_platyrhynchos", 1.0f, true, new FeatherColor[]{FeatherColor.Gray, FeatherColor.Brown, FeatherColor.Green}, FeatherColor.Yellow, new IRLRegion[]{IRLRegion.NorthAmerica, IRLRegion.Europe, IRLRegion.NorthAfrica, IRLRegion.Asia, IRLRegion.Oceania}, null),
        CairinaMoschata(2, "cairina_moschata", 1.0f, true, new FeatherColor[]{FeatherColor.Black}, FeatherColor.Black, new IRLRegion[]{IRLRegion.SouthAmerica}, null),

        // Goose
        AnserAnser(3, "anser_anser", 1.3f, false, new FeatherColor[]{FeatherColor.LightGray, FeatherColor.Brown}, FeatherColor.Brown, new IRLRegion[]{IRLRegion.Europe, IRLRegion.Asia}, null),
        BrantaHutchinsii(4, "branta_hutchinsii", 1.3f, false, new FeatherColor[]{FeatherColor.Black,FeatherColor.White, FeatherColor.Brown}, FeatherColor.Brown, new IRLRegion[]{IRLRegion.NorthAmerica}, null),

        // Swan
        CygnusAtratus(5,"cygnus_atratus", 1.5f, false, new FeatherColor[]{FeatherColor.Black}, FeatherColor.White, new IRLRegion[]{IRLRegion.Oceania}, null),
        CygnusOlor(6, "cygnus_olor", 1.5f, false, new FeatherColor[]{FeatherColor.White}, FeatherColor.White, new IRLRegion[]{IRLRegion.Europe, IRLRegion.Asia}, null),
        ;

        private static final AnseriformeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(AnseriformeVariant::getId)).toArray(AnseriformeVariant[]::new);

        private final int id;
        private final String fileName;
        private final float size;
        private final boolean isIridescent;
        private final FeatherColor[] featherColor;
        private final FeatherColor childFeatherColor;
        private final IRLRegion[] IRLRegions;
        @Nullable
        private final TagKey<Biome> biomeTagKey;

        AnseriformeVariant(int id, String fileName, float size,boolean isIridescent, FeatherColor[] featherColor, FeatherColor childFeatherColor, IRLRegion[] IRLRegions, TagKey<Biome> biomeTagKey) {
            this.id = id;
            this.fileName = fileName;
            this.size = size;
            this.isIridescent = isIridescent;
            this.featherColor = featherColor;
            this.childFeatherColor = childFeatherColor;
            this.IRLRegions = IRLRegions;
            this.biomeTagKey = biomeTagKey;
        }

        public int getId() {
            return id;
        }

        public String getFileName(){
            return fileName;
        }

        @Override
        public float getSize() {
            return size;
        }

        @Override
        public boolean isIridescent() {
            return isIridescent;
        }

        @Override
        public FeatherColor[] getFeatherColors() {
            return featherColor;
        }

        public FeatherColor getFeatherColor(int id){
            return this.featherColor[id % this.featherColor.length];
        }

        @Override
        public FeatherColor getChildFeatherColor() {
            return childFeatherColor;
        }

        @Override
        public IRLRegion[] getBiomeRegion() {
            return IRLRegions;
        }

        @Override
        public TagKey<Biome> getBiomeTagKey() {
            return biomeTagKey;
        }

        public static AnseriformeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static AnseriformeVariant byIrlRegionBiome(Holder<Biome> holder, IRLRegion region, int id) {
            AnseriformeVariant[] BY_BIOME = Arrays.stream(values())
                    .filter(variant -> variant.getBiomeTagKey() != null && holder.is(variant.getBiomeTagKey())).toArray(AnseriformeVariant[]::new);

            AnseriformeVariant[] BY_BIOMEREGION = Arrays.stream(BY_BIOME)
                    .filter(variant -> Arrays.stream(variant.getBiomeRegion())
                            .anyMatch(irlRegion -> irlRegion == region)).toArray(AnseriformeVariant[]::new);

            if(BY_BIOMEREGION.length != 0){
                return BY_BIOMEREGION[id % BY_BIOMEREGION.length];
            }
            else{
                return null;
            }
        }

        public static AnseriformeVariant byRegion(IRLRegion region, int id) {
            AnseriformeVariant[] BY_REGION = Arrays.stream(values())
                    .filter(variant -> Arrays.stream(variant.getBiomeRegion())
                            .anyMatch(irlRegion -> irlRegion == region)).toArray(AnseriformeVariant[]::new);

            if(BY_REGION.length != 0){
                return BY_REGION[id % BY_REGION.length];

            }
            else{
                return null;
            }
        }

        public static AnseriformeVariant byBiome(Holder<Biome> holder, int id) {
            AnseriformeVariant[] BY_BIOME = Arrays.stream(values())
                    .filter(variant -> variant.getBiomeTagKey() != null && holder.is(variant.getBiomeTagKey())).toArray(AnseriformeVariant[]::new);

            if(BY_BIOME.length != 0){
                return BY_BIOME[id % BY_BIOME.length];
            }
            else{
                return null;
            }
        }

        public static int lenght(){
            return BY_ID.length;
        }
    }
}
