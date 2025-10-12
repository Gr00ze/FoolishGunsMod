package com.musketeers.foolish_guns.items;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;
import java.util.function.Consumer;

public class PrototypeGunItem extends Item implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation ACTIVATE_ANIM = RawAnimation.begin().thenPlay("animation.gun_model.tesla");
    public PrototypeGunItem(Properties properties) {

        super(properties);

        GeoItem.registerSyncedAnimatable(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>("Activation", 0, animTest -> PlayState.STOP).triggerableAnim("activate", ACTIVATE_ANIM)
                .setCustomInstructionKeyframeHandler(event->{
                    if(event.keyframeData().getInstructions().equals("fire")){
                        this.shoot();
                    }
                }));

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    Object provider = null;
    public void injectRenderProvider(Object provider){
        this.provider = provider;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void createGeoRenderer(Consumer consumer) {
        System.out.println("Provider is "+ provider);
        if (provider == null)return;
        consumer.accept(provider);

    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand hand) {

        if (level instanceof ServerLevel serverLevel){
            //animation
            triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(hand), serverLevel), "Activation", "activate");


            //particles
            for (int i = 0; i < 100; i++) {
                Vec3 scaled = player.getEyePosition().add(player.getLookAngle().scale(i));
                serverLevel.sendParticles(ParticleTypes.SONIC_BOOM,scaled.x,scaled.y,scaled.z, 1,0,0,0,0);

            }
            //kill
            Vec3 eyePos = player.getEyePosition(1.0F);
            Vec3 look = player.getViewVector(1.0F);
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

                    }
                }
            }
            if (entityHit != null && entityHit.getEntity() != null) {
                Entity target = entityHit.getEntity();
                target.kill(serverLevel);
            }



        }
        player.playSound(SoundEvents.WARDEN_SONIC_BOOM, 8.0F, 15.0F);



        return InteractionResult.FAIL;
    }






    public void shoot() {

    }
}
