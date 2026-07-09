package net.akws.chiseled_lib.client.camera.screenshake;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class Screenshake {
    private Random random = Random.create();

    public int fullDuration;
    public int duration;
    public final Vec3d pos;
    public final float radius;
    public final float intensity;

    private boolean removed = false;

    protected Screenshake(int duration, Vec3d pos, float radius, float intensity) {
        this.fullDuration = duration;
        this.duration = duration;
        this.pos = pos;
        this.radius = radius;
        this.intensity = intensity;
    }

    public void tick() {
        this.duration--;
        if (this.duration <= 0) {
            this.remove();
        }
    }

    public float getDelta() {
        return (float) this.duration / (float) this.fullDuration;
    }

    public boolean isRemoved() {
        return this.removed;
    }

    public void remove() {
        this.removed = true;
    }

    public Random getRandom() {
        return this.random;
    }

}
