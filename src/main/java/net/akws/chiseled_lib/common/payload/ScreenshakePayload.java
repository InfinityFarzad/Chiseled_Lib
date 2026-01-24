package net.akws.chiseled_lib.common.payload;

import io.netty.buffer.ByteBuf;
import net.akws.chiseled_lib.client.camera.Screenshakes;
import net.akws.chiseled_lib.common.ChiseledLib;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public record ScreenshakePayload(int duration, Vec3d pos, float radius, float intensity) implements CustomPayload {
    public static final Id<ScreenshakePayload> ID = new Id<>(ChiseledLib.id("screenshake"));
    public static final PacketCodec<ByteBuf, ScreenshakePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, ScreenshakePayload::duration,
            Vec3d.PACKET_CODEC, ScreenshakePayload::pos,
            PacketCodecs.FLOAT, ScreenshakePayload::radius,
            PacketCodecs.FLOAT, ScreenshakePayload::intensity,
            ScreenshakePayload::new
    );

    public static void send(PlayerEntity player, int duration, Vec3d pos, float radius, float intensity) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, new ScreenshakePayload(duration, pos, radius, intensity));
        }
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<ScreenshakePayload> {
        @Override
        public void receive(ScreenshakePayload payload, ClientPlayNetworking.Context context) {
            Screenshakes.get().create(payload.duration, payload.pos, payload.radius, payload.intensity);
        }
    }
}
