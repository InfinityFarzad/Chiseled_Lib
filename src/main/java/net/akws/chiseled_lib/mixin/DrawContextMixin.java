package net.akws.chiseled_lib.mixin;

import net.akws.chiseled_lib.common.interfaces.mixin_interface.HighlightMixinInterface;
import net.akws.chiseled_lib.common.item.component.ItemHighlightComponent;
import net.akws.chiseled_lib.common.registries.ChiseledLibComponents;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public class DrawContextMixin implements HighlightMixinInterface {

    @Inject(method = "itemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lorg/joml/Matrix3x2fStack;pushMatrix()Lorg/joml/Matrix3x2fStack;", shift = At.Shift.AFTER))
    private void chiseledLib$drawStackGlint(Font font, ItemStack stack, int x, int y, String stackCountText, CallbackInfo ci) {
        chiseledLib$drawItemGlint(stack, x, y);
    }

    @Override
    public void chiseledLib$drawItemGlint(ItemStack item, int x, int y) {
        if (!item.isEmpty()) {
            ItemHighlightComponent glint = item.getOrDefault(ChiseledLibComponents.ITEM_HIGHLIGHT, ItemHighlightComponent.DEFAULT);

            if (glint.show()) {
                GuiGraphicsExtractor drawContext = (GuiGraphicsExtractor) (Object) this;
                drawContext.fillGradient(
                        x, y, x + 16, y + 16,
                        ARGB.color(2,glint.color()),
                        ARGB.color(45, glint.color())
                );
                drawContext.fillGradient(
                        x + 16, y + 16, x + 15, y,
                        ARGB.color(85, glint.color()),
                        ARGB.color(20, glint.color())
                );
                drawContext.fillGradient(
                        x + 1, y + 16, x, y,
                        ARGB.color(85, glint.color()),
                        ARGB.color(20, glint.color())
                );
                drawContext.fill(
                        x + 1, y + 16, x + 15, y + 15,
                        ARGB.color(85, glint.color())
                );
            }
        }
    }
}
