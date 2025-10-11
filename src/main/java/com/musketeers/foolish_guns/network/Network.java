package com.musketeers.foolish_guns.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class Network {
    public static void initialize(){
        registerPackets();
        registerReceivers();
    }

    public static void registerPackets(){
        PayloadTypeRegistry.playC2S().register(KillEntityC2SPayload.TYPE, KillEntityC2SPayload.CODEC);
        //new Examples
        //PayloadTypeRegistry.playS2C().register(NameS2CPayload.TYPE, NameS2CPayload.CODEC);
        //PayloadTypeRegistry.playC2S().register(NameC2SPayload.TYPE, NameC2SPayload.CODEC);
    }
    public static void registerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(KillEntityC2SPayload.TYPE,Handlers::killEntityC2SHandler);
        //ServerPlayNetworking.registerGlobalReceiver(NameC2SPayload.TYPE,Handlers::nameC2SPayloadC2SHandler);
        //ClientPlayNetworking is not present here. must be registered on client side
    }


}
