package com.tyton.bactrian_brews.entity.client;

import com.tyton.bactrian_brews.BactrianBrews;
import com.tyton.bactrian_brews.entity.custom.BactrianCamelEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BactrianCamelRenderer extends MobEntityRenderer<BactrianCamelEntity, BactrianCamelModel<BactrianCamelEntity>> {
  private static final Identifier TEXTURE = new Identifier(BactrianBrews.MOD_ID, "textures/entity/bactrian_camel.png");

  public BactrianCamelRenderer(EntityRendererFactory.Context context) {
    super(context, new BactrianCamelModel<>(context.getPart(ModModelLayers.BACTRIAN_CAMEL)), 0.7f);
    this.addFeature(new BactrianCamelSaddleFeatureRenderer(this, context));
    this.addFeature(new BactrianCamelChestFeatureRenderer(this, context));
  }

  @Override
  public Identifier getTexture(BactrianCamelEntity entity) {
    return TEXTURE;
  }

  @Override
  public void render(BactrianCamelEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
    super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
  }
}