package net.kunmc.lab.boobsmodfabric.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityModel.class)
public abstract class PlayerEntityModelMixin extends BipedEntityModel {

    public ModelPart rightBoob;
    public ModelPart leftBoob;

    @Inject(method = "getBodyParts", at = @At("RETURN"), cancellable = true)
    private void getModedBodyParts(CallbackInfoReturnable<Iterable<ModelPart>> cir) {
//        this.rightBoob = new ModelPart(this, 22, 22);
//        this.rightBoob.addCuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0);
//        this.rightBoob.setPivot(-2.1F, 4.0F, -3.0F);
//        this.leftBoob = new ModelPart(this, 22, 22);
//        this.leftBoob.addCuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0);
//        this.leftBoob.setPivot(2.1F, 4.0F, -3.0F);

        Iterable<ModelPart> beforValue = cir.getReturnValue();
        cir.setReturnValue(Iterables.concat(beforValue, ImmutableList.of(rightBoob, leftBoob)));
    }

    @Inject(method = "<init>(FZ)V", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        this.rightBoob = new ModelPart(this, 22, 22);
        this.rightBoob.addCuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0);
        this.rightBoob.setPivot(-2.1F, 4.0F, -3.0F);
        this.leftBoob = new ModelPart(this, 22, 22);
        this.leftBoob.addCuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0);
        this.leftBoob.setPivot(2.1F, 4.0F, -3.0F);
    }

    @Inject(method = "setAngles", at = @At("TAIL"))
    private void injected(LivingEntity livingEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
//        this.rightBoob.copyPositionAndRotation(this.torso);
//        this.leftBoob.copyPositionAndRotation(this.torso);
        this.rightBoob.pitch = this.torso.pitch;
        this.rightBoob.yaw = this.torso.yaw;
        this.rightBoob.roll = this.torso.roll;
        this.leftBoob.pitch = this.torso.pitch;
        this.leftBoob.yaw = this.torso.yaw;
        this.leftBoob.roll = this.torso.roll;

        if (this.isSneaking) {
            this.rightBoob.setPivot(-2.1F, 7.2F, -0.0F);
            this.leftBoob.setPivot(2.1F, 7.2F, -0.0F);
        } else {
            this.rightBoob.setPivot(-2.1F, 4.0F, -3.0F);
            this.leftBoob.setPivot(2.1F, 4.0F, -3.0F);
        }

        Vec3d vec3d = livingEntity.getVelocity();
        this.rightBoob.pitch = (float) Math.min(0.2, this.rightBoob.pitch + vec3d.y);
        this.leftBoob.pitch = (float) Math.min(0.2, this.leftBoob.pitch + vec3d.y);
    }

    public PlayerEntityModelMixin(float scale) {
        super(scale);
    }
}
