package net.akws.chiseled_lib.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.akws.chiseled_lib.common.interfaces.item.CustomEffectsItem;
import net.akws.chiseled_lib.common.payload.EmitterParticlePayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @WrapOperation(method = "addCritParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerChunkManager;sendToNearbyPlayers(Lnet/minecraft/entity/Entity;Lnet/minecraft/network/packet/Packet;)V"))
    private void chiseled_lib$critParticles(ServerChunkManager instance, Entity entity, Packet<? super ClientPlayPacketListener> packet, Operation<Void> original, Entity target) {
        if (entity instanceof ServerPlayerEntity player) {
            ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

            if (stack.getItem() instanceof CustomEffectsItem effectsItem) {
                if (effectsItem.hasCustomParticleLogic(CustomEffectsItem.ParticleType.CRIT)) {
                    effectsItem.useCustomParticleLogic(CustomEffectsItem.ParticleType.CRIT);
                    return;
                } else if (effectsItem.critParticles() != null) {
                    ServerPlayNetworking.send(player, new EmitterParticlePayload(target.getId(), effectsItem.critParticles()));
                    return;
                }
            }
        }

        original.call(instance, entity, packet);
    }

    @WrapOperation(method = "addEnchantedHitParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerChunkManager;sendToNearbyPlayers(Lnet/minecraft/entity/Entity;Lnet/minecraft/network/packet/Packet;)V"))
    private void chiseled_lib$enchantedHitParticles(ServerChunkManager instance, Entity entity, Packet<? super ClientPlayPacketListener> packet, Operation<Void> original, Entity target) {
        if (entity instanceof ServerPlayerEntity player) {
            ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

            if (stack.getItem() instanceof CustomEffectsItem effectsItem) {
                if (effectsItem.hasCustomParticleLogic(CustomEffectsItem.ParticleType.ENCHANTED_CRIT)) {
                    effectsItem.useCustomParticleLogic(CustomEffectsItem.ParticleType.ENCHANTED_CRIT);
                    return;
                } else if (effectsItem.enchantedCritParticles() != null) {
                    ServerPlayNetworking.send(player, new EmitterParticlePayload(target.getId(), effectsItem.enchantedCritParticles()));
                    return;
                }
            }
        }

        original.call(instance, entity, packet);
    }
}
