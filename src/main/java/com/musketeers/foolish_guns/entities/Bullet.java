package com.musketeers.foolish_guns.entities;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Bullet extends ShulkerBullet {
    private static final double SPEED = 5;
    protected final RandomSource random = RandomSource.create();

    @Nullable
    private EntityReference<Entity> finalTarget;
    @Nullable
    private Direction currentMoveDirection;
    private int flightSteps;
    private double targetDeltaX;
    private double targetDeltaY;
    private double targetDeltaZ;

    public Bullet(EntityType<? extends Bullet> entityType, Level level) {
        super(entityType, level);
        this.noPhysics=true;
    }
    //@Override
    public Bullet(Level level, LivingEntity shooter, Entity finalTarget, Direction.Axis axis) {
        this(EntityList.BULLET_ENTITY_TYPE, level);
        this.setOwner(shooter);
        Vec3 vec3 = shooter.getBoundingBox().getCenter();
        this.snapTo(vec3.x, vec3.y, vec3.z, this.getYRot(), this.getXRot());
        this.finalTarget = new EntityReference<>(finalTarget);
        this.currentMoveDirection = Direction.getRandom(this.random);
        this.selectNextMoveDirection(axis, finalTarget);
        this.setNoGravity(true);
    }
    private void setMoveDirection(@Nullable Direction direction) {
        this.currentMoveDirection = direction;
    }
    @Override
    protected double getDefaultGravity() {
        return 0.04;
    }\
    private void selectNextMoveDirection(@Nullable Direction.Axis axis, @Nullable Entity entity) {
        double d = 0.5;
        BlockPos blockPos;
        if (entity == null) {
            blockPos = this.blockPosition().below();
        } else {
            d = entity.getBbHeight() * 0.5;
            blockPos = BlockPos.containing(entity.getX(), entity.getY() + d, entity.getZ());
        }

        double e = blockPos.getX() + 0.5;
        double f = blockPos.getY() + d;
        double g = blockPos.getZ() + 0.5;
        Direction direction = null;
        if (!blockPos.closerToCenterThan(this.position(), 2.0)) {
            BlockPos blockPos2 = this.blockPosition();
            List<Direction> list = Lists.<Direction>newArrayList();
            if (axis != Direction.Axis.X) {
                if (blockPos2.getX() < blockPos.getX() && this.level().isEmptyBlock(blockPos2.east())) {
                    list.add(Direction.EAST);
                } else if (blockPos2.getX() > blockPos.getX() && this.level().isEmptyBlock(blockPos2.west())) {
                    list.add(Direction.WEST);
                }
            }

            if (axis != Direction.Axis.Y) {
                if (blockPos2.getY() < blockPos.getY() && this.level().isEmptyBlock(blockPos2.above())) {
                    list.add(Direction.UP);
                } else if (blockPos2.getY() > blockPos.getY() && this.level().isEmptyBlock(blockPos2.below())) {
                    list.add(Direction.DOWN);
                }
            }

            if (axis != Direction.Axis.Z) {
                if (blockPos2.getZ() < blockPos.getZ() && this.level().isEmptyBlock(blockPos2.south())) {
                    list.add(Direction.SOUTH);
                } else if (blockPos2.getZ() > blockPos.getZ() && this.level().isEmptyBlock(blockPos2.north())) {
                    list.add(Direction.NORTH);
                }
            }

            direction = Direction.getRandom(this.random);
            if (list.isEmpty()) {
                for (int i = 5; !this.level().isEmptyBlock(blockPos2.relative(direction)) && i > 0; i--) {
                    direction = Direction.getRandom(this.random);
                }
            } else {
                direction = (Direction)list.get(this.random.nextInt(list.size()));
            }

            e = this.getX() + direction.getStepX();
            f = this.getY() + direction.getStepY();
            g = this.getZ() + direction.getStepZ();
        }

        this.setMoveDirection(direction);
        double h = e - this.getX();
        double j = f - this.getY();
        double k = g - this.getZ();
        double l = Math.sqrt(h * h + j * j + k * k);
        if (l == 0.0) {
            this.targetDeltaX = 0.0;
            this.targetDeltaY = 0.0;
            this.targetDeltaZ = 0.0;
        } else {
            this.targetDeltaX = h / l * 0.15;
            this.targetDeltaY = j / l * 0.15;
            this.targetDeltaZ = k / l * 0.15;
        }

        this.hasImpulse = true;
        this.flightSteps = 10 + this.random.nextInt(5) * 10;
    }
    @Override
    public void tick() {
        super.tick();
        Entity entity = !this.level().isClientSide() ? EntityReference.get(this.finalTarget, this.level(), Entity.class) : null;
        HitResult hitResult = null;
        if (!this.level().isClientSide) {
            if (entity == null) {
                this.finalTarget = null;
            }

            if (entity == null || !entity.isAlive() || entity instanceof Player && entity.isSpectator()) {
                this.applyGravity();
            } else {
                this.targetDeltaX = Mth.clamp(this.targetDeltaX * 1.025, -1.0, 1.0);
                this.targetDeltaY = Mth.clamp(this.targetDeltaY * 1.025, -1.0, 1.0);
                this.targetDeltaZ = Mth.clamp(this.targetDeltaZ * 1.025, -1.0, 1.0);
                Vec3 vec3 = this.getDeltaMovement();
                this.setDeltaMovement(vec3.add((this.targetDeltaX - vec3.x) * 0.2, (this.targetDeltaY - vec3.y) * 0.2, (this.targetDeltaZ - vec3.z) * 0.2));
            }

            hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        }

        Vec3 vec3 = this.getDeltaMovement();
        this.setPos(this.position().add(vec3));
        this.applyEffectsFromBlocks();
        if (this.portalProcess != null && this.portalProcess.isInsidePortalThisTick()) {
            this.handlePortal();
        }

        if (hitResult != null && this.isAlive() && hitResult.getType() != HitResult.Type.MISS) {
            this.hitTargetOrDeflectSelf(hitResult);
        }

        ProjectileUtil.rotateTowardsMovement(this, 0.5F);
        if (this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.END_ROD, this.getX() - vec3.x, this.getY() - vec3.y + 0.15, this.getZ() - vec3.z, 0.0, 0.0, 0.0);
        } else if (entity != null) {
            if (this.flightSteps > 0) {
                this.flightSteps--;
                if (this.flightSteps == 0) {
                    this.selectNextMoveDirection(this.currentMoveDirection == null ? null : this.currentMoveDirection.getAxis(), entity);
                }
            }

            if (this.currentMoveDirection != null) {
                BlockPos blockPos = this.blockPosition();
                Direction.Axis axis = this.currentMoveDirection.getAxis();
                if (this.level().loadedAndEntityCanStandOn(blockPos.relative(this.currentMoveDirection), this)) {
                    this.selectNextMoveDirection(axis, entity);
                } else {
                    BlockPos blockPos2 = entity.blockPosition();
                    if (axis == Direction.Axis.X && blockPos.getX() == blockPos2.getX()
                            || axis == Direction.Axis.Z && blockPos.getZ() == blockPos2.getZ()
                            || axis == Direction.Axis.Y && blockPos.getY() == blockPos2.getY()) {
                        this.selectNextMoveDirection(axis, entity);
                    }
                }
            }
        }
    }

}
