package net.akws.chiseled_lib.common.util;

import net.akws.chiseled_lib.common.ChiseledLib;
import net.akws.chiseled_lib.common.interfaces.mixin_interface.TimerInterface;
import net.akws.chiseled_lib.common.system.timer.Timer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

public class TimerUtil {
    public static void addTimerToPlayer(Player player, Timer timer) {
        ((TimerInterface)player).chiseledLib$addTimer(timer);
    }
    public static void addTimerToPlayer(Player player, Identifier id, float duration) {
        var t = player.getAttached(ChiseledLib.TIMER_ATTACHMENT);
        if (t != null) {
            t.put(id, new Timer(duration, id));
            player.setAttached(ChiseledLib.TIMER_ATTACHMENT, t);
        }
    }

    public static float getTimerTimeLeft(Player player,Identifier timerId) {
        return getTimer(player, timerId).getTimeLeft();
    }

    public static boolean isTimerFinished(Player player, Identifier timerId) {
        return getTimer(player, timerId).isFinished() && ((TimerInterface)player).chiseledLib$containsTimer(timerId);
    }

    public static Timer getTimer(Player player, Identifier id) {
        return player.getAttachedOrCreate(ChiseledLib.TIMER_ATTACHMENT).get(id);
    }
}
