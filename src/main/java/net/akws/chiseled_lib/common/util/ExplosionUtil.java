package net.akws.chiseled_lib.common.util;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExplosionUtil {

    public static void createSphericalExplosion(World world, BlockPos center, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        BlockPos pos = center.add(x, y, z);
                        if (!world.isAir(pos) && world.getBlockState(pos).getBlock().getBlastResistance() < 8 ) {
                            world.setBlockState(pos, Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }
        }
    }

}
