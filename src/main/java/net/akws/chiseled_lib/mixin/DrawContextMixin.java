package net.akws.chiseled_lib.mixin;

import net.akws.chiseled_lib.common.interfaces.mixin_interface.HighlightMixinInterface;
import net.akws.chiseled_lib.common.item.component.ItemHighlightComponent;
import net.akws.chiseled_lib.common.registries.ChiseledLibComponents;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public class DrawContextMixin implements HighlightMixinInterface {

    @Inject(method = "drawStackOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lorg/joml/Matrix3x2fStack;pushMatrix()Lorg/joml/Matrix3x2fStack;", shift = At.Shift.AFTER))
    private void chiseledLib$drawStackGlint(TextRenderer textRenderer, ItemStack stack, int x, int y, String stackCountText, CallbackInfo ci) {
        chiseledLib$drawItemGlint(stack, x, y);
    }

    @Override
    public void chiseledLib$drawItemGlint(ItemStack item, int x, int y) {
        if (!item.isEmpty()) {
            ItemHighlightComponent glint = item.getOrDefault(ChiseledLibComponents.ITEM_HIGHLIGHT, ItemHighlightComponent.DEFAULT);

            if (glint.show()) {
                DrawContext drawContext = (DrawContext) (Object) this;
                drawContext.fillGradient(
                        x, y, x + 16, y + 16,
                        ColorHelper.withAlpha(2, glint.color()),
                        ColorHelper.withAlpha(45, glint.color())
                );
                drawContext.fillGradient(
                        x + 16, y + 16, x + 15, y,
                        ColorHelper.withAlpha(85, glint.color()),
                        ColorHelper.withAlpha(20, glint.color())
                );
                drawContext.fillGradient(
                        x + 1, y + 16, x, y,
                        ColorHelper.withAlpha(85, glint.color()),
                        ColorHelper.withAlpha(20, glint.color())
                );
                drawContext.fill(
                        x + 1, y + 16, x + 15, y + 15,
                        ColorHelper.withAlpha(85, glint.color())
                );
            }
        }
    }
}
