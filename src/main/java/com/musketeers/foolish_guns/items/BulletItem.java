package com.musketeers.foolish_guns.items;

import com.musketeers.foolish_guns.entities.Bullet;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.level.Level;
import java.util.Optional;

public class BulletItem extends ArrowItem {
    public BulletItem(Properties properties) {
        super(properties);
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
                                new Bullet(level, player, finalEntityHit.getEntity(), Direction.UP.getAxis()),
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
        return InteractionResult.SUCCESS;
    }

}
