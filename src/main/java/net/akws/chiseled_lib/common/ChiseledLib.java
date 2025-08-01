package net.akws.chiseled_lib.common;

import net.akws.chiseled_lib.common.component.ChiseledLibComponents;
import net.akws.chiseled_lib.common.mixin_interface.ScreenshakeDataInterface;
import net.akws.chiseled_lib.common.util.cameraEffects.ScreenshakeDataHolder;
import net.akws.chiseled_lib.common.util.cameraEffects.ScreenshakeManager;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChiseledLib implements ModInitializer {
	public static final String MOD_ID = "chiseled_lib";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Identifier id(String key) {return Identifier.of(MOD_ID,key);}

	@Override
	public void onInitialize() {
		ChiseledLibComponents.init();
		UseItemCallback.EVENT.register((playerEntity, world, hand) -> {
			ScreenshakeDataHolder data = new ScreenshakeDataHolder(5,20 * 5,playerEntity.getPos(),20);
			ScreenshakeManager.createScreenShake(data,playerEntity);

            return ActionResult.PASS;
        });

	}
}