package net.akws.chiseled_lib.mixin.timer;

import com.mojang.serialization.Codec;
import net.akws.chiseled_lib.common.interfaces.mixin_interface.TimerInterface;
import net.akws.chiseled_lib.common.system.timer.Timer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(PlayerEntity.class)
public class PlayerTimerMixin implements TimerInterface {
    @Unique
    private HashMap<Identifier, Timer> storedTimers = new HashMap<>();


    public void chiseledLib$addTimer(Timer timer, Identifier identifier) {
        storedTimers.put(identifier,timer);
    }

    public Timer chiseledLib$getTimer(Identifier identifier) {
        return storedTimers.get(identifier);
    }

    public void chiseledLib$clearTimersOnDisconnect() {
        for (Timer timer : storedTimers.values()) {
            if (timer.isImmuneToDisconnect()) {
                timer.remove();
            }
        }
    }

    public void chiseledLib$setTimer(Identifier identifier, float value) {
        if (storedTimers.containsKey(identifier)) {
            storedTimers.remove(identifier);
        }
        storedTimers.put(identifier,new Timer(value));
    }

    /* -* {  what am i doing again  } *- */

    @Inject(method = "tick",at =@At("HEAD"))
    private void chiseledLib$tickTimers(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object)this;
        World world = player.getEntityWorld();

        for (Timer timer : storedTimers.values()) {
            if (timer.isRemoved()) {
                storedTimers.remove(timer);
            } else {
                timer.tick();
            }
        }

    }

    @Inject(method = "onDeath",at =@At("HEAD"))
    private void chiseledLib$clearTimersOnDeath(DamageSource damageSource, CallbackInfo ci) {
        for (Timer timer : storedTimers.values()) {
            if (timer.isImmuneToDeath()) {
                timer.remove();
            }
        }
    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    private void chiseledLib$writeCustomDataTimer(WriteView view, CallbackInfo ci) {
        view.put("storedTimers", Codec.unboundedMap(Identifier.CODEC, Timer.CODEC),storedTimers);
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void chiseledLib$readCustomDataTimer(ReadView view, CallbackInfo ci) {
        view.read("storedTimers",Codec.unboundedMap(Identifier.CODEC, Timer.CODEC));
    }

}
