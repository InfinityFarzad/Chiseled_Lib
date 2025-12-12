package net.akws.chiseled_lib.common;

import net.akws.chiseled_lib.client.camera_effects.Screenshake;
import net.akws.chiseled_lib.common.component.ChiseledCCARegistries;
import net.akws.chiseled_lib.common.component.ChiseledLibComponents;
import net.akws.chiseled_lib.common.registries.ChiseledLibBlocks;
import net.akws.chiseled_lib.common.util.RegisteryUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ChiseledLib implements ModInitializer {
    public static final String MOD_ID = "chiseled_lib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Identifier id(String key) {
        return Identifier.of(MOD_ID, key);
    }
    public static List<Screenshake> screenshakes = new ArrayList<>();

    @Override
    public void onInitialize() {

        ChiseledLibComponents.init();
        ServerPlayConnectionEvents.DISCONNECT.register((serverPlayNetworkHandler, minecraftServer) -> {
            ChiseledCCARegistries.SCREENSHAKE_COMPONENT.get(serverPlayNetworkHandler.player).setScreenshakeDataHolder(Vec3d.ZERO, 0, 0, 0);
        });
        ChiseledLibBlocks.init();
        ServerTickEvents.END_WORLD_TICK.register((serverWorld) -> {
            Screenshake.tick();
        });

        //ChiseledLibBlocks.init();
        RegisteryUtil.init();
    }
}