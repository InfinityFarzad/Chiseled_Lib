package net.akws.chiseled_lib.common.system.timer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;

public class Timer {

    private int duration;
    private boolean removed;
    private final boolean deathImmune;
    private final boolean disconnectImmune;
    private final boolean freezeImmune;
    private final Identifier id;

    public static final Codec<Timer> CODEC = RecordCodecBuilder.create(i -> i.group(
            Identifier.CODEC.fieldOf("id").forGetter(Timer::getId),
            Codec.INT.fieldOf("duration").forGetter(Timer::getDuration),
            Codec.BOOL.fieldOf("deathImmune").forGetter(Timer::isImmuneToDeath),
            Codec.BOOL.fieldOf("disconnectImmune").forGetter(Timer::isImmuneToDisconnect),
            Codec.BOOL.fieldOf("freezeImmune").forGetter(Timer::isFreezeImmune)
    ).apply(i,Timer::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, Timer> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NonNull Timer decode(RegistryFriendlyByteBuf input) {
            return input.readLenientJsonWithCodec(CODEC);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf output, @NonNull Timer value) {
            output.writeJsonWithCodec(CODEC, value);
        }
    };

    public Timer(Identifier id, int duration) {
        this.duration = duration;
        this.freezeImmune = false;
        this.removed = false;
        this.deathImmune = false;
        this.disconnectImmune = true;
        this.id = id;
    }

    public Timer(Identifier id, int duration, boolean deathImmune, boolean disconnectImmune, boolean freezeImmune) {
        this.duration = duration;
        this.removed = false;
        this.freezeImmune = freezeImmune;
        this.deathImmune = deathImmune;
        this.disconnectImmune = disconnectImmune;
        this.id = id;
    }

    /* - methods related to events - */

    public void tick(Player player) {
        if (!(duration-- <= 0) && !isRemoved()) {
            duration--;
        } else {
            this.onTimeout(player);
        }
    }

    public void onTimeout(Player player) {
        TimerTimeoutEvent.EVENT.invoker().timeout(player,this.getId());
        this.remove();
    }

    /* - conditions - */

    public boolean isFreezeImmune() {
        return freezeImmune;
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
        return !isRemoved() && ((getDuration() - 1.0f) <= 0.0f);
    }

    /* - getter and setter - */

    public void remove() {
        this.removed = true;
    }

    public int getDuration() {
        return duration;
    }

    public Identifier getId() {
        return this.id;
    }

    public static Codec<Timer> getCodec() {
        return CODEC;
    }
}
