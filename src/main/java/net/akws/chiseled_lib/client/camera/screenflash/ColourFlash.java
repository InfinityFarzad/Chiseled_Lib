package net.akws.chiseled_lib.client.camera.screenflash;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class ColourFlash extends Flash {
    protected int colour;
    public ColourFlash(int duration, int colour) {
        super(duration);
        this.colour = colour;
        this.duration =duration;
        this.removed = false;
    }

    @Override
    public void remove() {
        super.remove();
    }

    public void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        int maxX = client.getWindow().getWidth();
        int maxY = client.getWindow().getHeight();
        context.fill(0, 0, maxX, maxY, colour);
    }
}
