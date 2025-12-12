package net.akws.chiseled_lib.mixin;


import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.akws.chiseled_lib.common.interfaces.item.ItemSweepingInterface;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @ModifyExpressionValue(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isIn(Lnet/minecraft/registry/tag/TagKey;)Z")
    )
    private boolean chiseled_lib$supportSweeping(boolean original) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        return original || (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof ItemSweepingInterface item && item.hasSweeping(player.getStackInHand(Hand.MAIN_HAND)));
    }

    @Inject(method = "spawnSweepAttackParticles", at = @At(value = "HEAD"), cancellable = true)
    private void chiseled_lib$injectSpawnSweepAttackParticles(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof ItemSweepingInterface item) {
            if (item.useCustomParticleLogic(player.getMainHandStack(),player)) {
                item.customParticleSpawn(player.getMainHandStack(),player);
            } else {
                double d = -MathHelper.sin(player.getYaw() * 0.017453292F);
                double e = MathHelper.cos(player.getYaw() * 0.017453292F);
                if (player.getWorld() instanceof ServerWorld) {
                    ((ServerWorld) player.getWorld()).spawnParticles(item.getSweepingParticle(player.getStackInHand(Hand.MAIN_HAND)), player.getX() + d, player.getBodyY(0.5), player.getZ() + e, 0, d, 0.0, e, 0.0);
                }
            }

            info.cancel();
        }
    }
}
