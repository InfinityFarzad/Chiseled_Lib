package net.akws.chiseled_lib.client;

import net.akws.chiseled_lib.client.camera.screenflash.Flashes;
import net.akws.chiseled_lib.client.camera.screenshake.Screenshakes;
import net.akws.chiseled_lib.common.payload.ExpandedParticlePayload;
import net.akws.chiseled_lib.common.payload.EmitterParticlePayload;
import net.akws.chiseled_lib.common.payload.ScreenshakePayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

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
