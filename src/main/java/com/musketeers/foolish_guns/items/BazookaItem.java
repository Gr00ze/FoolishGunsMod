package com.musketeers.foolish_guns.items;

import com.musketeers.foolish_guns.entities.IonBallEntity;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;
import java.util.function.Consumer;

public class BazookaItem extends ArrowItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation ACTIVATE_ANIM = RawAnimation.begin().thenPlay("animation.gun_model.tesla");

    public BazookaItem(Properties properties) {
        super(properties);
        GeoItem.registerSyncedAnimatable(this);
    }
    @Override
    @SuppressWarnings("unchecked")
    public void createGeoRenderer(Consumer consumer) {
        System.out.println("Provider is "+ provider);
        if (provider == null)return;
        consumer.accept(provider);

    }
    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand hand){
        ItemStack itemStack = player.getItemInHand(hand);
        if (level instanceof ServerLevel serverLevel) {
            Vec3 eyePos = player.getEyePosition(1f);
            Vec3 look = player.getViewVector(1f);
            double range = 50.0;
            Vec3 end = eyePos.add(look.scale(range));
            EntityHitResult entityHit = null;
            double closestDistance = range;
            for (Entity entity : level.getEntities(player, player.getBoundingBox().expandTowards(look.scale(range)).inflate(1.0))) {
                if (!entity.isPickable() || entity == player) continue;

                AABB box = entity.getBoundingBox().inflate(0.3D); // margine per entità piccole
                Optional<Vec3> hit = box.clip(eyePos, end);
                if (hit.isPresent()) {
                    double dist = eyePos.distanceTo(hit.get());
                    if (dist < closestDistance) {
                        closestDistance = dist;
                        entityHit = new EntityHitResult(entity, hit.get());
                        EntityHitResult finalEntityHit = entityHit;
                        Projectile.spawnProjectile(
                                new IonBallEntity(level, player, finalEntityHit.getEntity(), Direction.UP.getAxis()),
                                serverLevel,
                                itemStack
                        );

                    }
                }
            }
        }

        /*level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.WIND_CHARGE_THROW,
                SoundSource.NEUTRAL,
                0.5F,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
        );*/
        //player.awardStat(Stats.ITEM_USED.get(this));
        itemStack.consume(1, player);
        return InteractionResult.CONSUME;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>("Activation", 0, animTest -> PlayState.STOP).triggerableAnim("activate", ACTIVATE_ANIM)
                .setCustomInstructionKeyframeHandler(event->{
                    if(event.keyframeData().getInstructions().equals("fire")){
                        //this.shoot();
                    }
                }));

    }
    Object provider = null;
    public void injectRenderProvider(Object provider){
        this.provider = provider;
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
