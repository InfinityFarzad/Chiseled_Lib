package net.akws.chiseled_lib.client.camera.screenflash;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class Flash {

    protected int duration;
    protected boolean removed;

    public Flash(int duration) {
        this.duration =duration;
        this.removed = false;
    }

    public void tick() {
        if (duration <= 0) {
            this.remove();
        }
        duration--;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void remove() {
        this.removed =true;
    }

    public void render(GuiGraphicsExtractor context, DeltaTracker tickCounter) {}
}
