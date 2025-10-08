package com.musketeers.foolish_guns.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class Payloads {
    public static void initialize(){
        registerPackets();
        registerReceivers();
    }
    public static void registerPackets(){
        PayloadTypeRegistry.playC2S().register(
                KillEntityC2SPayload.TYPE,
                StreamCodec.of(
                        (RegistryFriendlyByteBuf buffer, KillEntityC2SPayload payload)-> buffer.writeInt(payload.entityId()),
                        (RegistryFriendlyByteBuf buffer)-> new KillEntityC2SPayload(buffer.readInt())

                )

        );
    }
    public static void registerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(
                KillEntityC2SPayload.TYPE, // tipo del pacchetto
                (payload, context) -> {    // handler del pacchetto
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
        );
    }
}
