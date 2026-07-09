package net.akws.chiseled_lib.client.camera.screenflash;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

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

    public void render(GuiGraphicsExtractor context, DeltaTracker tickCounter) {
        Minecraft client = Minecraft.getInstance();
        int maxX = client.getWindow().getScreenWidth();
        int maxY = client.getWindow().getScreenHeight();
        context.fill(0, 0, maxX, maxY, colour);
    }
}
