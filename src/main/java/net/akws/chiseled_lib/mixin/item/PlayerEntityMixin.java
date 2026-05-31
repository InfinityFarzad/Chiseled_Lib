package net.akws.chiseled_lib.mixin.item;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.akws.chiseled_lib.common.interfaces.item.CustomAttackItem;
import net.akws.chiseled_lib.common.interfaces.item.CustomEffectsItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow
    protected abstract boolean isCriticalHit(Entity target);

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F",shift = At.Shift.BEFORE))
    private void chiseled_lib$triggerCustomAttacks(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object)this;
        ItemStack weapon = player.getWeaponStack();
        float attackCooldownProgress = player.getAttackCooldownProgress(0.5f);
        if (attackCooldownProgress > 0.9 && target instanceof LivingEntity living && weapon.getItem() instanceof CustomAttackItem attackItem) {
            if (isCriticalHit(target)) {
                attackItem.onCritAttack(player,living,weapon);
            }
            attackItem.onFullAttack(player,living,weapon);
        }

    }

    @Inject(method = "doSweepingAttack", at = @At("TAIL"))
    private void chiseled_lib$onSweepAttack(Entity target, float damage, DamageSource damageSource, float cooldownProgress, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object)this;
        ItemStack weaponStack = player.getWeaponStack();
        if (weaponStack != null && weaponStack.getItem() instanceof CustomAttackItem attackItem && target instanceof LivingEntity living) {
            attackItem.onSweepAttack(player,living,player.getWeaponStack());
        }
    }

    @ModifyReturnValue(method = "canUseSweepAttack", at = @At("RETURN"))
    private boolean chiseled_lib$sweepingItem(boolean original, boolean cooldownPassed, boolean criticalHit, boolean knockback) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        if (stack.getItem() instanceof CustomAttackItem attackItem) {
            return attackItem.canDoSweepingAttack(stack, cooldownPassed, criticalHit, knockback);
        }

        return original;
    }

    @WrapOperation(method = "doSweepingAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;spawnParticles(Lnet/minecraft/particle/ParticleEffect;DDDIDDDD)I"))
    private int chiseled_lib$sweepParticle(ServerWorld serverWorld, ParticleEffect particleEffect, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double speed, Operation<Integer> original) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        if (stack.getItem() instanceof CustomEffectsItem effectsItem) {
            if (effectsItem.hasCustomParticleLogic(CustomEffectsItem.ParticleType.SWEEP)) {
                effectsItem.useCustomParticleLogic(CustomEffectsItem.ParticleType.SWEEP);
                return 0;
            } else if (effectsItem.sweepParticles() != null) {
                particleEffect = effectsItem.sweepParticles();
            }
        }

        return original.call(serverWorld, particleEffect, x, y, z, count, offsetX, offsetY, offsetZ, speed);
    }

    @ModifyArg(method = "doSweepingAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;playAttackSound(Lnet/minecraft/sound/SoundEvent;)V"))
    private SoundEvent chiseled_lib$sweepSound(SoundEvent sound) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        if (stack.getItem() instanceof CustomEffectsItem effectsItem) {
            if (effectsItem.sweepSound() != null) {
                return effectsItem.sweepSound();
            }
        }
        return sound;
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;playAttackSound(Lnet/minecraft/sound/SoundEvent;)V", ordinal = 0))
    private SoundEvent chiseled_lib$knockbackSound(SoundEvent sound) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        if (stack.getItem() instanceof CustomEffectsItem effectsItem) {
            if (effectsItem.knockbackSound() != null) {
                return effectsItem.knockbackSound();
            }
        }
        return sound;
    }

    @ModifyArg(method = "addAttackParticlesAndSounds", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;playAttackSound(Lnet/minecraft/sound/SoundEvent;)V", ordinal = 0))
    private SoundEvent chiseled_lib$critSound(SoundEvent sound) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        if (stack.getItem() instanceof CustomEffectsItem effectsItem) {
            if (effectsItem.critSound() != null) {
                return effectsItem.critSound();
            }
        }
        return sound;
    }

    @WrapOperation(method = "addAttackParticlesAndSounds", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;playAttackSound(Lnet/minecraft/sound/SoundEvent;)V", ordinal = 1))
    private void chiseled_lib$weakNStrongSound(PlayerEntity player, SoundEvent sound, Operation<Void> original, Entity target, boolean criticalHit, boolean sweeping, boolean cooldownPassed) {
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        if (stack.getItem() instanceof CustomEffectsItem effectsItem) {
            if (cooldownPassed) {
                if (effectsItem.strongSound() != null) {
                    original.call(player, effectsItem.strongSound());
                    return;
                }
            } else if (effectsItem.weakSound() != null) {
                original.call(player, effectsItem.weakSound());
                return;
            }
        }
        original.call(player, sound);
    }
}
