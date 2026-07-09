package net.akws.chiseled_lib.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.akws.chiseled_lib.common.interfaces.item.CustomEffectsItem;
import net.akws.chiseled_lib.common.payload.EmitterParticlePayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayer.class)
public class ServerPlayerEntityMixin {

    @WrapOperation(method = "crit", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerChunkCache;sendToTrackingPlayersAndSelf(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/protocol/Packet;)V"))
    private void chiseled_lib$critParticles(ServerChunkCache instance, Entity entity, Packet<? super ClientGamePacketListener> packet, Operation<Void> original, Entity target) {
        if (entity instanceof ServerPlayer player) {
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (stack.getItem() instanceof CustomEffectsItem effectsItem) {
                if (effectsItem.hasCustomParticleLogic(CustomEffectsItem.ParticleType.CRIT, stack)) {
                    effectsItem.useCustomParticleLogic(CustomEffectsItem.ParticleType.CRIT, stack);
                    return;
                } else if (effectsItem.critParticles(stack) != null) {
                    ServerPlayNetworking.send(player, new EmitterParticlePayload(target.getId(), effectsItem.critParticles(stack)));
                    return;
                }
            }
        }

        original.call(instance, entity, packet);
    }

    @WrapOperation(method = "magicCrit", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerChunkCache;sendToTrackingPlayersAndSelf(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/protocol/Packet;)V"))
    private void chiseled_lib$enchantedHitParticles(ServerChunkCache instance, Entity entity, Packet<? super ClientGamePacketListener> packet, Operation<Void> original, Entity target) {
        if (entity instanceof ServerPlayer player) {
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (stack.getItem() instanceof CustomEffectsItem effectsItem) {
                if (effectsItem.hasCustomParticleLogic(CustomEffectsItem.ParticleType.ENCHANTED_CRIT, stack)) {
                    effectsItem.useCustomParticleLogic(CustomEffectsItem.ParticleType.ENCHANTED_CRIT, stack);
                    return;
                } else if (effectsItem.enchantedCritParticles(stack) != null) {
                    ServerPlayNetworking.send(player, new EmitterParticlePayload(target.getId(), effectsItem.enchantedCritParticles(stack)));
                    return;
                }
            }
        }

        original.call(instance, entity, packet);
    }
}
