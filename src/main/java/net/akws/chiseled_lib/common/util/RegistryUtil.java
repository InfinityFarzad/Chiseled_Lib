package net.akws.chiseled_lib.common.util;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class RegistryUtil {

    public static <T extends ParticleEffect> ParticleType<T> registerComplexParticle(
            Identifier identifier,
            Boolean alwaysShow,
            Function<ParticleType<T>, MapCodec<T>> codec,
            Function<ParticleType<T>, PacketCodec<? super RegistryByteBuf, T>> packetCodec) {
            Registry.register(Registries.PARTICLE_TYPE,identifier, new ParticleType<T>(alwaysShow) {

                @Override
                public MapCodec<T> getCodec() {
                    return codec.apply(this);
                }

                @Override
                public PacketCodec<? super RegistryByteBuf, T> getPacketCodec() {
                    return packetCodec.apply(this);
                }
            });
            return FabricParticleTypes.complex(codec,packetCodec);
    }

    public static SimpleParticleType registerSimpleParticle(Identifier id) {
        Registry.register(Registries.PARTICLE_TYPE,id, FabricParticleTypes.simple());
        return FabricParticleTypes.simple();
    }

    public static RenderPipeline registerPipeline(RenderPipeline renderPipeline) {
        RenderPipelines.register(renderPipeline);
        return renderPipeline;
    }

}
