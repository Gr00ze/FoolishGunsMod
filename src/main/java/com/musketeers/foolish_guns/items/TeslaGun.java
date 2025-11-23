package com.musketeers.foolish_guns.items;

import com.musketeers.foolish_guns.GunParticles;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animatable.processing.AnimationTest;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.util.Optional;

public class TeslaGun extends ExtendedGeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation CHARGE_ANIMATION = RawAnimation.begin().thenPlay("animation.gun_model.tesla.charge");
    private static final RawAnimation DISCHARGE_ANIMATION = RawAnimation.begin().thenPlay("animation.gun_model.tesla.discharge");
    private static final String controllerName = "Gun_Controller";
    private static final String chargeAnimationName = "charge";
    private static final String dischargeAnimationName = "discharge";
    private boolean isLoading = false;
    private ServerLevel currentLevel;
    private Player currentPlayer;
    private InteractionHand currentHand;
    private int holdTime = 0;
    private static int maxDamage = 10;
    private boolean speed = true;

    public TeslaGun(Properties properties) {
        super(properties);
        GeoItem.registerSyncedAnimatable(this);
    }

    public static Item.Properties getItemProperties(){
        return new Item.Properties().stacksTo(1).useCooldown(0.5F).durability(maxDamage);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(controllerName, 5, this::animationHandler)
                .triggerableAnim(chargeAnimationName, CHARGE_ANIMATION)
                .triggerableAnim(dischargeAnimationName, DISCHARGE_ANIMATION)
        );
    }

    private PlayState animationHandler(AnimationTest<TeslaGun> animationTest) {
        //animationTest.controller().
        return PlayState.CONTINUE;
    }



    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }



    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand hand) {
        //player.setPose(Pose.SHOOTING);
        if (!(level instanceof ServerLevel serverLevel) || isLoading || player.getItemInHand(hand).getDamageValue() == maxDamage) return InteractionResult.FAIL;
        isLoading = true;

        this.currentLevel = serverLevel;
        this.currentPlayer = player;
        this.currentHand = hand;
        //player hand

        //animation
        triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(hand), serverLevel), controllerName, chargeAnimationName);
        //charge sound
        currentLevel.playSound(null,currentPlayer.getX(),currentPlayer.getY(),currentPlayer.getZ(),SoundEvents.WITHER_AMBIENT, SoundSource.PLAYERS, 2F, 0.5F);
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int holdTime) {
        this.holdTime = holdTime;
        if(currentLevel !=null && currentPlayer !=null && currentHand!=null){
            triggerAnim(currentPlayer, GeoItem.getOrAssignId(currentPlayer.getItemInHand(currentHand), currentLevel), controllerName, dischargeAnimationName);
            //speed = !speed;
            shoot();
            if (!currentPlayer.isCreative())
                itemStack.setDamageValue(itemStack.getDamageValue()+1);
        }
        return super.releaseUsing(itemStack, level, livingEntity, holdTime);
    }
    public void shoot(){
        //TODO double handed
        if (!isLoading) return;
        isLoading = false;
        LocalDate localDate = LocalDate.now();
        int dayOfMonth = localDate.getDayOfMonth();
        if(dayOfMonth == 31 && localDate.getMonth() == Month.OCTOBER) {
            this.spawnSpookyParticles(currentLevel, currentPlayer);
            currentLevel.playSound(null, currentPlayer.getX(), currentPlayer.getY(), currentPlayer.getZ(), SoundEvents.BAT_LOOP, SoundSource.PLAYERS, 1F, 2.0F);
        }else if(24 <= dayOfMonth && dayOfMonth <= 25 && localDate.getMonth() == Month.DECEMBER){
            this.spawnParticles(currentLevel,currentPlayer);
            currentLevel.playSound(null,currentPlayer.getX(),currentPlayer.getY(),currentPlayer.getZ(),SoundEvents.SNOW_HIT, SoundSource.PLAYERS, 1F, 0.5F);
        }else{
            this.spawnParticles(currentLevel,currentPlayer);
            //volume 0 - 1 - >1 distance
            //pitch 0.5 - 1 - > 1 faster sound
            currentLevel.playSound(null,currentPlayer.getX(),currentPlayer.getY(),currentPlayer.getZ(),SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 1F, 2.0F);
        }
        this.hitEnemy(currentLevel,currentPlayer);
        }

    private void spawnParticles(ServerLevel level, Player player) {
        //particles

        Vec3 eyePos = player.getEyePosition();

        Vec3 look = player.getLookAngle();

        // offset
        boolean isMainHandAndRightArm = player.getUsedItemHand() == InteractionHand.MAIN_HAND && player.getMainArm() == HumanoidArm.RIGHT;
        boolean isOffHandAndLeftArm = player.getUsedItemHand() == InteractionHand.OFF_HAND && player.getMainArm() == HumanoidArm.LEFT;
        int handFactor = isMainHandAndRightArm || isOffHandAndLeftArm ? 1:-1;

        Vec3 horizontalAdjustment = look.cross(new Vec3(0, 1, 0)).normalize().scale(0.8 * handFactor);  // spostamento a destra
        Vec3 verticalAdjustment = new Vec3(0, 1, 0).scale(-0.1);

        for (int i = 2; i < -holdTime; i++) {
            Vec3 pos = eyePos
                    .add(look.scale(i))
                    .add(horizontalAdjustment)
                    .add(verticalAdjustment);
            level.sendParticles(ParticleTypes.SONIC_BOOM,pos.x,pos.y,pos.z, 0,0,0,0,0);

        }


    }
    private void spawnSpookyParticles(ServerLevel level, Player player) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 look = player.getLookAngle().normalize();

        double radius = 0.3;          // quanto larga è la spirale
        double turnsPerBlock = 1.5;   // rotazioni per unità di distanza
        double spacing = 0.1;         // distanza tra particelle

        // calcolo vettori ortogonali
        Vec3 up = new Vec3(0, 1, 0);
        if (Math.abs(look.dot(up)) > 0.99) up = new Vec3(1, 0, 0);
        Vec3 right = look.cross(up).normalize();
        Vec3 upOrtho = right.cross(look).normalize();


        boolean rightHand = (player.getUsedItemHand() == InteractionHand.MAIN_HAND && player.getMainArm() == HumanoidArm.RIGHT)
                || (player.getUsedItemHand() == InteractionHand.OFF_HAND && player.getMainArm() == HumanoidArm.LEFT);
        Vec3 handOffset = right.scale(rightHand ? 0.4 : -0.4);

        for (double d = 2; d < -holdTime; d += spacing) {
            double angle = d * turnsPerBlock * 2 * Math.PI;

            double offsetX = Math.cos(angle) * radius;
            double offsetY = Math.sin(angle) * radius;

            Vec3 pos = eyePos
                    .add(look.scale(d))
                    .add(right.scale(offsetX))
                    .add(upOrtho.scale(offsetY))
                    .add(handOffset)
                    .add(0, -0.1, 0);

            // velocità zero: la logica del movimento è nel particle client
            level.sendParticles(GunParticles.SPOOKY_SPIRAL, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
        }

    }


    private void hitEnemy(ServerLevel level, Player player) {
        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 look = player.getViewVector(1.0F);
        double range = -holdTime; // range scale on hold time
        Vec3 end = eyePos.add(look.scale(range));

        //Ray cast blocks
        BlockHitResult blockHit = level.clip(new ClipContext(
                eyePos,
                end,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        ));
        //max Distance is the range where an entity can be hit, more than that there is a block if hit which doesn't go through
        double maxDistance = range;
        if (blockHit.getType() != HitResult.Type.MISS) {
            maxDistance = eyePos.distanceTo(blockHit.getLocation());
        }

        //Ray cast entity
        EntityHitResult closestEntityHit = null;
        double closestDistance = maxDistance;
        //Find the closest entity
        for (Entity entity : level.getEntities(player, player.getBoundingBox().expandTowards(look.scale(range)).inflate(1.0))) {
            if (!entity.isPickable() || entity == player) continue;

            AABB box = entity.getBoundingBox().inflate(0.3D);
            Optional<Vec3> hit = box.clip(eyePos, end);
            if (hit.isPresent()) {
                double dist = eyePos.distanceTo(hit.get());
                if (dist < closestDistance) {
                    closestDistance = dist;
                    closestEntityHit = new EntityHitResult(entity, hit.get());
                }
            }
        }
        //damage the closest entity
        if (closestEntityHit == null) return;

        Entity target = closestEntityHit.getEntity();
        Holder<DamageType> genericType = level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.GENERIC);
        DamageSource ds = new DamageSource(genericType);
        target.hurtServer(level, ds, -this.holdTime);
        //target.kill(level);

    }






}
