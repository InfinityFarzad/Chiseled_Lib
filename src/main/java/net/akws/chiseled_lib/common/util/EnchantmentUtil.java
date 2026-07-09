package net.akws.chiseled_lib.common.util;

import net.akws.chiseled_lib.mixin.RiptideStackAccesor;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentUtil {

    public static boolean hasEnchantment(ItemStack stack, ResourceKey<Enchantment> enchantment) {
        return stack.getEnchantments().keySet().toString().contains(enchantment.identifier().toString());
    }

    public static int getEnchantmentLevel(ItemStack stack, ResourceKey<Enchantment> enchantment) {
        for(Holder<Enchantment> enchantEntry : stack.getEnchantments().keySet()) {
            if(enchantEntry.unwrapKey().isPresent() && enchantEntry.unwrapKey().get().equals(enchantment)) {
                return stack.getEnchantments().getLevel(enchantEntry);
            }
        }
        return 0;
    }

    public static ItemStack getRiptideStack(Player player) {
        ItemStack stack = ((RiptideStackAccesor) player).stack();
        if (stack != null) {
            return ((RiptideStackAccesor) player).stack();
        } else {
            return ItemStack.EMPTY;
        }
    }
}
