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
public class BactrianCamelChestFeatureRenderer extends FeatureRenderer<BactrianCamelEntity, BactrianCamelModel<BactrianCamelEntity>> {

  private static final String TEXTURE_PATH = "textures/entity/bactrian_camel_chest";
  private final BactrianCamelChestModel<BactrianCamelEntity> chestModel;
  private String lastDye = null;
  private Identifier texture;

  public BactrianCamelChestFeatureRenderer(FeatureRendererContext<BactrianCamelEntity, BactrianCamelModel<BactrianCamelEntity>> context, EntityRendererFactory.Context factoryContext) {
    super(context);
    this.chestModel = new BactrianCamelChestModel<>(factoryContext.getPart(ModModelLayers.BACTRIAN_CAMEL_CHEST));
  }

  @Override
  public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, BactrianCamelEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
    if (!entity.hasChest()) {
      return;
    }
    String dye = entity.getDye();
    if (!dye.equals(this.lastDye)) {
      this.setChestTexture(dye);
      this.lastDye = dye;
    }
    this.chestModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
    VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(this.texture));
    this.chestModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
  }

  private void setChestTexture(String dye) {
    switch (dye) {
      case "black" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_black.png");
      case "blue" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_blue.png");
      case "brown" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_brown.png");
      case "cyan" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_cyan.png");
      case "gray" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_gray.png");
      case "green" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_green.png");
      case "light_blue" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_light_blue.png");
      case "light_gray" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_light_gray.png");
      case "lime" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_lime.png");
      case "magenta" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_magenta.png");
      case "orange" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_orange.png");
      case "pink" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_pink.png");
      case "purple" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_purple.png");
      case "red" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_red.png");
      case "white" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_white.png");
      case "yellow" -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + "_yellow.png");
      default -> this.texture = new Identifier(BactrianBrews.MOD_ID, TEXTURE_PATH + ".png");
    }
  }
}