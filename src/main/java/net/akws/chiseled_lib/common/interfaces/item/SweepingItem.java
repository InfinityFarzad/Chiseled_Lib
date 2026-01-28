package net.akws.chiseled_lib.common.interfaces.item;

import net.minecraft.item.ItemStack;

public interface SweepingItem {

    boolean canDoSweepingAttack(ItemStack stack, boolean cooldownPassed, boolean criticalHit, boolean knockbackAttack);

}
