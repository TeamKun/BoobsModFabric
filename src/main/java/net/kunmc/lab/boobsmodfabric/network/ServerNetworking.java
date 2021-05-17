package net.kunmc.lab.boobsmodfabric.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kunmc.lab.boobsmodfabric.Boobsmodfabric;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;

public class ServerNetworking {

    public static void sendShakeS2CPacket(LivingEntity player, int progress) {
        player.getServer().getPlayerManager().getPlayerList().forEach(serverPlayerEntity -> {
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeInt(player.getEntityId());
            buffer.writeInt(progress);
            ServerPlayNetworking.send(serverPlayerEntity, Boobsmodfabric.SHAKE_S2C_PACKET_ID, buffer);
        });
    }

}
