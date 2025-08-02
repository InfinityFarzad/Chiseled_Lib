package net.akws.chiseled_lib.common.component;

import net.akws.chiseled_lib.common.ChiseledLib;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class ChiseledCCARegistries implements EntityComponentInitializer {

    public static ComponentKey<ScreenshakeDataComponent> SCREENSHAKE_COMPONENT = ComponentRegistry.getOrCreate(ChiseledLib.id("screenshake_component"), ScreenshakeDataComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(SCREENSHAKE_COMPONENT, ScreenshakeDataComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
