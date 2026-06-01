package net.akws.chiseled_lib.mixin.timer;

import com.mojang.serialization.Codec;
import net.akws.chiseled_lib.common.ChiseledLib;
import net.akws.chiseled_lib.common.interfaces.mixin_interface.TimerInterface;
import net.akws.chiseled_lib.common.system.timer.Timer;
import net.akws.chiseled_lib.common.util.TimerUtil;
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
        if (storedTimers.containsKey(identifier)) {
            storedTimers.remove(identifier);
        }
        storedTimers.put(identifier,timer);
    }

    public Timer chiseledLib$getTimer(Identifier identifier) {
        return storedTimers.get(identifier) != null ? storedTimers.get(identifier) : new Timer(0.0f);
    }

    public void chiseledLib$clearTimersOnDisconnect() {
        for (Timer timer : storedTimers.values()) {
            if (timer.isImmuneToDisconnect()) {
                timer.remove();
            }
        }
    }

    @Override
    public void chiseledLib$getTimers() {
        for (Identifier ide : storedTimers.keySet()) {
            System.out.println(ide.getNamespace() + ide.getPath() + storedTimers.get(ide));
        }
    }

    @Override
    public boolean chiseledLib$containsTimer(Identifier identifier) {
        return storedTimers.containsKey(identifier);
    }

    /* -* {  what am i doing again  } *- */

    @Inject(method = "tick",at =@At("HEAD"))
    private void chiseledLib$tickTimers(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        for (Identifier id : storedTimers.keySet()) {
            Timer timer = storedTimers.get(id);
            if (timer.isRemoved()) {
                storedTimers.remove(id);
            } else {
                timer.tick(player);
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
