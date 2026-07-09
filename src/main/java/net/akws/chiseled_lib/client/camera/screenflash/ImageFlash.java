package net.akws.chiseled_lib.client.camera.screenflash;

import net.akws.chiseled_lib.client.util.RenderUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
public class ImageFlash extends Flash {

    protected Identifier sprite;
    public ImageFlash(int duration, Identifier sprite) {
        super(duration);
        this.sprite = sprite;
        this.duration =duration;
        this.removed = false;
    }

    @Override
    public void remove() {
        super.remove();
        System.out.println("fuck");
    }

    public void render(GuiGraphicsExtractor context, DeltaTracker tickCounter) {
        RenderUtil.renderScreenOverlay(context,sprite);
    }
}
