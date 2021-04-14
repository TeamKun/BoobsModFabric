package net.kunmc.lab.boobsmodfabric;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kunmc.lab.boobsmodfabric.interfaces.ILivingEntityMixin;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.glfw.GLFW;

public class Boobsmodfabric implements ModInitializer {

    public static final StatusEffect GROW_BOOBS;
    public static final EntityAttribute BOOBS_SIZE;
    public static final Identifier SHAKE_S2C_PACKET_ID = new Identifier("boobsmodfabric", "shake_s2c");
    public static final Identifier SHAKE_C2S_PACKET_ID = new Identifier("boobsmodfabric", "shake_c2s");

    static {
        BOOBS_SIZE = new ClampedEntityAttribute("boobSize", 0, 0, 100).setTracked(true);
        GROW_BOOBS = new GrowBoobs();
        GROW_BOOBS.addAttributeModifier(BOOBS_SIZE, "89e29cde-b117-42cc-8b9a-1f6842a360bd", 1.0D, EntityAttributeModifier.Operation.ADDITION);
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.ATTRIBUTE, new Identifier("boobsmodfabric", "boobs_size"), BOOBS_SIZE);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("boobsmodfabric", "grow_boobs"), GROW_BOOBS);

        ServerPlayNetworking.registerGlobalReceiver(SHAKE_C2S_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                server.getPlayerManager().getPlayerList().forEach(serverPlayerEntity -> {
                    PacketByteBuf buffer = PacketByteBufs.create();
                    buffer.writeInt(player.getEntityId());
                    ServerPlayNetworking.send(serverPlayerEntity, Boobsmodfabric.SHAKE_S2C_PACKET_ID, buffer);
                });
            });
        });
    }
}

