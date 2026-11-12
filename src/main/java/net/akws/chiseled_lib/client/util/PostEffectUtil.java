package net.akws.chiseled_lib.client.util;

import net.akws.chiseled_lib.common.interfaces.mixin_interface.IShaderOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class PostEffectUtil {
    private static IShaderOverride getPostEffectHelper() {
        return (IShaderOverride)Minecraft.getInstance().gameRenderer;
    }

    public static void togglePostEffects(boolean toggle) {
        getPostEffectHelper().chiseled_lib$enable();
    }

    public static void setCurrentPostShader(Identifier id) {
        getPostEffectHelper().chiseled_lib$setShader(id);
    }

    public static Identifier getCurrentPostShader() {
        return getPostEffectHelper().chiseled_lib$getShader();
    }

    public static void clearPostEffects() {
        getPostEffectHelper().chiseled_lib$clear_shaders();
    }

}
