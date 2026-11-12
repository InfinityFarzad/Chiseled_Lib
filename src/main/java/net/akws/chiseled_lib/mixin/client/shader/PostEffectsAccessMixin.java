package net.akws.chiseled_lib.mixin.client.shader;

import net.akws.chiseled_lib.common.interfaces.mixin_interface.IShaderOverride;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GameRenderer.class)
public abstract class PostEffectsAccessMixin implements IShaderOverride {

    @Shadow
    protected abstract void setPostEffect(Identifier id);

    @Shadow
    public abstract @Nullable Identifier currentPostEffect();

    @Shadow
    public abstract void clearPostEffect();

    @Shadow
    public abstract void togglePostEffect();

    @Override
    public void chiseled_lib$enable() {
        this.togglePostEffect();
    }

    @Override
    public void chiseled_lib$setShader(Identifier id) {
        setPostEffect(id);
    }

    @Override
    public void chiseled_lib$clear_shaders() {
        clearPostEffect();
    }

    @Override
    public Identifier chiseled_lib$getShader() {
        return currentPostEffect();
    }
}
