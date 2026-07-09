package net.akws.chiseled_lib.common.payload;

import io.netty.buffer.ByteBuf;
import net.akws.chiseled_lib.client.camera.screenshake.Screenshakes;
import net.akws.chiseled_lib.common.ChiseledLib;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public record ScreenshakePayload(int duration, Vec3 pos, float radius, float intensity) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ScreenshakePayload> TYPE = new CustomPacketPayload.Type<ScreenshakePayload>(ChiseledLib.id("screenshake"));
    public static final StreamCodec<ByteBuf, ScreenshakePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ScreenshakePayload::duration,
            Vec3.STREAM_CODEC, ScreenshakePayload::pos,
            ByteBufCodecs.FLOAT, ScreenshakePayload::radius,
            ByteBufCodecs.FLOAT, ScreenshakePayload::intensity,
            ScreenshakePayload::new
    );

    public static void send(Player player, int duration, Vec3 pos, float radius, float intensity) {
        if (player instanceof ServerPlayer serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, new ScreenshakePayload(duration, pos, radius, intensity));
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<ScreenshakePayload> {
        @Override
        public void receive(ScreenshakePayload payload, ClientPlayNetworking.Context context) {
            Screenshakes.get().create(payload.duration, payload.pos, payload.radius, payload.intensity);
        }
    }
}
