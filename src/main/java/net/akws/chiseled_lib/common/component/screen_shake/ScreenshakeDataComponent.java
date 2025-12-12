package net.akws.chiseled_lib.common.component.screen_shake;

import net.akws.chiseled_lib.client.camera_effects.Screenshake;
import net.akws.chiseled_lib.common.ChiseledLib;
import net.akws.chiseled_lib.common.component.ChiseledCCARegistries;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class ScreenshakeDataComponent implements AutoSyncedComponent, CommonTickingComponent {

    public PlayerEntity player;
    private float intensity;
    private float radius;
    private float screenshakeTick;
    private double x;
    private double y;
    private double z;

    public ScreenshakeDataComponent(PlayerEntity player) {
        this.player = player;
    }

    public static ScreenshakeDataComponent getData(PlayerEntity player) {
        return ChiseledCCARegistries.SCREENSHAKE_COMPONENT.get(player);
    }

    public void setScreenshakeDataHolder(Vec3d pos, float radius, float intensity, float screenshakeTick) {
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
        this.radius = radius;
        this.intensity = intensity;
        this.screenshakeTick = screenshakeTick;
        ChiseledCCARegistries.SCREENSHAKE_COMPONENT.sync(this.player);
    }

    public Screenshake getScreenshakeDataHolder() {
        return new Screenshake(this.intensity, this.screenshakeTick, new Vec3d(x, y, z), this.radius);
    }


    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {

        if (nbtCompound.contains("screenshake_intensity")) {
            this.intensity = nbtCompound.getFloat("screenshake_intensity", 0);
        } else {
            this.intensity = 0;
        }

        if (nbtCompound.contains("screenshake_tick")) {
            this.screenshakeTick = nbtCompound.getFloat("screenshake_tick", 0);
        } else {
            this.screenshakeTick = 0;
        }

        if (nbtCompound.contains("screenshake_radius")) {
            this.radius = nbtCompound.getFloat("screenshake_radius", 0);
        } else {
            this.screenshakeTick = 0;
        }

        if (nbtCompound.contains("screenshake_x")) {
            this.x = nbtCompound.getFloat("screenshake_x", 0);
        } else {
            this.x = 0;
        }

        if (nbtCompound.contains("screenshake_y")) {
            this.y = nbtCompound.getFloat("screenshake_y", 0);
        } else {
            this.y = 0;
        }

        if (nbtCompound.contains("screenshake_z")) {
            this.z = nbtCompound.getFloat("screenshake_z", 0);
        } else {
            this.z = 0;
        }

    }

    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return player == this.player;
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putFloat("screenshake_intensity", intensity);
        nbtCompound.putFloat("screenshake_tick", screenshakeTick);
        nbtCompound.putFloat("screenshake_radius", radius);

        nbtCompound.putDouble("screenshake_x", x);
        nbtCompound.putDouble("screenshake_y", y);
        nbtCompound.putDouble("screenshake_z", z);

    }

    @Override
    public void tick() {
        ScreenshakeDataComponent component = ChiseledCCARegistries.SCREENSHAKE_COMPONENT.get(this.player);
        if (ChiseledLib.screenshakes != null) {
            if (!ChiseledLib.screenshakes.isEmpty() && !(this.player.getPos().distanceTo(new Vec3d(component.x,component.y,component.z)) > component.radius)) {
                for (Screenshake screenshake : ChiseledLib.screenshakes) {
                    if (this.player.getPos().distanceTo(screenshake.pos) <= screenshake.radius) {
                        if (screenshake.intensity > component.intensity) {
                            component.setScreenshakeDataHolder(screenshake.pos, screenshake.radius, screenshake.intensity, screenshake.shakeTicks);
                            ChiseledCCARegistries.SCREENSHAKE_COMPONENT.sync(this.player);
                        }
                    }
                    screenshake.shakeTicks--;
                }
            } else {
                component.setScreenshakeDataHolder(Vec3d.ZERO,500000,0,0);
                ChiseledCCARegistries.SCREENSHAKE_COMPONENT.sync(this.player);
            }
        }
        ChiseledCCARegistries.SCREENSHAKE_COMPONENT.sync(this.player);
    }
}
