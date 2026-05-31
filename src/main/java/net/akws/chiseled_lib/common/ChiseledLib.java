package net.akws.chiseled_lib.common;

import net.akws.chiseled_lib.client.camera.ScreenShakeHelper;
import net.akws.chiseled_lib.client.camera.Screenshake;
import net.akws.chiseled_lib.common.interfaces.mixin_interface.TimerInterface;
import net.akws.chiseled_lib.common.payload.EmitterParticlePayload;
import net.akws.chiseled_lib.common.payload.ExpandedParticlePayload;
import net.akws.chiseled_lib.common.payload.ScreenshakePayload;
import net.akws.chiseled_lib.common.registries.ChiseledLibBlocks;
import net.akws.chiseled_lib.common.registries.ChiseledLibComponents;
import net.akws.chiseled_lib.common.util.TimerUtil;
import net.akws.chiseled_lib.mixin.timer.PlayerTimerMixin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.entity.model.WitherEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChiseledLib implements ModInitializer {
    public static final String MOD_ID = "chiseled_lib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String key) {
        return Identifier.of(MOD_ID, key);
    }

    @Override
    public void onInitialize() {
        ChiseledLibComponents.init();
        ServerPlayerEvents.LEAVE.register(player -> {
            ((TimerInterface)player).chiseledLib$clearTimersOnDisconnect();
        });

        //runDebugCode();
        this.initNetworking();
    }

    public void initNetworking() {
        PayloadTypeRegistry.playS2C().register(EmitterParticlePayload.ID, EmitterParticlePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ScreenshakePayload.ID, ScreenshakePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ExpandedParticlePayload.ID, ExpandedParticlePayload.CODEC);
    }
    private static void runDebugCode() {
        ChiseledLibBlocks.init();
        UseItemCallback.EVENT.register((playerEntity, world, hand) -> {
            if (playerEntity.getMainHandStack().isOf(Items.BAMBOO)) {
                ScreenShakeHelper.createLocalScreenshake(20 * 50,playerEntity.getEntityPos(),20,0.5f,world);
            }
            if (playerEntity.getMainHandStack().isOf(Items.EMERALD)) {
                ScreenShakeHelper.createLocalScreenshake(50,playerEntity.getEntityPos(),20,5f,world);
            }
            return ActionResult.PASS;

        });
    }
}