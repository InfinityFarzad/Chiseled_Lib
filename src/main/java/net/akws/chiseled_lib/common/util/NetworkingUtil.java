package net.akws.chiseled_lib.common.util;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NetworkingUtil {
    public static void sendPacketToAllClients(Level level, CustomPacketPayload payload) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            for (ServerPlayer player : PlayerLookup.level(serverLevel)) {
                ServerPlayNetworking.send(player,payload);
            }
        }
    }

    public static void sendPacketToAllNearbyClients(Level level, CustomPacketPayload payload, Vec3 pos, double radius) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            for (ServerPlayer player : PlayerLookup.around(serverLevel,pos,radius)) {
                ServerPlayNetworking.send(player, payload);
            }
        }
    }
}
