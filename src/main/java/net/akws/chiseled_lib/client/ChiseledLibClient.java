package net.akws.chiseled_lib.client;

import net.akws.chiseled_lib.client.camera.Screenshakes;
import net.akws.chiseled_lib.common.payload.EmitterParticlePayload;
import net.akws.chiseled_lib.common.payload.ScreenshakePayload;
import net.akws.chiseled_lib.common.registries.ChiseledLibBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;

public class ChiseledLibClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT,
                ChiseledLibBlocks.INFINITYFARZAD_PLUSHIE,
                ChiseledLibBlocks.MONGO_CAT_PLUSHIE,
                ChiseledLibBlocks.JUKO_LUL_PLUSHIE
        );

        ClientTickEvents.END_CLIENT_TICK.register((client) -> Screenshakes.get().tick());
        this.initNetworking();
    }

    public void initNetworking() {
        ClientPlayNetworking.registerGlobalReceiver(ScreenshakePayload.ID, new ScreenshakePayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(EmitterParticlePayload.ID, new EmitterParticlePayload.Receiver());
    }
}
