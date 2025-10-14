package net.akws.chiseled_lib.common.util;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.World;

import java.util.Random;

public class ExplosionUtil {

    public static void createSphericalExplosion(World world, BlockPos center, int radius, boolean doBlockDrops) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        BlockPos pos = center.add(x, y, z);
                        if (world.getRandom().nextInt(3) == 0 && !world.isAir(pos.down())) {
                            world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                        }
                        if (!world.isAir(pos) && world.getBlockState(pos).getBlock().getBlastResistance() < 8) {
                            if (!doBlockDrops) {
                                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                            } else {
                                world.breakBlock(pos, true);
                            }
                        }
                    }
                }
            }
        }
    }

}
