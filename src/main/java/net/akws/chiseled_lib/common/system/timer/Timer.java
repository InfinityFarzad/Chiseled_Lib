package net.akws.chiseled_lib.common.system.timer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.timer.TimerCallback;

public class Timer {

    private float timeLeft;
    private boolean removed;
    private boolean deathImmune;
    private boolean disconnectImmune;
    private boolean freazeImmune;
    private Identifier id;

    public static final Codec<Timer> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.FLOAT.fieldOf("timeLeft").forGetter(Timer::getTimeLeft),
            Codec.BOOL.fieldOf("deathImmune").forGetter(Timer::isImmuneToDeath),
            Codec.BOOL.fieldOf("disconnectImmune").forGetter(Timer::isImmuneToDisconnect),
            Codec.BOOL.fieldOf("freazeImmune").forGetter(Timer::isFreazeImmune),
            Identifier.CODEC.fieldOf("id").forGetter(Timer::getId)
    ).apply(i,Timer::new));

    public Timer(float timeLeft, Identifier id) {
        this.timeLeft = timeLeft;
        this.freazeImmune = false;
        this.removed = false;
        this.deathImmune = false;
        this.disconnectImmune = true;
        this.id = id;
    }

    public Timer(float timeLeft, boolean deathImmune, boolean disconnectImmune, boolean freazeImmune, Identifier id) {
        this.timeLeft = timeLeft;
        this.removed = false;
        this.freazeImmune = freazeImmune;
        this.deathImmune = deathImmune;
        this.disconnectImmune = disconnectImmune;
        this.id = id;
    }

    /* - methods related to events - */

    public void tick(PlayerEntity player) {
        if (!(timeLeft-- <= 0) && !isRemoved()) {
            timeLeft--;
        } else {
            this.onTimeout(player);
        }
    }

    public void onTimeout(PlayerEntity player) {
        TimerTimeoutEvent.EVENT.invoker().timeout(player,this.getId());
        this.remove();
    }

    /* - conditions - */

    public boolean isFreazeImmune() {
        return freazeImmune;
    }

    public boolean isImmuneToDeath() {
        return deathImmune;
    }

    public boolean isImmuneToDisconnect() {
        return disconnectImmune;
    }


    public boolean isRemoved() {
        return removed;
    }

    public boolean isFinished() {
        return !isRemoved() && ((getTimeLeft() - 1.0f) <= 0.0f);
    }

    /* - getter and setter - */

    public void remove() {
        this.removed = true;
    }

    public float getTimeLeft() {
        return timeLeft;
    }

    public Identifier getId() {
        return this.id;
    }

    public static Codec<Timer> getCodec() {
        return CODEC;
    }
}
