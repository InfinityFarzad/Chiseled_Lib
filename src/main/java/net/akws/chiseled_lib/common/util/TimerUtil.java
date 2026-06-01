package net.akws.chiseled_lib.common.util;

import net.akws.chiseled_lib.common.ChiseledLib;
import net.akws.chiseled_lib.common.interfaces.mixin_interface.TimerInterface;
import net.akws.chiseled_lib.common.system.timer.Timer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class TimerUtil {
    public static void addTimerToPlayer(PlayerEntity player, Identifier timerId, Timer timer) {
        ((TimerInterface)player).chiseledLib$addTimer(timer,timerId);
    }
    public static float getTimerTimeLeft(PlayerEntity player,Identifier timerId) {
        return ((TimerInterface)player).chiseledLib$getTimer(timerId).getTimeLeft();
    }
    public static boolean isTimerFinished(PlayerEntity player, Identifier timerId) {
        return ((TimerInterface)player).chiseledLib$getTimer(timerId).isFinished() && ((TimerInterface)player).chiseledLib$containsTimer(timerId);
    }


}
