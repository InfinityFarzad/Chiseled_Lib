package net.akws.chiseled_lib.common.system.timer;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

public interface TimerTimeoutEvent {
    Event<TimerTimeoutEvent> EVENT = EventFactory.createArrayBacked(TimerTimeoutEvent.class,
            (listeners) -> (player, id) -> {
        for (TimerTimeoutEvent listener : listeners) {
            listener.timeout(player,id);
        }
    });
    void timeout(Player player, Identifier identifier);
}
