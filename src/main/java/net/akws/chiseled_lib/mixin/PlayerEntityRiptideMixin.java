package net.akws.chiseled_lib.mixin;

import net.akws.chiseled_lib.common.mixin_interface.RiptideMixinInterface;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityRiptideMixin implements RiptideMixinInterface {

    ItemStack RStack;

    @Override
    public void chiseled_lib$setRiptideStack(ItemStack stack) {
        RStack = stack;
    }

    @Override
    public ItemStack chiseled_lib$getRiptideStack() {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (this.RStack != null) {
            return this.RStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

}
