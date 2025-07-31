package net.akws.chiseled_lib.mixin;

import net.akws.chiseled_lib.common.ChiseledLib;
import net.akws.chiseled_lib.common.component.ChiseledLibComponents;
import net.akws.chiseled_lib.common.component.ItemHighlightComponent;
import net.akws.chiseled_lib.common.mixin_interface.HighlightMixinInterface;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public class DrawContextMixin implements HighlightMixinInterface {

    @Inject(method = "drawStackOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", shift = At.Shift.AFTER))
    private void chiseledLib$drawStackGlint(TextRenderer textRenderer, ItemStack stack, int x, int y, String stackCountText, CallbackInfo ci) {
        chiseledLib$drawItemGlint(stack,x,y);
    }

    @Override
    public void chiseledLib$drawItemGlint(ItemStack item, int x, int y) {
        if (!item.isEmpty() && item.contains(ChiseledLibComponents.ITEM_HIGHLIGHT)) {
            ItemHighlightComponent glint = item.get(ChiseledLibComponents.ITEM_HIGHLIGHT);
            if (glint.show()) {
                DrawContext drawContext = (DrawContext) (Object) this;
                drawContext.fillGradient(RenderLayer.getGuiOverlay(),x,y,x +16, y + 16,ColorHelper.withAlpha(2,glint.color()),  ColorHelper.withAlpha(45,glint.color()),200);
                drawContext.fillGradient(RenderLayer.getGuiOverlay(),x + 16,y + 16 ,x + 15,y,ColorHelper.withAlpha(85,glint.color()),ColorHelper.withAlpha(20,glint.color()),200);
                drawContext.fillGradient(RenderLayer.getGuiOverlay(),x + 1,y + 16 ,x,y,ColorHelper.withAlpha(85,glint.color()),ColorHelper.withAlpha(20,glint.color()),200);
                drawContext.fill(RenderLayer.getGuiOverlay(),x + 1,y + 16,x + 15,y + 15,200,ColorHelper.withAlpha(85,glint.color()));
            }
        }
    }
}
