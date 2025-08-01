package net.akws.chiseled_lib.mixin;

import net.akws.chiseled_lib.common.mixin_interface.RiptideMixinInterface;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void twisted_and_carved$updateStrideStack(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof PlayerEntity playerEntity) {
            ((RiptideMixinInterface)playerEntity).chiseled_lib$setRiptideStack(((RiptideStackAccesor)playerEntity).stack());
        }
    }

}
