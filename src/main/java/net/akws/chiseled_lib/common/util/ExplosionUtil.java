package net.akws.chiseled_lib.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class ExplosionUtil {
    
    public static void createSphericalExplosion(Level level, BlockPos center, int radius, boolean doBlockDrops) {

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        BlockPos pos = center.offset(x, y, z);
                        if (level.getRandom().nextInt(3) == 0 && !level.isEmptyBlock(pos.below())) {
                            level.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
                        }
                        if (!level.isEmptyBlock(pos) && level.getBlockState(pos).getBlock().getExplosionResistance() < 8) {
                            if (!doBlockDrops) {
                                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                            } else {
                                level.destroyBlock(pos, true);
                            }
                        }
                    }
                }
            }
        }
    }

}
