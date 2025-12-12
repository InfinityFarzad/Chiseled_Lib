package net.akws.chiseled_lib.client;

import net.akws.chiseled_lib.client.util.RenderUtil;
import net.akws.chiseled_lib.common.registries.ChiseledLibBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class ChiseledLibClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ChiseledLibBlocks.INFINITYFARZAD_PLUSHIE,RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ChiseledLibBlocks.MONGO_CAT_PLUSHIE,RenderLayer.getCutoutMipped());
    }
}
