package net.akws.chiseled_lib.common.util;

import net.akws.chiseled_lib.common.interfaces.mixin_interface.TimerInterface;
import net.akws.chiseled_lib.common.system.timer.Timer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

public class TimerUtil {
    public static void addTimerToPlayer(Player player, Timer timer) {
        ((TimerInterface)player).chiseledLib$addTimer(timer);
    }
    public static void addTimerToPlayer(Player player, Identifier id, int duration) {
        addTimerToPlayer(player, new Timer(id, duration));
    }

    public static float getTimerTimeLeft(Player player, Identifier id) {
        if (((TimerInterface)player).chiseledLib$containsTimer(id)) {
            return ((TimerInterface)player).chiseledLib$getTimer(id).getDuration();
        }
        return 0;
    }

    public static boolean isTimerFinished(Player player, Identifier timerId) {
        return ((TimerInterface)player).chiseledLib$containsTimer(timerId) && ((TimerInterface)player).chiseledLib$getTimer(timerId).isFinished();
    }
}
