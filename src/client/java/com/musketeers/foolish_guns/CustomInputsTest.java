package com.musketeers.foolish_guns;

import com.musketeers.foolish_guns.items.PrototypeGunItem;
import com.musketeers.foolish_guns.network.KillEntityC2SPayload;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.*;

import java.util.Optional;

public class CustomInputsTest {

    public static void registerEvents(){
        //ClientTickEvents.END_CLIENT_TICK.register(CustomInputsTest::registerHitscanEvent);
        //ClientTickEvents.END_CLIENT_TICK.register(CustomInputsTest::something);
        //ClientTickEvents.END_CLIENT_TICK.register(CustomInputsTest::shoot);

    }
    private static void something(Minecraft client){

            if (client.player == null) return;
            if (true) {
                LocalPlayer player = client.player;
                if (player.getMainHandItem().getItem() instanceof PrototypeGunItem) {

                    Vec3 eyePos = player.getEyePosition(1.0F);
                    Vec3 look = player.getViewVector(1.0F);
                    double range = 50.0; // distanza max
                    Vec3 end = eyePos.add(look.scale(range));


                    BlockHitResult hit = client.level.clip(new ClipContext(
                            eyePos,
                            end,
                            ClipContext.Block.COLLIDER,
                            ClipContext.Fluid.ANY,
                            player
                    ));




                    Vec3 hitPos;
                    if (hit.getType() == HitResult.Type.BLOCK) {
                        hitPos = hit.getLocation();
                    } else {

                        hitPos = end;
                    }
                    //System.out.println(hitPos);
                    //lineStart = eyePos;
                    //lineEnd = hitPos;
                    //shouldDraw = true;


                }
            } else {

                //shouldDraw = false;
            }

    }

    private static void shoot(Minecraft client){


            if (client.player == null) return;

            if (client.options.keyAttack.isDown()) {
                if (client.player.getMainHandItem().getItem() instanceof PrototypeGunItem gun) {
                    //gun.shoot(client.level, client.player, client.player.getMainHandItem());
                }
            }
    }

    private static void registerHitscanEvent(Minecraft client){

        if (client.player == null) return;

        LocalPlayer player = client.player;
        if (!(player.getMainHandItem().getItem() instanceof PrototypeGunItem)) return;

        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 look = player.getViewVector(1.0F);
        double range = 50.0;
        Vec3 end = eyePos.add(look.scale(range));
        //client.options.keyAttack.consumeClick();
        EntityHitResult entityHit = null;
        double closestDistance = range;
        /*for (int i = 0; i < 100; i++) {
            Vec3 scaled = eyePos.add(look.scale(i));
            client.level.addParticle(ParticleTypes.SCULK_CHARGE_POP,scaled.x,scaled.y,scaled.z, 0,0,0);
        }*/
        for (Entity entity : client.level.getEntities(player, player.getBoundingBox().expandTowards(look.scale(range)).inflate(1.0))) {
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


            // manda l'id dell'entità al server (Fabric ClientPlayNetworking)

            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeInt(target.getId());
            ClientPlayNetworking.send(new KillEntityC2SPayload(target.getId()));
        }

    }

}
