package net.akws.chiseled_lib.common.interfaces.item;

import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundEvent;

public interface CustomEffectsItem {

    default boolean hasCustomParticleLogic(ParticleType particleType, ItemStack stack) {
        return false;
    }

    default void useCustomParticleLogic(ParticleType particleType, ItemStack stack) {}

    default SoundEvent critSound(ItemStack stack) {
        return null;
    }

    default SoundEvent sweepSound(ItemStack stack) {
        return null;
    }

    default SoundEvent knockbackSound(ItemStack stack) {
        return null;
    }

    default SoundEvent strongSound(ItemStack stack) {
        return null;
    }

    default SoundEvent weakSound(ItemStack stack) {
        return null;
    }

    default ParticleEffect critParticles(ItemStack stack) {
        return null;
    }

    default ParticleEffect enchantedCritParticles(ItemStack stack) {
        return null;
    }

    default ParticleEffect sweepParticles(ItemStack stack) {
        return null;
    }

    default boolean swingHand(ItemStack stack) {return true;}

    enum ParticleType {
        CRIT,
        ENCHANTED_CRIT,
        SWEEP
    }
}
