package net.akws.chiseled_lib.client.camera.screenflash;

import net.akws.chiseled_lib.client.util.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

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

    public void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        RenderUtil.renderScreenOverlay(context,sprite);
    }
}
