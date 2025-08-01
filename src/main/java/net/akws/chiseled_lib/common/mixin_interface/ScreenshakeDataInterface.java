package net.akws.chiseled_lib.common.mixin_interface;

import net.akws.chiseled_lib.common.util.cameraEffects.ScreenshakeDataHolder;

public interface ScreenshakeDataInterface {
    void chiseledLib$setScreenshakeInterface(ScreenshakeDataHolder screenshake);
    ScreenshakeDataHolder chiseledLib$getScreenshakeData();
}
