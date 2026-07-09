package net.akws.chiseled_lib.common;

import com.mojang.serialization.Codec;
import net.akws.chiseled_lib.client.camera.screenshake.ScreenShakeHelper;
import net.akws.chiseled_lib.common.interfaces.mixin_interface.TimerInterface;
import net.akws.chiseled_lib.common.payload.EmitterParticlePayload;
import net.akws.chiseled_lib.common.payload.ExpandedParticlePayload;
import net.akws.chiseled_lib.common.payload.ScreenshakePayload;
import net.akws.chiseled_lib.common.registries.ChiseledLibComponents;
import net.akws.chiseled_lib.common.system.timer.Timer;
import net.akws.chiseled_lib.common.system.timer.TimerTimeoutEvent;
import net.akws.chiseled_lib.common.util.TimerUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        return Identifier.of(MOD_ID, key);
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
        PayloadTypeRegistry.playS2C().register(EmitterParticlePayload.ID, EmitterParticlePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ScreenshakePayload.ID, ScreenshakePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ExpandedParticlePayload.ID, ExpandedParticlePayload.CODEC);
    }

    private static void runDebugCode() {
        UseItemCallback.EVENT.register((playerEntity, world, hand) -> {
            if (playerEntity.getStackInHand(hand).isOf(Items.ACACIA_PLANKS)) {
                TimerUtil.addTimerToPlayer(playerEntity,new Timer(20*4,id("wa")));
                TimerUtil.addTimerToPlayer(playerEntity,new Timer(20*9,id("da")));
            }
            return ActionResult.PASS;
        });
        TimerTimeoutEvent.EVENT.register((player, identifier) -> {
            if (Objects.equals(identifier, id("wa"))){
                ScreenShakeHelper.createGlobalScreenshake(player.getEntityWorld(),20*2,1.5f);
            }
            if (Objects.equals(identifier, id("da"))){
                ScreenShakeHelper.createGlobalScreenshake(player.getEntityWorld(),20*2,5.5f);
            }
        });
    }
}