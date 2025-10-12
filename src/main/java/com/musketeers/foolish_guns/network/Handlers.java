package com.musketeers.foolish_guns.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class Handlers {
    public static void killEntityC2SHandler(KillEntityC2SPayload payload, ServerPlayNetworking.Context context){
        ServerPlayer player = context.player();
        var level = player.level();

        // Esegui l’azione nel main server thread
        context.server().execute(() -> {
            Entity target = level.getEntity(payload.entityId());
            if (target != null && target.isAlive()) {

                // puoi aggiungere controlli extra:
                // - distanza massima
                // - visibilità
                // - ruolo / permesso del player
                target.kill(level); // oppure target.hurt(...)
                //particles
                for (int i = 0; i < 50; i++) {
                    Vec3 particlesPos = player.getEyePosition().add(player.getLookAngle().scale(i));
                    level.sendParticles(ParticleTypes.SONIC_BOOM,particlesPos.x,particlesPos.y,particlesPos.z,1,0,0,0,0);
                }

            }
        });
    }
}
