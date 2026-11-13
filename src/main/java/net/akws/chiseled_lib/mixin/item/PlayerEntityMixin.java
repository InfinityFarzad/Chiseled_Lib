package net.akws.chiseled_lib.mixin.item;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.akws.chiseled_lib.common.interfaces.item.CustomAttackItem;
import net.akws.chiseled_lib.common.interfaces.item.CustomEffectsItem;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {

    @Shadow
    protected abstract boolean canCriticalAttack(Entity entity);

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getAttackStrengthScale(F)F", shift = At.Shift.BEFORE))
    private void chiseled_lib$triggerCustomAttacks(Entity entity, CallbackInfo ci) {
        Player player = (Player) (Object)this;
        ItemStack weapon = player.getWeaponItem();
        float attackCooldownProgress = player.getAttackStrengthScale(0.5f);

        if (weapon.getItem() instanceof CustomAttackItem attackItem && entity instanceof LivingEntity living) {
            if (attackCooldownProgress > 0.9) {
                if (canCriticalAttack(entity)) {
                    attackItem.onCritAttack(player,living,weapon);
                }
                attackItem.onFullAttack(player,living,weapon);
            }
            attackItem.onAttackWithProgress(player,living,weapon, attackCooldownProgress);

        }

    }

    @Inject(method = "doSweepAttack", at = @At("TAIL"))
    private void chiseled_lib$onSweepAttack(Entity entity, float baseDamage, DamageSource damageSource, float attackStrengthScale, CallbackInfo ci) {
        Player player = (Player) (Object)this;
        ItemStack weaponStack = player.getWeaponItem();
        if (weaponStack != null && weaponStack.getItem() instanceof CustomAttackItem attackItem && entity instanceof LivingEntity living) {
            attackItem.onSweepAttack(player,living,player.getWeaponItem());
        }
    }

    @ModifyReturnValue(method = "isSweepAttack", at = @At("RETURN"))
    private boolean chiseled_lib$sweepingItem(boolean original, boolean fullStrengthAttack, boolean criticalAttack, boolean knockbackAttack) {
        Player player = (Player) (Object) this;
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (stack.getItem() instanceof CustomAttackItem attackItem) {
            return attackItem.canDoSweepingAttack(player, stack, fullStrengthAttack, criticalAttack, knockbackAttack);
        }

        return original;
    }

    @WrapOperation(method = "doSweepAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"))
    private int chiseled_lib$sweepParticle(ServerLevel serverWorld, ParticleOptions particle, double x, double y, double z, int count, double xDist, double yDist, double zDist, double speed, Operation<Integer> original) {
        Player player = (Player) (Object) this;
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (stack.getItem() instanceof CustomEffectsItem effectsItem) {
            if (effectsItem.hasCustomParticleLogic(CustomEffectsItem.ParticleType.SWEEP, stack, player, player.level())) {
                effectsItem.useCustomParticleLogic(CustomEffectsItem.ParticleType.SWEEP, stack, player, player.level());
                return 0;
            } else if (effectsItem.sweepParticles(stack) != null) {
                particle = effectsItem.sweepParticles(stack);
            }
        }

        return original.call(serverWorld, particle, x, y, z, count, xDist, yDist, zDist, speed);
    }

    @ModifyArg(method = "doSweepAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;playServerSideSound(Lnet/minecraft/sounds/SoundEvent;)V"))
    private SoundEvent chiseled_lib$sweepSound(SoundEvent sound) {
        Player player = (Player) (Object) this;
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (stack.getItem() instanceof CustomEffectsItem effectsItem) {
            if (effectsItem.sweepSound(stack) != null) {
                return effectsItem.sweepSound(stack);
            }
        }
        return sound;
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;playServerSideSound(Lnet/minecraft/sounds/SoundEvent;)V", ordinal = 0))
    private SoundEvent chiseled_lib$knockbackSound(SoundEvent sound) {
        Player player = (Player) (Object) this;
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (stack.getItem() instanceof CustomEffectsItem effectsItem) {
            if (effectsItem.knockbackSound(stack) != null) {
                return effectsItem.knockbackSound(stack);
            }
        }
        return sound;
    }

    @ModifyArg(method = "attackVisualEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;playServerSideSound(Lnet/minecraft/sounds/SoundEvent;)V", ordinal = 0))
    private SoundEvent chiseled_lib$critSound(SoundEvent sound) {
        Player player = (Player) (Object) this;
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (stack.getItem() instanceof CustomEffectsItem effectsItem) {
            if (effectsItem.critSound(stack) != null) {
                return effectsItem.critSound(stack);
            }
        }
        return sound;
    }

    @WrapOperation(method = "attackVisualEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;playServerSideSound(Lnet/minecraft/sounds/SoundEvent;)V", ordinal = 1))
    private void chiseled_lib$weakNStrongSound(Player player, SoundEvent sound, Operation<Void> original, Entity entity, boolean criticalAttack, boolean sweepAttack, boolean fullStrengthAttack) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (stack.getItem() instanceof CustomEffectsItem effectsItem) {
            if (fullStrengthAttack) {
                if (effectsItem.strongSound(stack) != null) {
                    original.call(player, effectsItem.strongSound(stack));
                    return;
                }
            } else if (effectsItem.weakSound(stack) != null) {
                original.call(player, effectsItem.weakSound(stack));
                return;
            }
        }
        original.call(player, sound);
    }
}
