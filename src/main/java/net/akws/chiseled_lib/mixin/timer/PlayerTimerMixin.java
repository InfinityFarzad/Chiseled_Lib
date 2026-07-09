package net.akws.chiseled_lib.mixin.timer;

import net.akws.chiseled_lib.common.ChiseledLib;
import net.akws.chiseled_lib.common.interfaces.mixin_interface.TimerInterface;
import net.akws.chiseled_lib.common.system.timer.Timer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mixin(Player.class)
public abstract class PlayerTimerMixin implements TimerInterface {
    @Unique
    private HashMap<Identifier, Timer> storedTimers = new HashMap<>();

    public void chiseledLib$addTimer(Timer timer) {
        if (storedTimers.containsKey(timer.getId())) {
            storedTimers.remove(timer.getId());
        }
        storedTimers.put(timer.getId(),timer);
    }

/*    public Timer chiseledLib$getTimer(Identifier identifier) {
        return storedTimers.get(identifier) != null ? storedTimers.get(identifier) : new Timer(0.0f, identifier);
    }*/

    public void chiseledLib$clearTimersOnDisconnect() {
        for (Timer timer : storedTimers.values()) {
            if (timer.isImmuneToDisconnect()) {
                timer.remove();
            }
        }
    }

    @Override
    public void chiseledLib$syncTimerAttachment() {
        Player player = (Player) (Object)this;
        player.setAttached(ChiseledLib.TIMER_ATTACHMENT,this.storedTimers);
    }

    @Override
    public void chiseledLib$initTimerHash() {
        Player player = (Player) (Object) this;
        this.storedTimers = new HashMap<Identifier, Timer>(player.getAttachedOrElse(ChiseledLib.TIMER_ATTACHMENT, new HashMap<Identifier, Timer>()));
    }

    @Unique
    public boolean chiseledLib$containsTimer(Identifier identifier) {
        return storedTimers.containsKey(identifier);
    }

    /* -* {  what am i doing again  } *- */

    @Inject(method = "tick",at =@At("HEAD"))
    private void chiseledLib$tickTimers(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        List<Identifier> removetimers = new ArrayList<>();
        for (Identifier id : storedTimers.keySet()) {
            Timer timer = storedTimers.get(id);
            if (timer.isRemoved()) {
                removetimers.add(id);

            } else {
                timer.tick(player);
            }
        }
        removetimers.forEach((id) ->
            storedTimers.remove(id)
        );
    }

    @Inject(method = "die",at =@At("HEAD"))
    private void chiseledLib$clearTimersOnDeath(DamageSource damageSource, CallbackInfo ci) {
        for (Timer timer : storedTimers.values()) {
            if (timer.isImmuneToDeath()) {
                timer.remove();
            }
        }
    }

}
