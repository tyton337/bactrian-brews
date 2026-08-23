package com.tyton.bactrian_brews.mixin;

import com.tyton.bactrian_brews.util.CamelColorAccessor;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CamelEntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.CamelEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CamelEntityModel.class)
public abstract class CamelEntityModelMixin<T extends CamelEntity> extends SinglePartEntityModel<T> {

  @Shadow @Final private ModelPart root;
  @Shadow @Final private ModelPart[] saddleAndBridle;
  @Shadow @Final private ModelPart[] reins;

  @Unique
  private int bactrian_brews$color = -1;

  @Unique
  private boolean bactrian_brews$isChild = false;

  @Inject(method = "setAngles", at = @At("TAIL"))
  private void bactrian_brews$captureColor(T entity, float f, float g, float h, float i, float j, CallbackInfo ci) {
    CamelColorAccessor accessor = (CamelColorAccessor) entity;
    this.bactrian_brews$color = accessor.bactrian_brews$hasCustomColor()
        ? accessor.bactrian_brews$getSkinColor()
        : -1;
    this.bactrian_brews$isChild = entity.isBaby();
  }

  @Inject(method = "render", at = @At("HEAD"), cancellable = true)
  private void bactrian_brews$splitBodyAndEquipment(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, CallbackInfo ci) {
    if (this.bactrian_brews$color == -1) {
      return;
    }
    ci.cancel();

    float tintRed = ((this.bactrian_brews$color >> 16) & 0xFF) / 255.0F;
    float tintGreen = ((this.bactrian_brews$color >> 8) & 0xFF) / 255.0F;
    float tintBlue = (this.bactrian_brews$color & 0xFF) / 255.0F;

    boolean[] savedSaddleBridle = new boolean[saddleAndBridle.length];
    for (int idx = 0; idx < saddleAndBridle.length; idx++) {
      savedSaddleBridle[idx] = saddleAndBridle[idx].visible;
    }
    boolean[] savedReins = new boolean[reins.length];
    for (int idx = 0; idx < reins.length; idx++) {
      savedReins[idx] = reins[idx].visible;
    }

    matrices.push();
    if (this.bactrian_brews$isChild) {
      matrices.scale(0.45F, 0.45F, 0.45F);
      matrices.translate(0.0F, 1.834375F, 0.0F);
    }

    for (ModelPart part : saddleAndBridle) part.visible = false;
    for (ModelPart part : reins) part.visible = false;
    this.root.render(matrices, vertices, light, overlay, tintRed, tintGreen, tintBlue, alpha);

    for (int idx = 0; idx < saddleAndBridle.length; idx++) saddleAndBridle[idx].visible = savedSaddleBridle[idx];
    for (int idx = 0; idx < reins.length; idx++) reins[idx].visible = savedReins[idx];

    bactrian_brews$renderEquipment(matrices, vertices, light, overlay, alpha);

    matrices.pop();
  }

  @Unique
  private void bactrian_brews$renderEquipment(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float alpha) {
    ModelPart body = this.root.getChild("body");
    ModelPart head = body.getChild("head");

    matrices.push();
    body.rotate(matrices);
    ModelPart saddle = this.saddleAndBridle[0];
    if (saddle.visible) saddle.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, alpha);

    head.rotate(matrices);
    ModelPart bridle = this.saddleAndBridle[1];
    ModelPart reinsPart = this.reins[0];
    if (bridle.visible) bridle.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, alpha);
    if (reinsPart.visible) reinsPart.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, alpha);
    matrices.pop();
  }
}
