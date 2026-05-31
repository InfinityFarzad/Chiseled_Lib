package net.akws.chiseled_lib.common.interfaces.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface CustomAttackItem {

    boolean canDoSweepingAttack(ItemStack stack, boolean cooldownPassed, boolean criticalHit, boolean knockbackAttack);

    void onCritAttack(LivingEntity attacker, LivingEntity target, ItemStack stack);
    void onFullAttack(LivingEntity attacker, LivingEntity target, ItemStack stack);
    void onSweepAttack(LivingEntity attacker, LivingEntity target, ItemStack stack);
}
