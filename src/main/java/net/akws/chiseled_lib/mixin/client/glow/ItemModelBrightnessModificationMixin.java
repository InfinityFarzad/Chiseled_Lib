package net.akws.chiseled_lib.mixin.client.glow;

import net.akws.chiseled_lib.common.interfaces.mixin_interface.ItemStackRenderStateInterface;
import net.akws.chiseled_lib.common.registries.ChiseledLibComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// the only reason for the rather overcomplicated name, is so IntelliJ does not give me spelling mistake errors.

@Mixin(CuboidItemModelWrapper.class)
public class ItemModelBrightnessModificationMixin {
    @Inject(method = "update", at = @At("HEAD"))
    private void chiseled_lib$applyEmissive(ItemStackRenderState output, ItemStack item, ItemModelResolver resolver, ItemDisplayContext displayContext, ClientLevel level, ItemOwner owner, int seed, CallbackInfo ci) {
        ((ItemStackRenderStateInterface) output).chiseled_lib$setEmissive(item.getOrDefault(ChiseledLibComponents.EMISSIVE_COMPONENT, false));
    }
}
