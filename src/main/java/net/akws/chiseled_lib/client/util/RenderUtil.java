package net.akws.chiseled_lib.client.util;

import net.akws.chiseled_lib.common.payload.ExpandedParticlePayload;
import net.akws.chiseled_lib.common.util.NetworkingUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;


public class RenderUtil {

    public static void renderScreenOverlay(GuiGraphicsExtractor context, Identifier sprite) {
        Minecraft client = Minecraft.getInstance();
        context.blit(RenderPipelines.GUI_TEXTURED, sprite, 0, 0, client.getWindow().getGuiScaledWidth(), client.getWindow().getGuiScaledHeight(), client.getWindow().getGuiScaledWidth(), client.getWindow().getGuiScaledHeight(), client.getWindow().getGuiScaledWidth(), client.getWindow().getGuiScaledHeight());
    }

    public static void renderScreenElement(GuiGraphicsExtractor context, Identifier sprite, int height, int width, int x, int y) {
        Minecraft client = Minecraft.getInstance();
        context.blit(RenderPipelines.GUI_TEXTURED, sprite, client.getWindow().getScreenWidth() / 2 + x, client.getWindow().getScreenHeight() - y, 0, 0, width, height, width, height);
    }

    public static void spawnParticle(Level level, ParticleOptions parameters, boolean force, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        NetworkingUtil.sendPacketToAllClients(level, new ExpandedParticlePayload(parameters,force,new Vec3(x, y, z), new Vec3(velocityX,velocityY,velocityZ)));
    }

    @SuppressWarnings("SameParameterValue")
    public static MutableComponent createTextGradient(String text, List<Integer> colours) {
        List<Character> charList = text.chars()
                .mapToObj(c -> (char) c)
                .toList();

        MutableComponent component = Component.empty();
        int lastColour = colours.getFirst();

        for (int index = 0; index  < charList.size(); index++) {
            int color;
            Character character = charList.get(index);

            if (charList.size() == colours.size()) {
                color = colours.get(index);
            } else {
                float wordProgress = (float) (index + 1) / charList.size();

                float colourIndexUndone = (colours.size()-1) * wordProgress;
                int completeIndex = (int) Math.clamp(colourIndexUndone, 0, colours.size()-1);

                float g = (colourIndexUndone-completeIndex);
                color = ARGB.linearLerp(Math.clamp(g, 0, colours.size()-1), colours.get(completeIndex), colours.get(Math.clamp(completeIndex+1,0, colours.size()-1)));
            }

            component.append(Component.literal(character.toString()).withColor(color));
            if (lastColour != color) {
                lastColour=color;
            }

        }
        return component;
    }

}
