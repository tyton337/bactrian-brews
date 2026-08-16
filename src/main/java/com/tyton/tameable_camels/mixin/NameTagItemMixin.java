package com.tyton.tameable_camels.mixin;

import com.tyton.tameable_camels.advancement.ModCriteria;
import com.tyton.tameable_camels.entity.custom.BactrianCamelEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.NameTagItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NameTagItem.class)
public class NameTagItemMixin {

  @Inject(method = "useOnEntity", at = @At("RETURN"))
  private void tameable_camels$onNameTagUsed(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
    if (user.getWorld().isClient()) return;
    if (!cir.getReturnValue().isAccepted()) return;
    if (!(entity instanceof BactrianCamelEntity camel)) return;
    if (camel.getCustomName() == null || !"Frederick".equals(camel.getCustomName().getString())) return;
    if (user instanceof ServerPlayerEntity serverPlayer) {
      ModCriteria.NAMED_CAMEL_FREDERICK.trigger(serverPlayer);
    }
  }
}