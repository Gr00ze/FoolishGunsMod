package com.musketeers.foolish_guns.network;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.id;

public record KillEntityC2SPayload(int entityId) implements CustomPacketPayload {

    //Payload identifier
    public static final ResourceLocation ID = id("kill_entity_c2s");
    //Type record with identifier
    public static final CustomPacketPayload.Type<KillEntityC2SPayload> TYPE = new CustomPacketPayload.Type<>(ID);
    //Codec: declaration of decode and encode behaviour
    public static final StreamCodec<RegistryFriendlyByteBuf,KillEntityC2SPayload> CODEC =
            StreamCodec.of(KillEntityC2SPayload::encode,KillEntityC2SPayload::decode);

    //For me, it is useless but mandatory XD
    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    //From the payload serialize the information in to the buffer
    public static void encode(RegistryFriendlyByteBuf buffer, KillEntityC2SPayload payload) {
        buffer.writeInt(payload.entityId());
    }
    //From the buffer take the data to reconstruct the payload
    public static @NotNull KillEntityC2SPayload decode(RegistryFriendlyByteBuf buffer) {
        return new KillEntityC2SPayload(buffer.readInt());
    }




}




