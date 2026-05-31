package net.akws.chiseled_lib.common.interfaces.mixin_interface;

import net.akws.chiseled_lib.common.system.timer.Timer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Unique;

public interface TimerInterface {

    void chiseledLib$addTimer(Timer timer, Identifier identifier);
    Timer chiseledLib$getTimer(Identifier identifier);
    void chiseledLib$clearTimersOnDisconnect();
    void chiseledLib$setTimer(Identifier identifier, float value);

}
