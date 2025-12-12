package net.akws.chiseled_lib.client.camera_effects;

import net.akws.chiseled_lib.common.ChiseledLib;
import net.akws.chiseled_lib.common.component.screen_shake.ScreenshakeDataComponent;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class Screenshake {

    public float shakeTicks;
    public float intensity;
    public Vec3d pos;
    public float radius;

    public Screenshake(float intensity, float duration, Vec3d pos, float radius) {
        this.shakeTicks = duration;
        this.intensity = intensity;
        this.pos = pos;
        this.radius = radius;
    }

    public static void createScreenShake(Screenshake data, PlayerEntity player) {
        if (ChiseledLib.screenshakes != null) {
            ChiseledLib.screenshakes.add(data);
        }
    }

    public static void tick() {
        if (ChiseledLib.screenshakes != null) {
            ChiseledLib.screenshakes.removeIf(screenshake -> screenshake.shakeTicks <= 0);
        }
    }

}
