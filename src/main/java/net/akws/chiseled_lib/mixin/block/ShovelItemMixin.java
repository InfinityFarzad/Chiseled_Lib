package net.akws.chiseled_lib.mixin.block;

import net.akws.chiseled_lib.common.interfaces.block.ExtinguishableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;"), cancellable = true)
    private void chiseled_lib$extinguishFire(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (context.getClickedFace() != Direction.DOWN) {
            Level level = context.getLevel();
            BlockPos pos = context.getClickedPos();
            BlockState state = level.getBlockState(pos);
            Player player = context.getPlayer();

            if (state.getBlock() instanceof ExtinguishableBlock block && block.isLit(state, pos, level) && block.canBeExtinguishedByShovel(state, pos, level)) {
                BlockState newState = state.setValue(BlockStateProperties.LIT, false);
                if (!level.isClientSide()) {
                    level.setBlock(pos, newState, 11);
                    level.levelEvent(null, 1009, pos, 0);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));

                    if (player != null) {
                        context.getItemInHand().hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
                    }
                }
                block.onExtinguish(player, state, pos, level);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}
