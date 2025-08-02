package net.akws.chiseled_lib.common.util.cameraEffects;

import net.akws.chiseled_lib.common.component.ChiseledCCARegistries;
import net.akws.chiseled_lib.common.component.ScreenshakeDataComponent;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class ScreenshakeManager {

    public static void createScreenShake(ScreenshakeDataHolder data, PlayerEntity player) {
        if (player.getWorld() instanceof ServerWorld world) {
            for (PlayerEntity Splayer : PlayerLookup.around(world,data.pos,data.radius)) {
                ScreenshakeDataComponent.getData(Splayer).setScreenshakeDataHolder(data.pos, data.radius, data.intensity, data.shakeTicks);
            }
        }
    }

    private static ServerWorld getServerworld(World world) {
        if (world instanceof ServerWorld serverWorld) {
            return serverWorld;
        } else {
            return null;
        }
    }

}
