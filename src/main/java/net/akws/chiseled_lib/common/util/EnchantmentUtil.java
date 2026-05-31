package net.akws.chiseled_lib.common.util;

import net.akws.chiseled_lib.mixin.RiptideStackAccesor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

public class EnchantmentUtil {

    public static boolean hasEnchantment(ItemStack stack, RegistryKey<Enchantment> enchantment) {
        return enchantment.getValue() != null & stack.getEnchantments().getEnchantments().toString().contains(enchantment.getValue().toString());
    }

    public static int getEnchantmentLevel(ItemStack stack, RegistryKey<Enchantment> enchantment) {
        for(RegistryEntry<Enchantment> enchantEntry : stack.getEnchantments().getEnchantments()) {
            if(enchantEntry.getKey().isPresent() && enchantEntry.getKey().get().equals(enchantment)) {
                return stack.getEnchantments().getLevel(enchantEntry);
            }
        }
        return 0;
    }

    public static ItemStack getRiptideStack(PlayerEntity player) {
        ItemStack stack = ((RiptideStackAccesor) player).stack();
        if (stack != null) {
            return ((RiptideStackAccesor) player).stack();
        } else {
            return ItemStack.EMPTY;
        }
    }
}
