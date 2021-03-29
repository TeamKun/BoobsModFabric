package net.kunmc.lab.boobsmodfabric;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Boobsmodfabric implements ModInitializer {

    public static final StatusEffect GROW_BOOBS = new GrowBoobs();
    public static final EntityAttribute BOOBS_SIZE = new ClampedEntityAttribute(null, "boobSize", 0, 0, 100).setName("Boob Size");

    @Override
    public void onInitialize() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("boobsmodfabric", "grow_boobs"), GROW_BOOBS);
    }
}

