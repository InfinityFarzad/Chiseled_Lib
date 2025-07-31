package net.akws.chiseled_lib.common.util;

import net.akws.chiseled_lib.common.mixin_interfaces.RiptideMixinInterface;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

public class EnchantmentUtil {
    public static boolean hasEnchantment(ItemStack stack, RegistryKey<Enchantment> enchantment) {
        return stack.getEnchantments().getEnchantments().toString().
                contains(enchantment.getValue().toString());
    }

    public static int getLevel(ItemStack stack, RegistryKey<Enchantment> enchantmentRegistryKey){
        for (RegistryEntry<Enchantment> enchantments : stack.getEnchantments().getEnchantments()){
            if (enchantments.toString().contains(enchantmentRegistryKey.getValue().toString())){
                return stack.getEnchantments().getLevel(enchantments);
            }
        }
        return 0;
    }

    public static ItemStack getRiptideStack(PlayerEntity player) {
        ItemStack stack = ((RiptideMixinInterface)player).chiseled_lib$getRiptideStack();
        if (stack != null) {
            return ((RiptideMixinInterface)player).chiseled_lib$getRiptideStack();
        } else {
            return ItemStack.EMPTY;
        }
    }

}
