package net.akws.chiseled_lib.common;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.serialization.Codec;
import net.akws.chiseled_lib.client.camera.screenflash.ColourFlash;
import net.akws.chiseled_lib.client.camera.screenflash.Flash;
import net.akws.chiseled_lib.client.camera.screenflash.Flashes;
import net.akws.chiseled_lib.client.camera.screenshake.ScreenShakeHelper;
import net.akws.chiseled_lib.common.interfaces.mixin_interface.TimerInterface;
import net.akws.chiseled_lib.common.item.component.ItemHighlightComponent;
import net.akws.chiseled_lib.common.payload.EmitterParticlePayload;
import net.akws.chiseled_lib.common.payload.ExpandedParticlePayload;
import net.akws.chiseled_lib.common.payload.ScreenshakePayload;
import net.akws.chiseled_lib.common.registries.ChiseledLibComponents;
import net.akws.chiseled_lib.common.system.timer.Timer;
import net.akws.chiseled_lib.common.system.timer.TimerTimeoutEvent;
import net.akws.chiseled_lib.common.util.ExplosionUtil;
import net.akws.chiseled_lib.common.util.TimerUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import org.joml.Matrix3x2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Map;
import java.util.Objects;

public class ChiseledLib implements ModInitializer {
    public static final String MOD_ID = "chiseled_lib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final AttachmentType<Map<Identifier, Timer>> TIMER_ATTACHMENT = AttachmentRegistry.create(
            id("timer_attachment"),builder -> builder
                    .persistent(Codec.unboundedMap(Identifier.CODEC, Timer.getCodec()))
                    .copyOnDeath()
    );

    public static Identifier id(String key) {
        return Identifier.fromNamespaceAndPath(MOD_ID, key);
    }

    @Override
    public void onInitialize() {
        ChiseledLibComponents.init();
        ServerPlayerEvents.LEAVE.register(player -> {
            ((TimerInterface)player).chiseledLib$clearTimersOnDisconnect();
            ((TimerInterface)player).chiseledLib$syncTimerAttachment();
        });
        ServerPlayerEvents.JOIN.register(player -> {
            ((TimerInterface)player).chiseledLib$initTimerHash();
        });

        //runDebugCode();
        this.initNetworking();
    }

    public void initNetworking() {
        PayloadTypeRegistry.clientboundPlay().register(EmitterParticlePayload.TYPE, EmitterParticlePayload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ScreenshakePayload.TYPE, ScreenshakePayload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ExpandedParticlePayload.TYPE, ExpandedParticlePayload.CODEC);
    }

    private static void runDebugCode() {
        UseItemCallback.EVENT.register((playerEntity, world, hand) -> {
            if (playerEntity.getItemInHand(hand).is(Items.ACACIA_PLANKS)) {
                TimerUtil.addTimerToPlayer(playerEntity,new Timer(20*4,id("wa")));
                TimerUtil.addTimerToPlayer(playerEntity,new Timer(20*9,id("da")));
            }
            return InteractionResult.PASS;
        });
        TimerTimeoutEvent.EVENT.register((player, identifier) -> {
            if (Objects.equals(identifier, id("wa"))){
                ScreenShakeHelper.createGlobalScreenshake(player.level(),20*2,1.5f);
                Flashes.instance().addFlash(new ColourFlash(5,0x55FFFFFF));
                ExplosionUtil.createSphericalExplosion(player.level(), player.blockPosition(), 15, false);
            }
            if (Objects.equals(identifier, id("da"))){
                ScreenShakeHelper.createGlobalScreenshake(player.level(),20*2,5.5f);
            }
        });
        DefaultItemComponentEvents.MODIFY.register((context) -> context.modify(Items.ACACIA_PLANKS, builder -> builder.set(ChiseledLibComponents.ITEM_HIGHLIGHT, new ItemHighlightComponent(0xFFFFFF,true))));

    }
}