package net.kunmc.lab.boobsmodfabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.kunmc.lab.boobsmodfabric.Boobsmodfabric;
import net.kunmc.lab.boobsmodfabric.interfaces.ILivingEntityMixin;
import net.kunmc.lab.boobsmodfabric.network.ClientNetworking;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class BoobsmodfabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyBinding binding = KeyBindingHelper.registerKeyBinding(new KeyBinding("揺らす", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "BoobsMod"));
        ClientTickCallback.EVENT.register(client -> {
            while (binding.wasPressed()) {
                ClientNetworking.sendShakeC2SPacket();
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(Boobsmodfabric.SHAKE_S2C_PACKET_ID, (client, handler, buf, responseSender) -> {
            int entityID = buf.readInt();
            int progress = buf.readInt();
            client.execute(() -> {
                Entity entity = client.world.getEntityById(entityID);
                if (!(entity instanceof AbstractClientPlayerEntity)) {
                    return;
                }
                ((ILivingEntityMixin) entity).shake(progress);
            });
        });
    }
}
