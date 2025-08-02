package net.akws.chiseled_lib.common.component;

import net.akws.chiseled_lib.common.util.cameraEffects.ScreenshakeDataHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.Vec3d;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class ScreenshakeDataComponent implements AutoSyncedComponent {

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
    }

    public ScreenshakeDataHolder getScreenshakeDataHolder() {
        return new ScreenshakeDataHolder(this.intensity, this.screenshakeTick, new Vec3d(x,y,z), this.radius);
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
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {

        nbtCompound.putFloat("screenshake_intensity", intensity);
        nbtCompound.putFloat("screenshake_tick", screenshakeTick);
        nbtCompound.putFloat("screenshake_radius", radius);

        nbtCompound.putDouble("screenshake_x", x);
        nbtCompound.putDouble("screenshake_y", y);
        nbtCompound.putDouble("screenshake_z", z);


    }
}
