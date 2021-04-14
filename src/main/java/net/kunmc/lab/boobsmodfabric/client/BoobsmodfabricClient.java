package net.kunmc.lab.boobsmodfabric.client;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.impl.screenhandler.client.ClientNetworking;
import net.kunmc.lab.boobsmodfabric.Boobsmodfabric;
import net.kunmc.lab.boobsmodfabric.interfaces.ILivingEntityMixin;
import net.kunmc.lab.boobsmodfabric.mixin.LivingEntityMixin;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class BoobsmodfabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyBinding binding = KeyBindingHelper.registerKeyBinding(new KeyBinding("揺らす", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "BoobsMod"));
        ClientTickCallback.EVENT.register(client -> {
            while (binding.wasPressed()) {
                // ((ILivingEntityMixin) client.player).shake();
                PacketByteBuf packetByteBuf = PacketByteBufs.create();
                packetByteBuf.writeInt(1);
                ClientPlayNetworking.send(Boobsmodfabric.SHAKE_C2S_PACKET_ID, packetByteBuf);
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(Boobsmodfabric.SHAKE_S2C_PACKET_ID, (client, handler, buf, responseSender) -> {
            int entityID = buf.readInt();
            client.execute(() -> {
                Entity entity = client.world.getEntityById(entityID);
                if (!(entity instanceof AbstractClientPlayerEntity)) {
                    return;
                }
                ((ILivingEntityMixin) entity).shake();
            });
        });
    }
}
