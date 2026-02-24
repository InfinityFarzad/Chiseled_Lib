package net.akws.chiseled_lib.common.payload;

import net.akws.chiseled_lib.common.ChiseledLib;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public record ExpandedParticlePayload(ParticleEffect parameters, boolean force, Vec3d pos, Vec3d vel) implements CustomPayload {
    public static final Identifier PARTICLE_SPAWNING_S2C_PAYLOAD = ChiseledLib.id("expanded_particle_spawning");
    public static final Id<ExpandedParticlePayload> ID = new Id<>(PARTICLE_SPAWNING_S2C_PAYLOAD);
    public static final PacketCodec<RegistryByteBuf, ExpandedParticlePayload> CODEC =
            PacketCodec.tuple(ParticleTypes.PACKET_CODEC, ExpandedParticlePayload::parameters,
                    PacketCodecs.BOOLEAN, ExpandedParticlePayload::force,
                    Vec3d.PACKET_CODEC, ExpandedParticlePayload::pos,
                    Vec3d.PACKET_CODEC, ExpandedParticlePayload::vel,
                    ExpandedParticlePayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<ExpandedParticlePayload> {
        @Override
        public void receive(ExpandedParticlePayload par, ClientPlayNetworking.Context context) {
            World world = context.player().getEntityWorld();
            if (world != null && world.isClient()) {
                world.addParticleClient(par.parameters(),par.pos().x,par.pos().y,par.pos().z,par.vel().x,par.vel().y,par.vel().z);
            }

        }
    }

}
