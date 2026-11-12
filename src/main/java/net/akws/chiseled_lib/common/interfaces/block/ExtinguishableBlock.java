package net.akws.chiseled_lib.common.interfaces.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


public interface ExtinguishableBlock {
    default void onExtinguish(Entity source, BlockState state, BlockPos pos, LevelAccessor level) {
    }

    @SuppressWarnings("unused")
    default boolean isLit(BlockState state, BlockPos pos, LevelAccessor level) {
        return state.hasProperty(BlockStateProperties.LIT) && state.getValue(BlockStateProperties.LIT);
    }

    default boolean canBeExtinguishedByShovel(BlockState state, BlockPos pos, LevelAccessor level) {
        return false;
    }
}
