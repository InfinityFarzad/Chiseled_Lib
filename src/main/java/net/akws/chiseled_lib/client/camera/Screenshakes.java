package net.akws.chiseled_lib.client.camera;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Environment(EnvType.CLIENT)
public class Screenshakes implements Iterable<Screenshake> {
    private static final Screenshakes INSTANCE = new Screenshakes();
    private final List<Screenshake> screenshakes = new ArrayList<>();

    public static Screenshakes get() {
        return INSTANCE;
    }

    public void create(int duration, Vec3d pos, float radius, float intensity) {
        this.create(new Screenshake(duration, pos, radius, intensity));
    }

    public void create(Screenshake screenshake) {
        this.screenshakes.add(screenshake);
    }

    public void tick() {
        List<Screenshake> removed = new ArrayList<>();

        for (Screenshake screenshake : this) {
            screenshake.tick();
            if (screenshake.isRemoved()) {
                removed.add(screenshake);
            }
        }

        this.screenshakes.removeAll(removed);
    }

    public void applyScreenshake(Entity camEntity, float yaw, float pitch, ScreenshakeConsumer consumer) {
        float heave = 0F;
        float sway = 0F;

        for (Screenshake screenshake : this) {
            float distance = (float) screenshake.pos.distanceTo(camEntity.getEyePos());

            if (distance < screenshake.radius) {
                float disMultiplier = 1 - (distance / screenshake.radius);
                float delta = screenshake.getDelta() * disMultiplier;

                heave += (screenshake.getRandom().nextFloat() - 0.5F) * screenshake.intensity * delta;
                sway += (screenshake.getRandom().nextFloat() - 0.5F) * screenshake.intensity * delta;
            }
        }

        consumer.applyScreenshake(0F, heave, sway);
    }

    @Override
    public @NotNull Iterator<Screenshake> iterator() {
        return this.screenshakes.iterator();
    }

    public interface ScreenshakeConsumer {
        void applyScreenshake(float surge, float heave, float sway);
    }
}
