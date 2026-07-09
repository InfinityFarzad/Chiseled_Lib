package net.akws.chiseled_lib.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface RiptideStackAccesor {
    @Accessor("autoSpinAttackItemStack")
    ItemStack stack();
}
