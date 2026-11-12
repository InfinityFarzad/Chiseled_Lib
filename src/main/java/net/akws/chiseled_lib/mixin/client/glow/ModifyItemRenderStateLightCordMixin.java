package net.akws.chiseled_lib.mixin.client.glow;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.akws.chiseled_lib.common.interfaces.mixin_interface.ItemStackRenderStateInterface;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.util.LightCoordsUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStackRenderState.class)
public class ModifyItemRenderStateLightCordMixin implements ItemStackRenderStateInterface {
    @Unique
    private boolean chiseled_lib$isEmissive = false;

    @WrapOperation(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState;submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;III)V"))
    private void chiseled_lib$causeEmission(ItemStackRenderState.LayerRenderState instance, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor, Operation<Void> original) {
        if (chiseled_lib$isEmissive) {
            original.call(instance, poseStack, submitNodeCollector, LightCoordsUtil.FULL_BRIGHT, overlayCoords, outlineColor);
        } else {
            original.call(instance, poseStack, submitNodeCollector, lightCoords, overlayCoords, outlineColor);
        }
    }

    @Override
    public void chiseled_lib$setEmissive(boolean value) {
        chiseled_lib$isEmissive = value;
    }
}
