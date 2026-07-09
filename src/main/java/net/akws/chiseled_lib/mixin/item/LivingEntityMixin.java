package net.akws.chiseled_lib.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.akws.chiseled_lib.common.interfaces.item.CustomEffectsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @WrapOperation(method = "swingHand(Lnet/minecraft/util/Hand;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;swingHand(Lnet/minecraft/util/Hand;Z)V"))
    private void chiseledLib$cancelHandSwing(LivingEntity instance, Hand hand, boolean fromServerPlayer, Operation<Void> original) {
        if (instance.getStackInHand(hand).getItem() instanceof CustomEffectsItem effectsItem) {
            if (effectsItem.swingHand(instance.getStackInHand(hand))) {
                original.call(instance,hand,fromServerPlayer);
            }
        }
        original.call(instance,hand,fromServerPlayer);
    }
}
