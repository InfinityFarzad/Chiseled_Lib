package net.akws.chiseled_lib.client;

import net.akws.chiseled_lib.client.camera.screenflash.Flashes;
import net.akws.chiseled_lib.client.camera.screenshake.Screenshakes;
import net.akws.chiseled_lib.common.payload.ExpandedParticlePayload;
import net.akws.chiseled_lib.common.payload.EmitterParticlePayload;
import net.akws.chiseled_lib.common.payload.ScreenshakePayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import org.joml.Matrix3x2f;

import static net.akws.chiseled_lib.common.ChiseledLib.id;

public class ChiseledLibClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> Flashes.instance().tick());
        ClientTickEvents.END_CLIENT_TICK.register((client) -> Screenshakes.get().tick());
        this.initNetworking();
    }

    public void initNetworking() {
        ClientPlayNetworking.registerGlobalReceiver(ScreenshakePayload.TYPE, new ScreenshakePayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(EmitterParticlePayload.TYPE, new EmitterParticlePayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(ExpandedParticlePayload.TYPE,new ExpandedParticlePayload.Receiver());
    }
}
