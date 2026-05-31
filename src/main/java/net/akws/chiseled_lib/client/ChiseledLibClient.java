package net.akws.chiseled_lib.client;

import net.akws.chiseled_lib.client.camera.Screenshakes;
import net.akws.chiseled_lib.common.payload.ExpandedParticlePayload;
import net.akws.chiseled_lib.common.payload.EmitterParticlePayload;
import net.akws.chiseled_lib.common.payload.ScreenshakePayload;
import net.akws.chiseled_lib.common.registries.ChiseledLibBlocks;
import net.akws.chiseled_lib.common.util.TimerUtil;
import net.akws.chiseled_lib.mixin.timer.PlayerTimerMixin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

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


        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            if (MinecraftClient.getInstance() != null ) {
                MinecraftClient client = MinecraftClient.getInstance();
                PlayerEntity player = client.player;

                drawContext.drawText(MinecraftClient.getInstance().textRenderer,
                        String.valueOf(
                                TimerUtil.getTimerTimeLeft(player, Identifier.of("ccc","d"))
                        ),0,0,16777215,true
                );

            }

        });

    }

    public void initNetworking() {
        ClientPlayNetworking.registerGlobalReceiver(ScreenshakePayload.ID, new ScreenshakePayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(EmitterParticlePayload.ID, new EmitterParticlePayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(ExpandedParticlePayload.ID,new ExpandedParticlePayload.Receiver());
    }
}
