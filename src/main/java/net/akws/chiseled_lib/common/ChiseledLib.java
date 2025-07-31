package net.akws.chiseled_lib.common;

import net.akws.chiseled_lib.common.component.ChiseledLibComponents;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChiseledLib implements ModInitializer {
	public static final String MOD_ID = "chiseled_lib";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ChiseledLibComponents.init();
	}
}