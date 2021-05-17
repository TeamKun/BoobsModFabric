package net.kunmc.lab.boobsmodfabric.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.kunmc.lab.boobsmodfabric.Boobsmodfabric;
import net.minecraft.network.PacketByteBuf;

public class ClientNetworking {

    public static void sendShakeC2SPacket() {
        PacketByteBuf packetByteBuf = PacketByteBufs.create();
        ClientPlayNetworking.send(Boobsmodfabric.SHAKE_C2S_PACKET_ID, packetByteBuf);
    }

}
