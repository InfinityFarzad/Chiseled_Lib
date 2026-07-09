package net.akws.chiseled_lib.mixin.client.flash;

import net.akws.chiseled_lib.client.camera.screenflash.Flashes;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
        Gui.class
)
public class GuiFlashMixin {
    @Inject(method = "extractCameraOverlays", at = @At("HEAD"))
    private void chiseledLib$renderFlashOverlay(GuiGraphicsExtractor graphicsExtractor, DeltaTracker deltaTracker, CallbackInfo ci) {
        Flashes.instance().render(graphicsExtractor, deltaTracker);
    }
}
