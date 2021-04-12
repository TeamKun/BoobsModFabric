package net.kunmc.lab.boobsmodfabric.mixin;

import net.kunmc.lab.boobsmodfabric.Boobsmodfabric;
import net.kunmc.lab.boobsmodfabric.interfaces.ILivingEntityMixin;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AbstractEntityAttributeContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements ILivingEntityMixin {

    private static final int SHAKE_TIME = 50;
    private int shakeProgress = 0;

    @Override
    public float getProgress() {
        return shakeProgress / 50;
    }

    @Shadow
    public abstract AbstractEntityAttributeContainer getAttributes();

    @Inject(method = "initAttributes", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        this.getAttributes().register(Boobsmodfabric.BOOBS_SIZE);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        shakeProgress = Math.max(0, shakeProgress - 1);
    }

    public void shake() {
        shakeProgress = SHAKE_TIME;
    }
}
