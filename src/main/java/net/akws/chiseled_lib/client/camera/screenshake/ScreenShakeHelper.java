package net.akws.chiseled_lib.client.camera.screenshake;

import net.akws.chiseled_lib.common.payload.ScreenshakePayload;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ScreenShakeHelper {
    public static void createGlobalScreenshake(World world,int duration,float intensity) {
        for (PlayerEntity player : world.getPlayers()) {
            ScreenshakePayload.send(player,duration,new Vec3d(0,0,0), Float.MAX_VALUE,intensity);
        }
    }

    public static void createClientSideScreenshake(int duration, Vec3d pos, float radius, float intensity) {
        createClientSideScreenshake(new Screenshake(duration,pos,radius,intensity));
    }

    public static void createClientSideScreenshake(Screenshake screenshake) {
        ScreenshakePayload.send(MinecraftClient.getInstance().player, screenshake.duration,screenshake.pos, screenshake.radius, screenshake.intensity);
    }

    public static void createLocalScreenshake(int duration, Vec3d pos, float radius, float intensity, World world) {
        createLocalScreenshake(new Screenshake(duration, pos, radius, intensity),world);
    }

    public static void createLocalScreenshake(Screenshake screenshake, World world) {
        for (PlayerEntity player : world.getPlayers()) {
            ScreenshakePayload.send(player,screenshake.duration,screenshake.pos,screenshake.radius,screenshake.intensity);
        }
    }

}
