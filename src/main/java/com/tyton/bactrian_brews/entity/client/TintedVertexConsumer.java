package com.tyton.bactrian_brews.entity.client;

import net.minecraft.client.render.VertexConsumer;

/**
 * Wraps a VertexConsumer and multiplies every color it receives by a fixed
 * tint (red/green/blue/alpha, each 0.0-1.0). This lets us tint the grayscale
 * camel texture per-entity WITHOUT having to reimplement the entity render
 * pipeline (which is what caused the upside-down rendering bug - the custom
 * render() was skipping the matrices.scale(-1,-1,1) + rotation setup that
 * MobEntityRenderer/LivingEntityRenderer normally perform).
 */
public class TintedVertexConsumer implements VertexConsumer {
  private final VertexConsumer delegate;
  private final int tintRed;
  private final int tintGreen;
  private final int tintBlue;
  private final int tintAlpha;

  public TintedVertexConsumer(VertexConsumer delegate, float red, float green, float blue, float alpha) {
    this.delegate = delegate;
    this.tintRed = (int) (red * 255.0F);
    this.tintGreen = (int) (green * 255.0F);
    this.tintBlue = (int) (blue * 255.0F);
    this.tintAlpha = (int) (alpha * 255.0F);
  }

  @Override
  public VertexConsumer vertex(double x, double y, double z) {
    delegate.vertex(x, y, z);
    return this;
  }

  @Override
  public VertexConsumer color(int red, int green, int blue, int alpha) {
    delegate.color(
        (red * tintRed) / 255,
        (green * tintGreen) / 255,
        (blue * tintBlue) / 255,
        (alpha * tintAlpha) / 255
    );
    return this;
  }

  @Override
  public VertexConsumer texture(float u, float v) {
    delegate.texture(u, v);
    return this;
  }

  @Override
  public VertexConsumer overlay(int u, int v) {
    delegate.overlay(u, v);
    return this;
  }

  @Override
  public VertexConsumer light(int u, int v) {
    delegate.light(u, v);
    return this;
  }

  @Override
  public VertexConsumer normal(float x, float y, float z) {
    delegate.normal(x, y, z);
    return this;
  }

  @Override
  public void next() {
    delegate.next();
  }

  @Override
  public void fixedColor(int red, int green, int blue, int alpha) {
    delegate.fixedColor(red, green, blue, alpha);
  }

  @Override
  public void unfixColor() {
    delegate.unfixColor();
  }
}