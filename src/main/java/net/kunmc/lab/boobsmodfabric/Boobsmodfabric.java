package net.kunmc.lab.boobsmodfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.fabric.api.event.server.ServerTickCallback;
import net.kunmc.lab.boobsmodfabric.interfaces.ILivingEntityMixin;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.glfw.GLFW;

public class Boobsmodfabric implements ModInitializer {

    public static final StatusEffect GROW_BOOBS;
    public static final EntityAttribute BOOBS_SIZE;

    static {
        GROW_BOOBS = new GrowBoobs();
        BOOBS_SIZE = new ClampedEntityAttribute(null, "boobSize", 0, 0, 100).setName("Boob Size").setTracked(true);

        GROW_BOOBS.addAttributeModifier(BOOBS_SIZE, "89e29cde-b117-42cc-8b9a-1f6842a360bd", 1.0D, EntityAttributeModifier.Operation.ADDITION);
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("boobsmodfabric", "grow_boobs"), GROW_BOOBS);
        KeyBinding binding = KeyBindingHelper.registerKeyBinding(new KeyBinding("揺らす", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "BoobsMod"));
        ClientTickCallback.EVENT.register(client -> {
            while (binding.wasPressed()) {
                ((ILivingEntityMixin) client.player).shake();
            }
        });
    }
}

