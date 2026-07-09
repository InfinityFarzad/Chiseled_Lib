package net.akws.chiseled_lib.client;

import net.akws.chiseled_lib.client.camera.screenflash.ColourFlash;
import net.akws.chiseled_lib.client.camera.screenflash.Flashes;
import net.akws.chiseled_lib.client.camera.screenflash.ImageFlash;
import net.akws.chiseled_lib.client.camera.screenshake.Screenshakes;
import net.akws.chiseled_lib.common.ChiseledLib;
import net.akws.chiseled_lib.common.payload.ExpandedParticlePayload;
import net.akws.chiseled_lib.common.payload.EmitterParticlePayload;
import net.akws.chiseled_lib.common.payload.ScreenshakePayload;
import net.akws.chiseled_lib.common.system.timer.Timer;
import net.akws.chiseled_lib.common.util.TimerUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;

public class ChiseledLibClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> Flashes.instance().tick());
        ClientTickEvents.END_CLIENT_TICK.register((client) -> Screenshakes.get().tick());
        this.initNetworking();
    }

    public void initNetworking() {
        ClientPlayNetworking.registerGlobalReceiver(ScreenshakePayload.ID, new ScreenshakePayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(EmitterParticlePayload.ID, new EmitterParticlePayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(ExpandedParticlePayload.ID,new ExpandedParticlePayload.Receiver());
    }
}
