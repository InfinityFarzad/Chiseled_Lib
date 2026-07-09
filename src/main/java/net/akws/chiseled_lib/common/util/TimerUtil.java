package net.akws.chiseled_lib.common.util;

import net.akws.chiseled_lib.common.interfaces.mixin_interface.TimerInterface;
import net.akws.chiseled_lib.common.system.timer.Timer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

public class TimerUtil {
    public static void addTimerToPlayer(Player player, Timer timer) {
        ((TimerInterface)player).chiseledLib$addTimer(timer);
    }
    public static void addTimerToPlayer(Player player, Identifier id, float duration) {
        ((TimerInterface)player).chiseledLib$addTimer(new Timer(duration,id));
    }

    public static float getTimerTimeLeft(Player player,Identifier timerId) {
        return ((TimerInterface)player).chiseledLib$getTimer(timerId).getTimeLeft();
    }

    public static boolean isTimerFinished(Player player, Identifier timerId) {
        return ((TimerInterface)player).chiseledLib$getTimer(timerId).isFinished() && ((TimerInterface)player).chiseledLib$containsTimer(timerId);
    }
}
