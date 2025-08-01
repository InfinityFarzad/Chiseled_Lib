package net.akws.chiseled_lib.mixin;

import net.akws.chiseled_lib.common.ChiseledLib;
import net.akws.chiseled_lib.common.mixin_interface.ScreenshakeDataInterface;
import net.akws.chiseled_lib.common.util.cameraEffects.ScreenshakeDataHolder;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements ScreenshakeDataInterface {

    NbtCompound screenshake;

    @Override
    public NbtCompound chiseledLib$getScreenshakeData() {
        if (this.screenshake == null) {
            this.screenshake = new NbtCompound();
        }

        return this.screenshake;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfo ci) {
        if(screenshake != null) {
            nbt.put(ChiseledLib.MOD_ID + ".screenshakeData", screenshake);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains(ChiseledLib.MOD_ID + ".screenshakeData") && nbt.getCompound(ChiseledLib.MOD_ID + ".screenshakeData").isPresent()) {
            screenshake = nbt.getCompound(ChiseledLib.MOD_ID + ".screenshakeData").get();
        }
    }
}
