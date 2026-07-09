package net.akws.chiseled_lib.mixin.client.camera;

import net.akws.chiseled_lib.client.camera.screenshake.Screenshakes;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.world.entity.Entity;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow
    protected abstract void move(float surge, float heave, float sway);

    @Shadow
    private @Nullable Entity entity;

    @Inject(method = "update", at = @At("RETURN"))
    private void chiseled$screenshake(DeltaTracker deltaTracker, CallbackInfo ci) {

        if (this.entity != null) {
            float tickProgress = deltaTracker.getGameTimeDeltaPartialTick(false);
            float yaw = this.entity.getYRot(tickProgress);
            float pitch = entity.getXRot(tickProgress);

            Screenshakes.get().applyScreenshake(entity, yaw, pitch, this::move);
        }
    }

}
