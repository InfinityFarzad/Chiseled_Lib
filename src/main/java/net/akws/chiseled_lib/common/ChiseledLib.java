package net.akws.chiseled_lib.common;

import net.akws.chiseled_lib.common.interfaces.mixin_interface.TimerInterface;
import net.akws.chiseled_lib.common.payload.EmitterParticlePayload;
import net.akws.chiseled_lib.common.payload.ExpandedParticlePayload;
import net.akws.chiseled_lib.common.payload.ScreenshakePayload;
import net.akws.chiseled_lib.common.registries.ChiseledLibBlocks;
import net.akws.chiseled_lib.common.registries.ChiseledLibComponents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
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
    }
}