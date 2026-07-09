package net.akws.chiseled_lib.client.camera.screenshake;

import net.akws.chiseled_lib.common.payload.ScreenshakePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ScreenShakeHelper {
    public static void createGlobalScreenshake(Level level, int duration, float intensity) {
        for (Player player : level.players()) {
            ScreenshakePayload.send(player,duration,new Vec3(0,0,0), Float.MAX_VALUE,intensity);
        }
    }

    public static void createClientSideScreenshake(int duration, Vec3 pos, float radius, float intensity) {
        createClientSideScreenshake(new Screenshake(duration,pos,radius,intensity));
    }

    public static void createClientSideScreenshake(Screenshake screenshake) {
        ScreenshakePayload.send(Minecraft.getInstance().player, screenshake.duration,screenshake.pos, screenshake.radius, screenshake.intensity);
    }

    public static void createLocalScreenshake(int duration, Vec3 pos, float radius, float intensity, Level level) {
        createLocalScreenshake(new Screenshake(duration, pos, radius, intensity),level);
    }

    public static void createLocalScreenshake(Screenshake screenshake, Level level) {
        for (Player player : level.players()) {
            ScreenshakePayload.send(player,screenshake.duration,screenshake.pos,screenshake.radius,screenshake.intensity);
        }
    }

}
