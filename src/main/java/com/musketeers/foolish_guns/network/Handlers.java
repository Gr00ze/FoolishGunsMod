package com.musketeers.foolish_guns.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

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
            }
        });
    }
}
