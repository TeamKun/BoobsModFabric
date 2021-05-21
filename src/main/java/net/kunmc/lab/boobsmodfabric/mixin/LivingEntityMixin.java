package net.kunmc.lab.boobsmodfabric.mixin;

import net.kunmc.lab.boobsmodfabric.Boobsmodfabric;
import net.kunmc.lab.boobsmodfabric.interfaces.ILivingEntityMixin;
import net.kunmc.lab.boobsmodfabric.network.ServerNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ILivingEntityMixin {

    private static final int SHAKE_TIME = 30;
    private int shakeProgress = 0;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public float getProgress() {
        return (float) shakeProgress / (float) SHAKE_TIME;
    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void addAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(Boobsmodfabric.BOOBS_SIZE, 0.0D);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        shakeProgress = Math.max(0, shakeProgress - 1);
    }

    public void shake() {
        shake(SHAKE_TIME);
    }

    public void shake(int progress) {
        shakeProgress = progress;
        if (!(world instanceof ServerWorld)) {
            return;
        }
        if (!((Object) this instanceof PlayerEntity)) {
            return;
        }
        ServerNetworking.sendShakeS2CPacket((LivingEntity) (Object) this, progress);
    }

    @Inject(method = "jump", at = @At("TAIL"))
    private void jumpInject(CallbackInfo ci) {
        if (!(world instanceof ServerWorld)) {
            return;
        }
        shake();
    }

    @Inject(method = "swingHand(Lnet/minecraft/util/Hand;Z)V", at = @At("TAIL"))
    private void swingHand(Hand hand, boolean bl, CallbackInfo ci) {
        if (!(world instanceof ServerWorld)) {
            return;
        }
        shake(15);
    }

    @Inject(method = "takeKnockback", at = @At("TAIL"))
    private void takeKnockBack(float f, double d, double e, CallbackInfo ci) {
        if (!(world instanceof ServerWorld)) {
            return;
        }
        shake();
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void tickMovement(CallbackInfo ci) {
        if (!(world instanceof ServerWorld)) {
            return;
        }
        if (!isSprinting()) {
            return;
        }
        shake(15);
    }
}
