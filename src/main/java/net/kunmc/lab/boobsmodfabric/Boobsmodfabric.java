package net.kunmc.lab.boobsmodfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kunmc.lab.boobsmodfabric.interfaces.ILivingEntityMixin;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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
                ((ILivingEntityMixin) player).shake(30);
            });
        });
    }
}

