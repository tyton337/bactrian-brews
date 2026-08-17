package com.tyton.bactrian_brews.mixin;

import com.tyton.bactrian_brews.util.CamelColorAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CamelEntity.class)
public abstract class CamelEntityMixin extends AnimalEntity implements CamelColorAccessor {

  @Unique
  private static final TrackedData<Integer> bactrian_brews$SKIN_COLOR =
      DataTracker.registerData(CamelEntity.class, TrackedDataHandlerRegistry.INTEGER);

  protected CamelEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
    super(entityType, world);
  }

  @Inject(method = "initDataTracker", at = @At("TAIL"))
  private void bactrian_brews$initSkinColor(CallbackInfo ci) {
    this.dataTracker.startTracking(bactrian_brews$SKIN_COLOR, -1); // -1 = no custom color, render vanilla
  }

  @Override
  public int bactrian_brews$getSkinColor() {
    return this.dataTracker.get(bactrian_brews$SKIN_COLOR);
  }

  @Override
  public void bactrian_brews$setSkinColor(int color) {
    this.dataTracker.set(bactrian_brews$SKIN_COLOR, color);
  }

  @Override
  public boolean bactrian_brews$hasCustomColor() {
    return this.dataTracker.get(bactrian_brews$SKIN_COLOR) != -1;
  }

  // Without this, a vanilla camel never enters "love mode" from cactus, so our
  // AnimalMateGoal (which only runs on the Bactrian side) would never find it as a candidate.
  @Inject(method = "isBreedingItem", at = @At("HEAD"), cancellable = true)
  private void bactrian_brews$allowCactusBreeding(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
    if (stack.isOf(Items.CACTUS)) {
      cir.setReturnValue(true);
    }
  }
}