package com.tyton.bactrian_brews.mixin;

import com.tyton.bactrian_brews.util.CamelColorAccessor;
import net.minecraft.client.render.entity.CamelEntityRenderer;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CamelEntityRenderer.class)
public class CamelEntityRendererMixin {

  @Unique
  private static final Identifier bactrian_brews$GRAYSCALE_TEXTURE =
      new Identifier("bactrian_brews", "textures/entity/camel_grayscale.png");

  @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
  private void bactrian_brews$swapToGrayscale(CamelEntity camel, CallbackInfoReturnable<Identifier> cir) {
    if (((CamelColorAccessor) camel).bactrian_brews$hasCustomColor()) {
      cir.setReturnValue(bactrian_brews$GRAYSCALE_TEXTURE);
    }
  }
}