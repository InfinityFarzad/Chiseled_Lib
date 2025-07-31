package net.akws.chiseled_lib.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class RenderUtil {

    public static void renderScreenOverlay(DrawContext context, MinecraftClient client, Identifier sprite) {
        context.drawTexture(RenderLayer::getGuiTexturedOverlay,sprite,0,0, client.getWindow().getScaledWidth(),client.getWindow().getScaledHeight(), client.getWindow().getScaledWidth(),client.getWindow().getScaledHeight(),client.getWindow().getScaledWidth(),client.getWindow().getScaledHeight());
    }

    public static void renderScreenElement(DrawContext context, Identifier sprite,MinecraftClient client, int height, int width, int x, int y) {
        context.drawTexture(RenderLayer::getGuiTextured, sprite, client.getWindow().getScaledWidth() / 2 + x, client.getWindow().getScaledHeight() - y, 0, 0, width, height, width, height);
    }

}
