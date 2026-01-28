package net.akws.chiseled_lib.mixin.camera;

import net.akws.chiseled_lib.client.camera.Screenshakes;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow
    protected abstract void setRotation(float yaw, float pitch);

    @Shadow
    protected abstract void moveBy(float surge, float heave, float sway);

    @Inject(method = "update", at = @At("RETURN"))
    private void chiseled$screenshake(World area, Entity entity, boolean thirdPerson, boolean inverseView, float tickProgress, CallbackInfo ci) {
        float yaw = entity.getYaw(tickProgress);
        float pitch = entity.getPitch(tickProgress);

        Screenshakes.get().applyScreenshake(entity, yaw, pitch, this::moveBy);
    }

}
