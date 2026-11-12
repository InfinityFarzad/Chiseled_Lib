package net.akws.chiseled_lib.common.interfaces.mixin_interface;

import net.minecraft.resources.Identifier;

public interface IShaderOverride {
    void chiseled_lib$setShader(Identifier id);

    Identifier chiseled_lib$getShader();

    void chiseled_lib$clear_shaders();

    void chiseled_lib$enable();
}
