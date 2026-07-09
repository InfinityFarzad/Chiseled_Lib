package net.akws.chiseled_lib.common.util;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NetworkingUtil {
    public static void sendPacketToAllClients(World world, CustomPayload payload) {
        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            for (ServerPlayerEntity player : PlayerLookup.world(serverWorld)) {
                ServerPlayNetworking.send(player,payload);
            }
        }
    }

    public static void sendPacketToAllNearbyClients(World world, CustomPayload payload, Vec3d pos, double radius) {
        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            for (ServerPlayerEntity player : PlayerLookup.around(serverWorld,pos,radius)) {
                ServerPlayNetworking.send(player, payload);
            }
        }
    }
}
