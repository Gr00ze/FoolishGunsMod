package com.musketeers.foolish_guns;

import com.musketeers.foolish_guns.items.PrototypeGunItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class CustomInputsTest {
    public void something(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
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
        });
    }

    public void shoot(){
        /*
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (client.options.keyAttack.isDown()) {
                if (client.player.getMainHandItem().getItem() instanceof PrototypeGunItem gun) {
                    //gun.shoot(client.level, client.player, client.player.getMainHandItem());
                }
            }

        });
        */
    }

}
