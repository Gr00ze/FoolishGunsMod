package com.musketeers.foolish_guns.items;

import com.musketeers.foolish_guns.FoolishGuns;
import com.musketeers.foolish_guns.particles.GunParticles;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animatable.processing.AnimationTest;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.dataticket.SerializableDataTicket;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;
import java.util.function.BiConsumer;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.id;
import static com.musketeers.foolish_guns.utils.Season.getSeasonalMode;

public class TeslaGun extends ExtendedGeoItem {



    //Gecko lib settings
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation CHARGE_ANIMATION = RawAnimation.begin().thenPlay("animation.gun_model.tesla.charge");
    private static final RawAnimation DISCHARGE_ANIMATION = RawAnimation.begin().thenPlay("animation.gun_model.tesla.discharge");

    public static final SerializableDataTicket<Float> GLOW_EFFECT =
            SerializableDataTicket.ofFloat(id("tesla_gun_glow"));

    private static final String controllerName = "Gun_Controller";
    private static final String chargeAnimationName = "charge";
    private static final String dischargeAnimationName = "discharge";
    //Settings
    private static final int maxDamage = 10;



    public TeslaGun(Properties properties) {
        super(properties);
        GeoItem.registerSyncedAnimatable(this);
    }

    public static Item.Properties getItemProperties() {
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
        InteractionResult result = InteractionResult.FAIL;
        if (!(level instanceof ServerLevel serverLevel))
            return result;
        //first hand
        if (canBeUsed(player.getItemInHand(hand))){
            result = this.useGun(serverLevel, (ServerPlayer) player, hand);
            player.startUsingItem(hand);
        }

        //second hand
        InteractionHand otherHand = getOtherPlayerHand(hand);
        ItemStack otherStack = player.getItemInHand(otherHand);
        if (canBeUsed(otherStack)){
            if (otherStack.getItem() instanceof TeslaGun) {
                InteractionResult result2 =
                this.useGun(serverLevel, (ServerPlayer) player, otherHand);
                if(result == InteractionResult.FAIL && result2 != InteractionResult.FAIL){
                    result = result2;
                    player.startUsingItem(otherHand);
                }
            }
        }


        return result;
    }

