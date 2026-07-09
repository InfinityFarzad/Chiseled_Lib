package net.akws.chiseled_lib.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.akws.chiseled_lib.common.interfaces.item.CustomEffectsItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @WrapOperation(method = "swing(Lnet/minecraft/world/InteractionHand;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;swing(Lnet/minecraft/world/InteractionHand;Z)V"))
    private void chiseledLib$cancelHandSwing(LivingEntity instance, InteractionHand hand, boolean fromServerPlayer, Operation<Void> original) {
        if (instance.getItemInHand(hand).getItem() instanceof CustomEffectsItem effectsItem) {
            if (effectsItem.swingHand(instance.getItemInHand(hand))) {
                original.call(instance,hand,fromServerPlayer);
            }
        }
        original.call(instance,hand,fromServerPlayer);
    }
}
