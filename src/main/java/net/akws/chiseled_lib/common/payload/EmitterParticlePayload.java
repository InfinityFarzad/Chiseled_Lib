package net.akws.chiseled_lib.common.payload;

import net.akws.chiseled_lib.common.ChiseledLib;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;

public record EmitterParticlePayload(int entityId, ParticleEffect particleEffect) implements CustomPayload {
    public static final Id<EmitterParticlePayload> ID = new Id<>(ChiseledLib.id("emitter_particle"));
    public static final PacketCodec<RegistryByteBuf, EmitterParticlePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, EmitterParticlePayload::entityId,
            ParticleTypes.PACKET_CODEC, EmitterParticlePayload::particleEffect,
            EmitterParticlePayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<EmitterParticlePayload> {
        @Override
        public void receive(EmitterParticlePayload payload, ClientPlayNetworking.Context context) {
            MinecraftClient client = MinecraftClient.getInstance();

            if (client != null && client.world != null) {
                Entity entity = client.world.getEntityById(payload.entityId);
                if (entity != null) {
                    client.particleManager.addEmitter(entity, payload.particleEffect);
                }
            }
        }
    }
}
