package net.akws.chiseled_lib.common.util;

import net.akws.chiseled_lib.common.interfaces.mixin_interface.TimerInterface;
import net.akws.chiseled_lib.common.system.timer.Timer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class TimerUtil {
    public static void addTimerToPlayer(PlayerEntity player, Timer timer) {
        ((TimerInterface)player).chiseledLib$addTimer(timer);
    }
    public static void addTimerToPlayer(PlayerEntity player, Identifier id, float duration) {
        ((TimerInterface)player).chiseledLib$addTimer(new Timer(duration,id));
    }

    public static float getTimerTimeLeft(PlayerEntity player,Identifier timerId) {
        return ((TimerInterface)player).chiseledLib$getTimer(timerId).getTimeLeft();
    }
    public static boolean isTimerFinished(PlayerEntity player, Identifier timerId) {
        return ((TimerInterface)player).chiseledLib$getTimer(timerId).isFinished() && ((TimerInterface)player).chiseledLib$containsTimer(timerId);
    }

}
