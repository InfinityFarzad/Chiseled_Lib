package net.akws.chiseled_lib.common.interfaces.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface CustomAttackItem {

    default boolean canDoSweepingAttack(ItemStack stack, boolean cooldownPassed, boolean criticalHit, boolean knockbackAttack) {
        return true;
    }

    default void onAttackWithProgress(LivingEntity attacker, LivingEntity target, ItemStack stack) {}

    default void onCritAttack(LivingEntity attacker, LivingEntity target, ItemStack stack) {}

    default void onFullAttack(LivingEntity attacker, LivingEntity target, ItemStack stack) {}

    default void onSweepAttack(LivingEntity attacker, LivingEntity target, ItemStack stack) {}
}
