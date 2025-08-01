package net.akws.chiseled_lib.common.util.cameraEffects;

import net.akws.chiseled_lib.common.ChiseledLib;
import net.akws.chiseled_lib.common.mixin_interface.ScreenshakeDataInterface;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.logging.Logger;

public class ScreenshakeManager {

    public static final TrackedData<NbtCompound> SCREANSHAKE_COMPOUND = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);

    public static void createScreenShake(ScreenshakeDataHolder data, PlayerEntity player) {
        if (player.getWorld() instanceof ServerWorld world) {
            for (PlayerEntity Splayer : PlayerLookup.around(world,data.pos,data.radius)) {
                NbtCompound nbt = ((ScreenshakeDataInterface)Splayer).chiseledLib$getScreenshakeData();
                nbt.putFloat(ChiseledLib.MOD_ID + ".screenshake_intensity", data.intensity);
                nbt.putFloat(ChiseledLib.MOD_ID + ".screenshake_radius", data.radius);
                nbt.putFloat(ChiseledLib.MOD_ID + ".screenshake_ticks", data.shakeTicks);
                nbt.putDouble(ChiseledLib.MOD_ID + ".screenshake_x", data.pos.x);
                nbt.putDouble(ChiseledLib.MOD_ID + ".screenshake_y", data.pos.y);
                nbt.putDouble(ChiseledLib.MOD_ID + ".screenshake_z", data.pos.z);
            }
        }
    }

    public static ScreenshakeDataHolder getScreenshakeDataHolder(PlayerEntity player) {
        NbtCompound nbt = ((ScreenshakeDataInterface)player).chiseledLib$getScreenshakeData();
        if (nbt.getCompound(ChiseledLib.MOD_ID + ".screenshakeData").isPresent()) {
            float intensity = nbt.getFloat(ChiseledLib.MOD_ID + ".screenshake_intensity").get();
            float duration = nbt.getFloat(ChiseledLib.MOD_ID + ".screenshake_ticks").get();
            float radius = nbt.getFloat(ChiseledLib.MOD_ID + ".screenshake_radius").get();
            Vec3d pos = convertPosNbtData(nbt);

            return new ScreenshakeDataHolder(intensity,duration,pos,radius);
        } else {
            ChiseledLib.LOGGER.error("ba");
            return null;
        }
    }

    public static Vec3d convertPosNbtData(NbtCompound nbt) {
        return new Vec3d(
                nbt.getDouble(ChiseledLib.MOD_ID + ":screenshake_x").get(),
                nbt.getDouble(ChiseledLib.MOD_ID + ":screenshake_y").get(),
                nbt.getDouble(ChiseledLib.MOD_ID + ":screenshake_z").get());
    }

}
