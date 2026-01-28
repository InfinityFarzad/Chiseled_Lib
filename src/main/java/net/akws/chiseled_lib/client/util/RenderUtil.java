package net.akws.chiseled_lib.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class RenderUtil {

    public static void renderScreenOverlay(DrawContext context, MinecraftClient client, Identifier sprite) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, sprite, 0, 0, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight(), client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight(), client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());
    }

    public static void renderScreenElement(DrawContext context, Identifier sprite, MinecraftClient client, int height, int width, int x, int y) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, sprite, client.getWindow().getScaledWidth() / 2 + x, client.getWindow().getScaledHeight() - y, 0, 0, width, height, width, height);
    }


    // DO NOT USE THESE, THEY ARE INCOMPLETE AND BUGGY

    /*public static void renderCube(MatrixStack stack, VertexConsumer vertices, Vec3d pos, int color, float scale) {
        float radius = scale / 2;
        stack.push();
        renderQuad(stack.peek(), vertices, new Vec3d(pos.x + radius, pos.y + radius, pos.z + radius), new Vec3d(pos.x - radius, pos.y + radius, pos.z + radius), new Vec3d(pos.x - radius, pos.y - radius, pos.z + radius), new Vec3d(pos.x + radius, pos.y - radius, pos.z + radius), color);
        stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
        renderQuad(stack.peek(), vertices, new Vec3d(pos.x - radius, pos.y - radius, pos.z - radius), new Vec3d(pos.x + radius, pos.y - radius, pos.z - radius), new Vec3d(pos.x + radius, pos.y + radius, pos.z - radius), new Vec3d(pos.x - radius, pos.y + radius, pos.z - radius), color);
        stack.pop();
    }

    public static void renderQuad(MatrixStack.Entry stack, VertexConsumer vertices, Vec3d firstPoint, Vec3d secondPoint, Vec3d thirdPoint, Vec3d forthPoint, int color) {
        renderVertex(stack, vertices, firstPoint, color, 2, 2);
        renderVertex(stack, vertices, secondPoint, color, 2, 2);
        renderVertex(stack, vertices, thirdPoint, color, 2, 2);
        renderVertex(stack, vertices, forthPoint, color, 2, 2);
    }

    public static void renderLine(MatrixStack.Entry stack, VertexConsumer vertices, Vec3d firstPoint, Vec3d secondPoint, int color) {
        renderVertex(stack, vertices, firstPoint, color, 2, 2);
        renderVertex(stack, vertices, secondPoint, color, 2, 2);
    }

    public static void renderVertex(MatrixStack.Entry stack, VertexConsumer vertices, Vec3d pos, int color, int u, int v) {
        vertices.vertex(stack, (float) pos.x, (float) pos.y, (float) pos.z).overlay(OverlayTexture.DEFAULT_UV).color(color).normal(stack, (float) pos.x, (float) pos.y, (float) pos.z).texture(40, 40).light(503);
    }*/

}
