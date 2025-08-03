package net.akws.chiseled_lib.client.camera_effects;

import net.akws.chiseled_lib.common.component.screen_shake.ScreenshakeDataComponent;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class ScreenshakeManager {

    public static void createScreenShake(ScreenshakeDataHolder data, PlayerEntity player) {
        if (player.getWorld() instanceof ServerWorld world) {
            for (PlayerEntity Splayer : PlayerLookup.world(world)) {
                ScreenshakeDataComponent.getData(Splayer).setScreenshakeDataHolder(data.pos, data.radius, data.intensity, data.shakeTicks);
            }
        }
    }

}
