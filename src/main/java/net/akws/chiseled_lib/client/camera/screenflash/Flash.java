package net.akws.chiseled_lib.client.camera.screenflash;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

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

    public void render(DrawContext context, RenderTickCounter tickCounter) {}
}
