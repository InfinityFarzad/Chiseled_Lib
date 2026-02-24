package net.akws.chiseled_lib.client.util;

import net.akws.chiseled_lib.common.payload.ExpandedParticlePayload;
import net.akws.chiseled_lib.common.util.NetworkingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RenderUtil {

    public static void renderScreenOverlay(DrawContext context, MinecraftClient client, Identifier sprite) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, sprite, 0, 0, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight(), client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight(), client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());
    }

    public static void renderScreenElement(DrawContext context, Identifier sprite, MinecraftClient client, int height, int width, int x, int y) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, sprite, client.getWindow().getScaledWidth() / 2 + x, client.getWindow().getScaledHeight() - y, 0, 0, width, height, width, height);
    }

    public static void spawnParticle(World world, ParticleEffect parameters, boolean force, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        NetworkingUtil.sendPacketToAllClients(world, new ExpandedParticlePayload(parameters,force,new Vec3d(x, y, z), new Vec3d(velocityX,velocityY,velocityZ)));
    }

}
