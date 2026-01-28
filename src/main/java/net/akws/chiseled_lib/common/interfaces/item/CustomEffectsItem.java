package net.akws.chiseled_lib.common.interfaces.item;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundEvent;

public interface CustomEffectsItem {

    default boolean hasCustomParticleLogic(ParticleType particleType) {
        return false;
    }

    default void useCustomParticleLogic(ParticleType particleType) {}

    default SoundEvent critSound() {
        return null;
    }

    default SoundEvent sweepSound() {
        return null;
    }

    default SoundEvent knockbackSound() {
        return null;
    }

    default SoundEvent strongSound() {
        return null;
    }

    default SoundEvent weakSound() {
        return null;
    }

    default ParticleEffect critParticles() {
        return null;
    }

    default ParticleEffect enchantedCritParticles() {
        return null;
    }

    default ParticleEffect sweepParticles() {
        return null;
    }

    enum ParticleType {
        CRIT,
        ENCHANTED_CRIT,
        SWEEP
    }
}
