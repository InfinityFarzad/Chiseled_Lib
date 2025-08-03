package net.akws.chiseled_lib.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class RenderUtil {

    public static void renderScreenOverlay(DrawContext context, MinecraftClient client, Identifier sprite) {
        context.drawTexture(RenderLayer::getGuiTexturedOverlay, sprite, 0, 0, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight(), client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight(), client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());
    }

    public static void renderScreenElement(DrawContext context, Identifier sprite, MinecraftClient client, int height, int width, int x, int y) {
        context.drawTexture(RenderLayer::getGuiTextured, sprite, client.getWindow().getScaledWidth() / 2 + x, client.getWindow().getScaledHeight() - y, 0, 0, width, height, width, height);
    }


    // DO NOT USE THESE, THEY ARE INCOMPLETE AND BUGGY

    /*
    disabled methods
    public static void renderCube(MatrixStack stack, VertexConsumer vertices, Vec3d pos, int color, float scale) {
        float radius = scale / 2;

        renderQuadruple(stack.peek(),vertices,new Vec3d(pos.x + radius,pos.y + radius, pos.z + radius),new Vec3d(pos.x - radius,pos.y + radius, pos.z + radius),new Vec3d(pos.x - radius,pos.y - radius, pos.z + radius),new Vec3d(pos.x + radius,pos.y - radius, pos.z + radius),color);
        stack.push();
        renderQuadruple(stack.peek(),vertices,new Vec3d(pos.x + radius,pos.y + radius, pos.z + radius),new Vec3d(pos.x - radius,pos.y + radius, pos.z + radius),new Vec3d(pos.x - radius,pos.y - radius, pos.z + radius),new Vec3d(pos.x + radius,pos.y - radius, pos.z + radius),color);
        stack.translate(0,0,radius);
        stack.pop();

    }

    public static void renderQuadruple(MatrixStack.Entry stack, VertexConsumer vertices, Vec3d firstPoint, Vec3d secondPoint, Vec3d thirdPoint, Vec3d forthPoint, int color) {
        renderVertex(stack,vertices,firstPoint,color);
        renderVertex(stack,vertices,secondPoint,color);
        renderVertex(stack,vertices,thirdPoint,color);
        renderVertex(stack,vertices,forthPoint,color);
    }

    public static void renderLine(MatrixStack.Entry stack, VertexConsumer vertices, Vec3d firstPoint, Vec3d secondPoint, int color) {
        renderVertex(stack,vertices,firstPoint,color);
        renderVertex(stack,vertices,secondPoint,color);

    }

    public static void renderVertex(MatrixStack.Entry stack,VertexConsumer vertices,Vec3d pos, int color) {
        vertices.vertex(stack,(float)pos.x,(float)pos.y,(float)pos.z).color(color).normal(0,1,0).overlay(OverlayTexture.DEFAULT_UV).light(10).texture(0,0);
    }*/

}
