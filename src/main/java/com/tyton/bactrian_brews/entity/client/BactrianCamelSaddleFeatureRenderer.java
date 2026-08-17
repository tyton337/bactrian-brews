package com.tyton.bactrian_brews.entity.client;

import com.tyton.bactrian_brews.BactrianBrews;
import com.tyton.bactrian_brews.entity.custom.BactrianCamelEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BactrianCamelSaddleFeatureRenderer extends FeatureRenderer<BactrianCamelEntity, BactrianCamelModel<BactrianCamelEntity>> {

  private static final Identifier SADDLE_LAYER_TEXTURE = new Identifier(BactrianBrews.MOD_ID, "textures/entity/bactrian_camel_saddle.png");
  private final BactrianCamelSaddleModel<BactrianCamelEntity> saddleModel;

  public BactrianCamelSaddleFeatureRenderer(FeatureRendererContext<BactrianCamelEntity, BactrianCamelModel<BactrianCamelEntity>> context, EntityRendererFactory.Context factoryContext) {
    super(context);
    this.saddleModel = new BactrianCamelSaddleModel<>(factoryContext.getPart(ModModelLayers.BACTRIAN_CAMEL_SADDLE));
  }

  @Override
  public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, BactrianCamelEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
    if (!entity.isSaddled()) {
      return;
    }
    this.saddleModel.getReins().visible = entity.hasPassengers();
    this.saddleModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
    VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(SADDLE_LAYER_TEXTURE));
    this.saddleModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
  }
}
