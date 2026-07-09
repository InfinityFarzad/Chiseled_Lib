package net.akws.chiseled_lib.common.interfaces.item;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;

public interface CustomEffectsItem {

    default boolean hasCustomParticleLogic(ParticleType particleType, ItemStack stack) {
        return false;
    }

    default void useCustomParticleLogic(ParticleType particleType, ItemStack stack) {}

    default net.minecraft.sounds.SoundEvent critSound(ItemStack stack) {
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

    default ParticleOptions critParticles(ItemStack stack) {
        return null;
    }

    default ParticleOptions enchantedCritParticles(ItemStack stack) {
        return null;
    }

    default ParticleOptions sweepParticles(ItemStack stack) {
        return null;
    }

    default boolean swingHand(ItemStack stack) {return true;}

    enum ParticleType {
        CRIT,
        ENCHANTED_CRIT,
        SWEEP
    }
}
