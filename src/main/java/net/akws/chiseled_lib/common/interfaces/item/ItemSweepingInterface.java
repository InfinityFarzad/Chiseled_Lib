package net.akws.chiseled_lib.common.interfaces.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;

public interface ItemSweepingInterface {

    default boolean hasSweeping(ItemStack stack) {
        return stack.isIn(ItemTags.SWORDS);
    }

    default ParticleEffect getSweepingParticle(ItemStack stack) {
        return ParticleTypes.SWEEP_ATTACK;
    }

    default boolean useCustomParticleLogic(ItemStack stack, PlayerEntity player) {
        return false;
    }

    default void customParticleSpawn(ItemStack stack, PlayerEntity player) {}

}
