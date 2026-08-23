package com.tyton.bactrian_brews.entity.client;

import com.tyton.bactrian_brews.entity.custom.BactrianCamelEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class BactrianCamelChestModel<T extends BactrianCamelEntity> extends SinglePartEntityModel<T> {
  private final ModelPart root;
  private final ModelPart body;

  private final ModelPart leftBag;
  private final ModelPart rightBag;

  public BactrianCamelChestModel(ModelPart modelPart) {
    this.root = modelPart.getChild("root");
    this.body = this.root.getChild("body");

    this.leftBag = this.body.getChild("left_bag");
    this.rightBag = this.body.getChild("right_bag");
  }

  public static TexturedModelData getTexturedModelData() {
    ModelData modelData = new ModelData();
    ModelPartData modelPartData = modelData.getRoot();
    ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
    ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.5F, -20.0F, 9.5F));

    body.addChild("right_bag", ModelPartBuilder.create().uv(0, 0).mirrored()
            .cuboid(-17.5F, -11.0F, -24.6F, 15.0F, 10.0F, 7.0F, new Dilation(0.0F)).mirrored(false),
        ModelTransform.of(-10.5F, 0.0F, 0.0F, 0.0F, -(float)(Math.PI / 2), 0.0F));

    body.addChild("left_bag", ModelPartBuilder.create().uv(0, 0)
            .cuboid(-17.5F, -11.0F, -2.6F, 15.0F, 10.0F, 7.0F, new Dilation(0.0F)),
        ModelTransform.of(-10.5F, 0.0F, 0.0F, 0.0F, -(float)(Math.PI / 2), 0.0F));

    return TexturedModelData.of(modelData, 128, 128);
  }

  @Override
  public ModelPart getPart() {
    return this.root;
  }

  @Override
  public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    this.getPart().traverse().forEach(ModelPart::resetTransform);

    float currentTicks = (float) entity.getLastPoseTickDelta();
    float tickDelta = net.minecraft.client.MinecraftClient.getInstance().getTickDelta();
    float interpolatedTicks = MathHelper.clamp(currentTicks + tickDelta, 0.0F, 14.0F);
    float transitionProgress = interpolatedTicks / 14.0F;

    float sitProgress = entity.isSitting() ? transitionProgress : 1.0F - transitionProgress;
    this.body.pivotY = MathHelper.lerp(sitProgress, -20.0F, -12.0F);
  }

  @Override
  public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
    this.getPart().render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
  }
}
