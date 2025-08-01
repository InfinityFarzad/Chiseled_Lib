package net.akws.chiseled_lib.mixin;

import net.akws.chiseled_lib.common.mixin_interface.ScreenshakeDataInterface;
import net.akws.chiseled_lib.common.util.cameraEffects.ScreenshakeDataHolder;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements ScreenshakeDataInterface {

    @Unique
    private ScreenshakeDataHolder screenshakeDataHolder;

    @Override
    public void chiseledLib$setScreenshakeInterface(ScreenshakeDataHolder screenshake) {
        this.screenshakeDataHolder = screenshake;
    }

    @Override
    public ScreenshakeDataHolder chiseledLib$getScreenshakeData() {
        return screenshakeDataHolder;
    }
}
