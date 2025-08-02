package net.akws.chiseled_lib.mixin;

import net.akws.chiseled_lib.common.ChiseledLib;
import net.akws.chiseled_lib.common.component.ScreenshakeDataComponent;
import net.akws.chiseled_lib.common.util.cameraEffects.ScreenshakeDataHolder;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow
    private Vec3d pos;
    @Shadow
    private float lastTickProgress;

    @Shadow
    protected abstract void setRotation(float yaw, float pitch);

    @Shadow
    public abstract float getYaw();

    @Shadow
    public abstract float getPitch();

    @Inject(method = "update", at = @At("RETURN"))
    private void chiseled$updateScreenshakeTick(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickProgress, CallbackInfo ci) {
        Camera cam = (Camera) (Object) this;
        if (focusedEntity instanceof PlayerEntity player) {
            ScreenshakeDataHolder data = ScreenshakeDataComponent.getData(player).getScreenshakeDataHolder();
            if (data != null && data.shakeTicks != 0) {
                if (player.getPos().distanceTo(data.pos) <= data.radius && data.shakeTicks > 0) {
                    Random rand = new Random();
                    setRotation(getYaw() + rand.nextFloat(0.5f * data.intensity), getPitch() + rand.nextFloat(0.5f * data.intensity));
                    ScreenshakeDataComponent.getData(player).setScreenshakeDataHolder(data.pos,data.radius,data.intensity, data.shakeTicks - this.lastTickProgress);
                } else {
                    if (data.shakeTicks > 0) {
                        ScreenshakeDataComponent.getData(player).setScreenshakeDataHolder(data.pos,data.radius,data.intensity, data.shakeTicks - this.lastTickProgress);
                    }
                }
            }
        }
    }

}
