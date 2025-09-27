package com.musketeers.foolish_guns.entities;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.level.Level;

public class Bullet extends ShulkerBullet {
    private static final double SPEED = 5.0;

    public Bullet(EntityType<? extends Bullet> entityType, Level level) {
        super(entityType, level);
        this.noPhysics=false;
    }

    public Bullet(Level level, LivingEntity shooter, Entity finalTarget, Direction.Axis axis) {
        super(level, shooter, finalTarget, axis);
    }

}
