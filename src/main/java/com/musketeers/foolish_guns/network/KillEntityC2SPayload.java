package com.musketeers.foolish_guns.network;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.id;

public record KillEntityC2SPayload(int entityId) implements CustomPacketPayload {

    // Identificatore univoco del pacchetto
    public static final ResourceLocation ID = id("kill_entity_c2s");

    // Tipo registrato (serve per la serializzazione)
    public static final CustomPacketPayload.Type<KillEntityC2SPayload> TYPE = new CustomPacketPayload.Type<>(ID);

    // Costruttore per decodifica da buffer
    public KillEntityC2SPayload(FriendlyByteBuf buf) {
        this(buf.readInt());

    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


}




