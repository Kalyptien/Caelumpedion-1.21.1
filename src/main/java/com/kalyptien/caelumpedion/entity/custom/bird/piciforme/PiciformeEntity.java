package com.kalyptien.caelumpedion.entity.custom.bird.piciforme;

import com.kalyptien.caelumpedion.entity.custom.common.FlyingBirdEntity;
import com.kalyptien.caelumpedion.misc.CaelumpedionSounds;
import com.kalyptien.caelumpedion.misc.CaelumpedionTags;
import com.kalyptien.caelumpedion.util.IRLRegion;
import com.kalyptien.caelumpedion.util.FeatherColor;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;

public class PiciformeEntity extends FlyingBirdEntity {

    protected static final EntityDataAccessor<Boolean> NEED_PECK_ANIM =
            SynchedEntityData.defineId(PiciformeEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState peckAnimationState = new AnimationState();
    protected int peckAnimationTimein = 0;

    protected BlockPos blockPosPecked;

    public PiciformeEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

        this.setSizeBirdType(SizeBirdType.SMALL);

        this.flyRange = 100;
        this.flyHeight = 30;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 12d)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FLYING_SPEED, 3.0D)
                .add(Attributes.ARMOR, 0d)
                .add(Attributes.FOLLOW_RANGE, 16D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        // Goal
        // this.goalSelector.addGoal(10, new PiciformePeckNerbyGoal(this));
    }

    @Override
    protected void updateGravity(){
        super.updateGravity();
        /*if (this.isFlying()) {
            this.setNoGravity(true);
            if (this.isLandNavigator) {
                switchNavigator(false);
            }
        } else {
            if(this.isNeedPeckAnimation()){
                this.setNoGravity(true);
                if (!this.isLandNavigator) {
                    switchNavigator(true);
                }
            }
            else{
                this.setNoGravity(false);
                if (!this.isLandNavigator) {
                    switchNavigator(true);
                }
            }
        }*/
    }

    //Anim

    @Override
    protected void setupAnimationStates() {
        /*if (this.isNeedPeckAnimation() && blockPosPecked != null) {
            this.setPeckAnimation();
        }*/

        super.setupAnimationStates();
    }

    protected void setPeckAnimation(){
        if(!this.peckAnimationState.isStarted()){
            this.peckAnimationState.start(this.tickCount);
            this.setOnAnimation(true);
            this.peckAnimationTimein = 0;
        }
        else{
            this.peckAnimationTimein++;
            this.getLookControl().setLookAt(blockPosPecked.getX(), blockPosPecked.getY(), blockPosPecked.getZ());

            if(peckAnimationTimein % 70 == 0){
                if((this.random.nextInt(100)/100.0f) <= 0.25){
                    BlockState blockState = this.level().getBlockState(blockPosPecked);
                    if (blockState.is(Blocks.POINTED_DRIPSTONE)){
                        this.level().removeBlock(this.blockPosPecked, false);
                    }
                    else if(blockState.is(BlockTags.LOGS)){
                        BlockState strippedBlockState = AxeItem.getAxeStrippingState(blockState);

                        if(strippedBlockState != null){
                            this.level().setBlock(this.blockPosPecked, strippedBlockState, 11);
                        }
                    }

                    this.resetAnimations();
                    this.setNeedToFlyAway(true);
                }
                else{
                    //Son en fonction du block
                }
            }
        }
    }

    @Override
    public void resetAnimations() {
        /*if(this.peckAnimationState.isStarted()){
            this.peckAnimationState.stop();
        }

        this.peckAnimationTimein = 0;
        this.blockPosPecked = null;
        this.setNeedPeckAnimation(false);*/

        super.resetAnimations();
    }

    //Getter / Setter

    public int getIdVariant() {
        return this.entityData.get(VARIANT);
    }

    public PiciformeVariant getVariant() {
        return PiciformeVariant.byId(this.getIdVariant());
    }

    public void setVariant(PiciformeVariant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    public boolean isNeedPeckAnimation() {
        return this.entityData.get(NEED_PECK_ANIM);
    }

    public void setNeedPeckAnimation(boolean needPeckAnimation) {
        this.entityData.set(NEED_PECK_ANIM, needPeckAnimation);
    }

    public BlockPos getBlockPosPecked() {
        return blockPosPecked;
    }

    public void setBlockPosPecked(BlockPos blockPosPecked) {
        this.blockPosPecked = blockPosPecked;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(NEED_PECK_ANIM, false);
    }

    // SPAWN

    protected void setRandomVariant(){
        PiciformeVariant variant = Util.getRandom(PiciformeVariant.values(), this.random);

        this.setVariant(variant);
    }

    protected boolean setVariantFromIrlRegionBiome(Holder<Biome> holder, IRLRegion IrlRegionKey, int randomInt){
        PiciformeVariant variant = PiciformeVariant.byIrlRegionBiome(holder, IrlRegionKey, randomInt);

        if(variant != null){
            this.setVariant(variant);
            return true;
        }
        else{
            return false;
        }
    };

    protected boolean setVariantFromBiome(Holder<Biome> holder, int randomInt){
        PiciformeVariant variant = PiciformeVariant.byBiome(holder, randomInt);

        if(variant != null){
            this.setVariant(variant);
            return true;
        }
        else{
            return false;
        }
    };

    protected boolean setVariantFromIrlRegion(IRLRegion IrlRegionKey, int randomInt){
        PiciformeVariant variant = PiciformeVariant.byRegion(IrlRegionKey, randomInt);

        if(variant != null){
            this.setVariant(variant);
            return true;
        }
        else{
            return false;
        }
    }

    protected void setVariantFromId(int id){
        PiciformeVariant variant = PiciformeVariant.byId(id);

        this.setVariant(variant);
    }

    protected int getVariantLength(){
        return PiciformeVariant.lenght();
    };

    // Sound

    protected SoundEvent getCallSound(){
        int id = this.getIdVariant();
        SoundEvent sound = null;

        switch (id){
            case 0:
                sound = CaelumpedionSounds.COLAPTES_AURATUS_CALL.get();
                break;
            case 1:
                sound = CaelumpedionSounds.DENDROCOPOS_MAJOR_CALL.get();
                break;
            case 2:
                sound = CaelumpedionSounds.DENDROCOPTES_MEDIUS_CALL.get();
                break;
            case 3:
                break;
            case 4:
                sound = CaelumpedionSounds.PICUS_VIRIDS_CALL.get();
                break;
            default:
                sound = CaelumpedionSounds.PICUS_VIRIDS_CALL.get();
        }

        return sound;
    }

    protected SoundEvent getChantSound(){
        int id = this.getIdVariant();
        SoundEvent sound = null;

        switch (id){
            case 0:
                sound = CaelumpedionSounds.COLAPTES_AURATUS_CHANT.get();
                break;
            case 1:
                sound = CaelumpedionSounds.DENDROCOPOS_MAJOR_CALL.get();
                break;
            case 2:
                sound = CaelumpedionSounds.DENDROCOPTES_MEDIUS_CHANT.get();
                break;
            case 3:
                break;
            case 4:
                sound = CaelumpedionSounds.PICUS_VIRIDS_CHANT.get();
                break;
            default:
                sound = CaelumpedionSounds.PICUS_VIRIDS_CHANT.get();
        }

        return sound;
    }

    // Variant

    public static enum PiciformeVariant implements BirdVariant {
        ColaptesAuratus(0, "colaptes_auratus", 1.0f, false, new FeatherColor[]{FeatherColor.Brown, FeatherColor.Black}, FeatherColor.Brown, new IRLRegion[]{IRLRegion.NorthAmerica}, null),
        DendrocoposMajor(1, "dendrocopos_major", 1.0f, false, new FeatherColor[]{FeatherColor.Black, FeatherColor.White}, FeatherColor.Black, new IRLRegion[]{IRLRegion.Europe, IRLRegion.Asia, IRLRegion.NorthAfrica}, null),
        DendrocoptesMedius(2, "dendrocoptes_medius", 1.0f, false, new FeatherColor[]{FeatherColor.Black, FeatherColor.White}, FeatherColor.Black, new IRLRegion[]{IRLRegion.Europe}, null),
        PicusPhantasiaTroglodyta(3, "picus_phantasia_troglodyta", 1.0f, false, new FeatherColor[]{FeatherColor.Brown, FeatherColor.Gray}, FeatherColor.Gray, new IRLRegion[]{IRLRegion.Europe, IRLRegion.NorthAmerica, IRLRegion.SouthAmerica, IRLRegion.Oceania, IRLRegion.Asia, IRLRegion.SouthAfrica, IRLRegion.NorthAfrica}, CaelumpedionTags.Biomes.IS_CAVE),
        PicusViridis(4, "picus_viridis", 1.0f, false, new FeatherColor[]{FeatherColor.Lime, FeatherColor.White}, FeatherColor.Green, new IRLRegion[]{IRLRegion.Europe}, null),
        ;

        private static final PiciformeVariant[] BY_ID = Arrays.stream(values()).sorted(
                Comparator.comparingInt(PiciformeVariant::getId)).toArray(PiciformeVariant[]::new);

        private final int id;
        private final String fileName;
        private final float size;
        private final boolean isIridescent;
        private final FeatherColor[] featherColor;
        private final FeatherColor childFeatherColor;
        private final IRLRegion[] IRLRegions;
        @Nullable
        private final TagKey<Biome> biomeTagKey;

        PiciformeVariant(int id, String fileName, float size,boolean isIridescent, FeatherColor[] featherColor, FeatherColor childFeatherColor, IRLRegion[] IRLRegions, TagKey<Biome> biomeTagKey) {
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

        public static PiciformeVariant byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static PiciformeVariant byIrlRegionBiome(Holder<Biome> holder, IRLRegion region, int id) {
            PiciformeVariant[] BY_BIOME = Arrays.stream(values())
                    .filter(variant -> variant.getBiomeTagKey() != null && holder.is(variant.getBiomeTagKey())).toArray(PiciformeVariant[]::new);

            PiciformeVariant[] BY_BIOMEREGION = Arrays.stream(BY_BIOME)
                    .filter(variant -> Arrays.stream(variant.getBiomeRegion())
                            .anyMatch(irlRegion -> irlRegion == region)).toArray(PiciformeVariant[]::new);

            if(BY_BIOMEREGION.length != 0){
                return BY_BIOMEREGION[id % BY_BIOMEREGION.length];
            }
            else{
                return null;
            }
        }

        public static PiciformeVariant byRegion(IRLRegion region, int id) {
            PiciformeVariant[] BY_REGION = Arrays.stream(values())
                    .filter(variant -> Arrays.stream(variant.getBiomeRegion())
                            .anyMatch(irlRegion -> irlRegion == region)).toArray(PiciformeVariant[]::new);

            if(BY_REGION.length != 0){
                return BY_REGION[id % BY_REGION.length];

            }
            else{
                return null;
            }
        }

        public static PiciformeVariant byBiome(Holder<Biome> holder, int id) {
            PiciformeVariant[] BY_BIOME = Arrays.stream(values())
                    .filter(variant -> variant.getBiomeTagKey() != null && holder.is(variant.getBiomeTagKey())).toArray(PiciformeVariant[]::new);

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
