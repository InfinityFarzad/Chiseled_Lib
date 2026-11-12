package net.akws.chiseled_lib.common.interfaces.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public interface LightableBlock {

    default void light(Player player, BlockState state, BlockPos pos, LevelAccessor level) {
    }

    @SuppressWarnings("unused")
    default boolean canLight(BlockState state, BlockPos pos, LevelAccessor level) {
        return state.hasProperty(BlockStateProperties.LIT) && !state.getValue(BlockStateProperties.LIT);
    }

}
