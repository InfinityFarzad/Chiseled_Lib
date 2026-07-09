package net.akws.chiseled_lib.mixin.client.flash;

import net.akws.chiseled_lib.client.camera.screenflash.Flashes;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
        InGameHud.class
)
public class GuiFlashMixin {

    @Inject(method = "renderMiscOverlays", at = @At("HEAD"))
    private void chiseledLib$renderFlashOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        Flashes.instance().render(context,tickCounter);
    }


}