    public @NotNull InteractionResult useGun(ServerLevel serverLevel, ServerPlayer player, InteractionHand hand) {

        FoolishGuns.LOGGER.info("Player {} charging the gun in hand {}", player.getName(), hand.name());

        switch (getSeasonalMode()) {
            case HALLOWEEN -> {
                serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WITHER_AMBIENT, SoundSource.PLAYERS, 2F, 0.5F);
            }
            case CHRISTMAS -> {
                Vec3 pos = player.position();

                serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.NOTE_BLOCK_BELL, SoundSource.PLAYERS, 0.3f, 1.0f);

            }
            default -> {
                //volume 0 - 1 - >1 distance
                //pitch 0.5 - 1 - > 1 faster sound
                serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WITHER_AMBIENT, SoundSource.PLAYERS, 2F, 0.5F);
            }

        }


        triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(hand), serverLevel), controllerName, chargeAnimationName);
        //With PASS the gun will still charge, but it will let the offhand to be used

        //Can be added a check to disable the second call if not TeslaGun
        return InteractionResult.CONSUME;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        if (!(livingEntity instanceof ServerPlayer player)) return;
        player.setPose(Pose.SHOOTING);
        ServerLevel serverLevel = (ServerLevel) level;
        Vec3 pos = player.position();
        switch (getSeasonalMode()) {
            case HALLOWEEN -> {

            }
            case CHRISTMAS -> {

                int tick = player.tickCount % 60; // ciclo di 3 secondi (20 tick = 1 sec)

                if(tick == 0) {
                    serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.NOTE_BLOCK_CHIME, SoundSource.PLAYERS, 0.5f, 0.5f); // 1 secondo
                } else if(tick == 15) {
                    serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.NOTE_BLOCK_CHIME, SoundSource.PLAYERS, 0.5f, 0.66f); // 2 secondo
                } else if(tick == 30) {
                    serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.NOTE_BLOCK_CHIME, SoundSource.PLAYERS, 0.5f, 0.74f); // 3 secondo
                }
                else if(tick == 45) {
                    serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.NOTE_BLOCK_CHIME, SoundSource.PLAYERS, 0.5f, 0.5f); // 3 secondo
                }




                if (player.tickCount % 10 == 0) {
                    serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ELYTRA_FLYING, SoundSource.PLAYERS, 0.05f, 5.6f);
                }
            }
            default -> {
                //volume 0 - 1 - >1 distance
                //pitch 0.5 - 1 - > 1 faster sound

            }

        }


        float t = Math.min(1f, (-remainingUseDuration) / 80f);
        float glow = 1f - (float)Math.exp(-3f * t); // ease-in
        forEachTeslaGunInHands(
                player,
                player.getUsedItemHand(),
                (stack1, hand) -> {
                    if (!canBeUsed(stack1)) return;
                    this.setGlow(player, stack1, glow);
                }

        );
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int holdTime) {

        boolean releaseUsing = super.releaseUsing(itemStack, level, livingEntity, holdTime);

        if (!(livingEntity instanceof Player player))return releaseUsing;

        forEachTeslaGunInHands(
                player,
                player.getUsedItemHand(),
                (stack, hand) ->{
                    if(!canBeUsed(stack))return;
                    this.releaseGun(stack, level, player, holdTime, isUsedRightHand(player, hand));
                }

        );
        return releaseUsing;
    }

    private void releaseGun(ItemStack itemStack, Level level, Player player, int holdTime, boolean isRightHand) {
        if (level instanceof ServerLevel serverLevel){// implicit ServerPlayer
            triggerAnim(player, GeoItem.getOrAssignId(itemStack, serverLevel), controllerName, dischargeAnimationName);
            shoot(serverLevel, player, isRightHand, -holdTime);
            //setGlow(player, itemStack, 0f);
            if (!player.isCreative())
                itemStack.setDamageValue(itemStack.getDamageValue()+1);
        }
    }

    public void shoot(ServerLevel serverLevel, Player player, boolean isRightHand, float power){

        Vec3 eyePos = player.getEyePosition();
        Vec3 look = player.getLookAngle();

        switch (getSeasonalMode()) {
            case HALLOWEEN -> {
                this.spawnSpookyParticles(serverLevel,  eyePos, look, isRightHand?0.4: -0.4, (int)power);
                serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BAT_LOOP, SoundSource.PLAYERS, 1F, 2.0F);
            }
            case CHRISTMAS -> {
                this.spawnChristmasParticles(serverLevel, eyePos, look, isRightHand?1: -1, (int)power);

                serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS, 0.6f, 1.0f);
            }
            default -> {
                this.spawnParticles(serverLevel, eyePos, look, isRightHand?1: -1, (int)power);
                //volume 0 - 1 - >1 distance
                //pitch 0.5 - 1 - > 1 faster sound
                serverLevel.playSound(null,player.getX(),player.getY(),player.getZ(),SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 1F, 2.0F);
            }

        }

        this.hitEnemy(serverLevel, player, power);
    }

    @Override
    public void inventoryTick(ItemStack itemStack,
                              ServerLevel serverLevel,
                              Entity entity,
                              @Nullable EquipmentSlot slot
    ) {
        if(!(entity instanceof Player player))return;
        if(player.isUsingItem())return;
        if(slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
            dimmingTeslaGun(itemStack, player);
        }
    }

    private void dimmingTeslaGun(
            ItemStack stack, Player player
    ){
        Float actualGlow = getGlow(player, stack);
        if(actualGlow == null || actualGlow == 0F) return;

        actualGlow = actualGlow >= 0.01F? Mth.clamp(actualGlow / 1.1f, 0f, 1f):0;
        setGlow(player,stack,actualGlow);
    }

    private void spawnChristmasParticles(ServerLevel level, Vec3 eyePos, Vec3 look, int offset, int rayLength) {
        Vec3 horizontalAdjustment = look.cross(new Vec3(0, 1, 0)).normalize().scale(0.8 * offset);  // spostamento a destra
        Vec3 verticalAdjustment = new Vec3(0, 1, 0).scale(-0.1);

        for (int i = 2; i < rayLength; i++) {
            Vec3 pos = eyePos
                    .add(look.scale(i))
                    .add(horizontalAdjustment)
                    .add(verticalAdjustment);
            level.sendParticles(ParticleTypes.SNOWFLAKE,pos.x,pos.y,pos.z, 0,0,0,0,0);
            level.sendParticles(ParticleTypes.GLOW,pos.x,pos.y,pos.z, 0,0,0,0,0);
            level.sendParticles(ParticleTypes.GLOW_SQUID_INK,pos.x,pos.y,pos.z, 0,0,0,0,0);
            level.sendParticles(ParticleTypes.CLOUD,pos.x,pos.y,pos.z, 0,0,0,0,0);
            level.sendParticles(ParticleTypes.CRIMSON_SPORE,pos.x,pos.y,pos.z, 50,0,0,0,0);
            level.sendParticles(ParticleTypes.FIREWORK, pos.x,pos.y,pos.z, 1,0,0,0,0);


        }

    }

    private void spawnParticles(ServerLevel level, Vec3 eyePos, Vec3 look, double offset, int rayLength) {

        Vec3 horizontalAdjustment = look.cross(new Vec3(0, 1, 0)).normalize().scale(0.8 * offset);  // spostamento a destra
        Vec3 verticalAdjustment = new Vec3(0, 1, 0).scale(-0.1);

        for (int i = 2; i < rayLength; i++) {
            Vec3 pos = eyePos
                    .add(look.scale(i))
                    .add(horizontalAdjustment)
                    .add(verticalAdjustment);
            level.sendParticles(ParticleTypes.SONIC_BOOM,pos.x,pos.y,pos.z, 0,0,0,0,0);

        }


    }
    private void spawnSpookyParticles(ServerLevel level, Vec3 eyePos, Vec3 look, double offset, int rayLength) {
        double radius = 0.3;          // quanto larga è la spirale
        double turnsPerBlock = 1.5;   // rotazioni per unità di distanza
        double spacing = 0.1;         // distanza tra particelle

        // calcolo vettori ortogonali
        Vec3 up = new Vec3(0, 1, 0);
        if (Math.abs(look.dot(up)) > 0.99) up = new Vec3(1, 0, 0);
        Vec3 right = look.cross(up).normalize();
        Vec3 upOrtho = right.cross(look).normalize();

        for (double d = 2; d < rayLength; d += spacing) {
            double angle = d * turnsPerBlock * 2 * Math.PI;

            double offsetX = Math.cos(angle) * radius;
            double offsetY = Math.sin(angle) * radius;

            Vec3 pos = eyePos
                    .add(look.scale(d))
                    .add(right.scale(offsetX))
                    .add(upOrtho.scale(offsetY))
                    .add(offset)
                    .add(0, -0.1, 0);

            // velocità zero: la logica del movimento è nel particle client
            level.sendParticles(GunParticles.SPOOKY_SPIRAL, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
        }

    }


    private void hitEnemy(ServerLevel level, Player player, float range) {
        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 look = player.getViewVector(1.0F);

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
        target.hurtServer(level, ds, range);
        //target.kill(level);

    }

    //utility


    private InteractionHand getOtherPlayerHand(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND? InteractionHand.OFF_HAND: InteractionHand.MAIN_HAND;
    }

    private double getOffsetWithPlayerHand(Player player, InteractionHand hand, double valueIfLeft, double valueIfRight){
        return isUsedRightHand(player, hand) ? valueIfLeft : -valueIfRight;
    }

    private boolean isUsedRightHand(Player player, InteractionHand hand){
        return (hand == InteractionHand.MAIN_HAND && player.getMainArm() == HumanoidArm.RIGHT) ||
                        (hand == InteractionHand.OFF_HAND && player.getMainArm() == HumanoidArm.LEFT);
    }

    private boolean isUsedRightHand(Player player){
        return isUsedRightHand(player, player.getUsedItemHand());
    }
    private void triggerAnimationOnBothHandsIfPossible(InteractionHand useHand, Player player, ServerLevel level, String controllerName, String animationName){
        //animation
        triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(useHand), level), controllerName, animationName);

        InteractionHand otherHand = getOtherPlayerHand(useHand);
        ItemStack stack = player.getItemInHand(otherHand);
        if(stack.getItem() instanceof TeslaGun){
            //other hand animation
            triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(otherHand), level), controllerName, animationName);
        }

    }

    private boolean canBeUsed(ItemStack stack){
        return stack.getDamageValue() < maxDamage;
    };

    private void setGlow(Player player, ItemStack stack, float value) {
        if (!(player.level() instanceof ServerLevel level)) return;

        long id = GeoItem.getOrAssignId(stack, level);
        AnimatableManager<?> manager = this.cache.getManagerForId(id);
        manager.setAnimatableData(GLOW_EFFECT, value);
        //FoolishGuns.LOGGER.info("Loading {}", value);
    }

    private Float getGlow(Player player, ItemStack stack) {
        if (!(player.level() instanceof ServerLevel level)) return null;

        long id = GeoItem.getOrAssignId(stack, level);
        AnimatableManager<?> manager = this.cache.getManagerForId(id);
        return manager.getAnimatableData(GLOW_EFFECT);
        //FoolishGuns.LOGGER.info("Loading {}", value);
    }

    private void forEachTeslaGunInHands(
            Player player,
            InteractionHand usedHand,
            BiConsumer<ItemStack, InteractionHand> action
    ) {
        // mano usata
        ItemStack main = player.getItemInHand(usedHand);
        if (main.getItem() instanceof TeslaGun) {
            action.accept(main, usedHand);
        }

        // altra mano
        InteractionHand other = getOtherPlayerHand(usedHand);
        ItemStack off = player.getItemInHand(other);
        if (off.getItem() instanceof TeslaGun) {
            action.accept(off, other);
        }
    }


}
