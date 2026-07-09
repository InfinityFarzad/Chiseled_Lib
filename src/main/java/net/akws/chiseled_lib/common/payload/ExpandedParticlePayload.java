package net.akws.chiseled_lib.common.payload;

import net.akws.chiseled_lib.common.ChiseledLib;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public record ExpandedParticlePayload(ParticleOptions parameters, boolean force, Vec3 pos, Vec3 vel) implements CustomPacketPayload {
    public static final Identifier PARTICLE_SPAWNING_S2C_PAYLOAD = ChiseledLib.id("expanded_particle_spawning");
    public static final CustomPacketPayload.Type<ExpandedParticlePayload> TYPE = new Type<>(PARTICLE_SPAWNING_S2C_PAYLOAD);
    public static final StreamCodec<RegistryFriendlyByteBuf, ExpandedParticlePayload> CODEC =
            StreamCodec.composite(ParticleTypes.STREAM_CODEC, ExpandedParticlePayload::parameters,
                    ByteBufCodecs.BOOL, ExpandedParticlePayload::force,
                    Vec3.STREAM_CODEC, ExpandedParticlePayload::pos,
                    Vec3.STREAM_CODEC, ExpandedParticlePayload::vel,
                    ExpandedParticlePayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<ExpandedParticlePayload> {
        @Override
        public void receive(ExpandedParticlePayload par, ClientPlayNetworking.Context context) {
            Level level = context.player().level();
            if (level != null && level.isClientSide()) {
                level.addParticle(par.parameters(),par.pos().x,par.pos().y,par.pos().z,par.vel().x,par.vel().y,par.vel().z);
            }

        }
    }

}
