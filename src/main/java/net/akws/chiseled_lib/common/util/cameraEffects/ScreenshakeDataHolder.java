package net.akws.chiseled_lib.common.util.cameraEffects;

import net.akws.chiseled_lib.common.ChiseledLib;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

public class ScreenshakeDataHolder {

    public float shakeTicks;
    public float intensity;
    public Vec3d pos;
    public float radius;

    public ScreenshakeDataHolder(float intensity, float duration, Vec3d pos, float radius) {
        this.shakeTicks = duration;
        this.intensity = intensity;
        this.pos = pos;
        this.radius = radius;
    }

}
