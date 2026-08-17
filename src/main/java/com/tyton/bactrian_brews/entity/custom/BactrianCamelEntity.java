package com.tyton.bactrian_brews.entity.custom;

import com.tyton.bactrian_brews.advancement.ModCriteria;
import com.tyton.bactrian_brews.entity.ModEntities;
import com.tyton.bactrian_brews.util.CamelColorAccessor;
import com.tyton.bactrian_brews.util.CamelColorUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BactrianCamelEntity extends CamelEntity {
  private static final TrackedData<Integer> SKIN_COLOR =
      DataTracker.registerData(BactrianCamelEntity.class, TrackedDataHandlerRegistry.INTEGER);
  private static final TrackedData<Boolean> HAS_CHEST =
      DataTracker.registerData(BactrianCamelEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
  private static final TrackedData<Boolean> FREDERICK_COLOR_ROLLED =
      DataTracker.registerData(BactrianCamelEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
  protected SimpleInventory items;

  public BactrianCamelEntity(EntityType<? extends CamelEntity> entityType, World world) {
    super(entityType, world);
    this.setupInventory();
  }

  @Override
  protected void initDataTracker() {
    super.initDataTracker();
    this.dataTracker.startTracking(SKIN_COLOR, CamelColorUtil.CHAMOMILE);
    this.dataTracker.startTracking(HAS_CHEST, false);
    this.dataTracker.startTracking(FREDERICK_COLOR_ROLLED, false);
  }

  public int getSkinColor() {
    return this.dataTracker.get(SKIN_COLOR);
  }

  public void setSkinColor(int color) {
    this.dataTracker.set(SKIN_COLOR, color);
  }

  @Override
  public net.minecraft.entity.EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
    this.setSkinColor(CamelColorUtil.getRandomNaturalColor(this.random));
    return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
  }

  @Override
  protected void initGoals() {
    super.initGoals();
    this.goalSelector.add(2, new AnimalMateGoal(this, 1.0, CamelEntity.class));
  }


  @Override
  public boolean canBreedWith(AnimalEntity other) {
    if (other == this) return false;
    if (!(other instanceof CamelEntity)) return false;
    return this.isInLove() && other.isInLove();
  }

  @Override
  protected boolean canAddPassenger(Entity passenger) {
    return this.getPassengerList().isEmpty();
  }

  private void setupInventory() {
    this.items = new SimpleInventory(15);
  }

  public boolean hasChest() {
    return this.dataTracker.get(HAS_CHEST);
  }

  public void setHasChest(boolean hasChest) {
    this.dataTracker.set(HAS_CHEST, hasChest);
  }

  @Override
  public boolean isBreedingItem(ItemStack stack) {
    return stack.isOf(Items.CACTUS);
  }

  @Override
  public ActionResult interactMob(PlayerEntity player, Hand hand) {
    ItemStack itemStack = player.getStackInHand(hand);
    if (this.isSitting()) {
      if (!this.getWorld().isClient()) {
        this.setSitting(false);
        this.playSound(SoundEvents.ENTITY_CAMEL_STAND, 1.0F, 1.0F);
      }
      return ActionResult.success(this.getWorld().isClient());
    }
    if (!this.hasChest() && itemStack.isOf(Items.CHEST)) {
      if (!this.getWorld().isClient()) {
        this.setHasChest(true);
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, 1.0F);
        if (!player.getAbilities().creativeMode) {
          itemStack.decrement(1);
        }
      }
      return ActionResult.success(this.getWorld().isClient());
    }
    if (player.isSneaking() && this.hasChest()) {
      if (!this.getWorld().isClient()) {
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, playerInventory, playerEntity) ->
                GenericContainerScreenHandler.createGeneric9x2(syncId, playerInventory),
            this.getDisplayName()
        ));
      }
      return ActionResult.success(this.getWorld().isClient());
    }
    return super.interactMob(player, hand);
  }

  @Override
  protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
    if (this.hasPassenger(passenger)) {
      float targetHeight = this.getDimensions(this.getPose()).height;
      double verticalOffset = (double) targetHeight - 0.20D;
      if (this.isSitting()) {
        verticalOffset -= 0.65D;
      }
      float yawRadians = this.getYaw() * ((float)Math.PI / 180F);
      float cosYaw = MathHelper.cos(yawRadians);
      float sinYaw = MathHelper.sin(yawRadians);
      double forwardShift = 0.35D;
      double offsetX = -((double)sinYaw * forwardShift);
      double offsetZ = (double)cosYaw * forwardShift;
      positionUpdater.accept(
          passenger,
          this.getX() + offsetX,
          this.getY() + verticalOffset + passenger.getHeightOffset(),
          this.getZ() + offsetZ
      );
    }
  }

  @Override
  protected void dropInventory() {
    super.dropInventory();
    if (this.items != null) {
      for (int i = 0; i < this.items.size(); ++i) {
        ItemStack itemStack = this.items.getStack(i);
        if (!itemStack.isEmpty()) {
          this.dropStack(itemStack);
        }
      }
    }
    if (this.hasChest()) {
      if (!this.getWorld().isClient()) {
        this.dropItem(Blocks.CHEST);
      }
      this.setHasChest(false);
    }
  }

  @Override
  public void writeCustomDataToNbt(NbtCompound nbt) {
    super.writeCustomDataToNbt(nbt);
    nbt.putInt("SkinColor", this.getSkinColor());
    nbt.putBoolean("HasChest", this.hasChest());
    nbt.putBoolean("FrederickColorRolled", this.dataTracker.get(FREDERICK_COLOR_ROLLED));
    if (this.items != null) {
      NbtList nbtList = new NbtList();
      for (int i = 0; i < this.items.size(); ++i) {
        ItemStack itemStack = this.items.getStack(i);
        if (itemStack.isEmpty()) continue;
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putByte("Slot", (byte)i);
        itemStack.writeNbt(nbtCompound);
        nbtList.add(nbtCompound);
      }
      nbt.put("ChestItems", nbtList);
    }
  }

  @Override
  public void readCustomDataFromNbt(NbtCompound nbt) {
    super.readCustomDataFromNbt(nbt);
    if (nbt.getType("SkinColor") == NbtElement.STRING_TYPE) {
      String colorName = nbt.getString("SkinColor");
      int convertedHex = com.tyton.bactrian_brews.util.CamelColorUtil.getColorFromName(colorName);
      this.setSkinColor(convertedHex);
    } else {
      this.setSkinColor(nbt.getInt("SkinColor"));
    }
    this.dataTracker.set(FREDERICK_COLOR_ROLLED, nbt.getBoolean("FrederickColorRolled"));
    this.setHasChest(nbt.getBoolean("HasChest"));
    if (nbt.contains("ChestItems", 9)) {
      NbtList nbtList = nbt.getList("ChestItems", 10);
      this.setupInventory();
      for (int i = 0; i < nbtList.size(); ++i) {
        NbtCompound nbtCompound = nbtList.getCompound(i);
        int j = nbtCompound.getByte("Slot") & 255;
        if (j >= this.items.size()) continue;
        this.items.setStack(j, ItemStack.fromNbt(nbtCompound));
      }
    }
  }

  @Override
  public CamelEntity createChild(ServerWorld serverWorld, PassiveEntity partner) {
    int thisColor = this.getSkinColor();

    if (partner instanceof BactrianCamelEntity partnerCamel) {
      BactrianCamelEntity baby = ModEntities.BACTRIAN_CAMEL.create(serverWorld);
      if (baby != null) {
        int blendedColor = CamelColorUtil.blendParentColors(thisColor, partnerCamel.getSkinColor());
        baby.setSkinColor(blendedColor);
        triggerBreedAdvancement(blendedColor);
      }
      return baby;
    }

    if (partner instanceof CamelEntity partnerCamel) {
      CamelEntity baby = EntityType.CAMEL.create(serverWorld);
      if (baby != null) {
        CamelColorAccessor partnerAccessor = (CamelColorAccessor) partnerCamel;
        int partnerColor = partnerAccessor.bactrian_brews$hasCustomColor()
            ? partnerAccessor.bactrian_brews$getSkinColor()
            : CamelColorUtil.CHAMOMILE; // baseline for a pure vanilla parent

        int blendedColor = CamelColorUtil.blendParentColors(thisColor, partnerColor);
        ((CamelColorAccessor) baby).bactrian_brews$setSkinColor(blendedColor);
        triggerBreedAdvancement(blendedColor);
      }
      return baby;
    }

    return null;
  }

  private void triggerBreedAdvancement(int blendedColor) {
    ServerPlayerEntity lovingPlayer = this.getLovingPlayer();
    if (lovingPlayer != null) {
      ModCriteria.BRED_COLOR_CAMEL.trigger(lovingPlayer, blendedColor);
    }
  }

  public boolean isSitting() {
    return this.getPose() == net.minecraft.entity.EntityPose.SITTING;
  }

  public void setSitting(boolean sitting) {
    if (sitting) {
      this.setPose(net.minecraft.entity.EntityPose.SITTING);
    } else {
      this.setPose(net.minecraft.entity.EntityPose.STANDING);
    }
  }

  @Override
  protected net.minecraft.sound.SoundEvent getAmbientSound() {
    return this.isSitting() ? null : net.minecraft.sound.SoundEvents.ENTITY_CAMEL_AMBIENT;
  }

  @Override
  protected void playStepSound(net.minecraft.util.math.BlockPos pos, net.minecraft.block.BlockState state) {
    this.playSound(net.minecraft.sound.SoundEvents.ENTITY_CAMEL_STEP, 0.15F, 1.0F);
  }

  @Override
  public void startJumping(int heightTicks) {
    this.jumping = true;
    net.minecraft.util.math.Vec3d lookVec = this.getRotationVector();
    this.setVelocity(lookVec.x * 2.0, 0.25, lookVec.z * 2.0);
    this.velocityDirty = true;
    if (!this.getWorld().isClient()) {
      this.getWorld().playSound(null, this.getBlockPos(), net.minecraft.sound.SoundEvents.ENTITY_CAMEL_DASH, this.getSoundCategory(), 1.0F, 1.0F);
    }
  }

  @Override
  protected void playJumpSound() {
    this.playSound(net.minecraft.sound.SoundEvents.ENTITY_CAMEL_STEP, 0.4F, 1.0F);
  }

  @Override
  public boolean handleFallDamage(float fallDistance, float damageMultiplier, net.minecraft.entity.damage.DamageSource damageSource) {
    if (fallDistance > 1.0F) {
      this.playSound(net.minecraft.sound.SoundEvents.ENTITY_CAMEL_STEP, 0.4F, 1.0F);
    }
    return super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
  }

  @Override
  public void stopJumping() {
    this.jumping = false;
    if (!this.getWorld().isClient()) {
      this.getWorld().playSound(
          null,
          this.getBlockPos(),
          net.minecraft.sound.SoundEvents.ENTITY_CAMEL_STEP,
          this.getSoundCategory(),
          1.0F,
          1.0F
      );
    }
  }

  @Override
  public double getMountedHeightOffset() {
    return (double)this.getDimensions(this.getPose()).height * 0.6D;
  }

  @Override
  public void setCustomName(@Nullable Text name) {
    super.setCustomName(name);
    if (this.getWorld().isClient()) return;

    boolean isFrederick = name != null && name.getString().equals("Frederick");
    if (isFrederick) {
      if (!this.dataTracker.get(FREDERICK_COLOR_ROLLED)) {
        this.setSkinColor(CamelColorUtil.getRandomHexColor(this.random));
        this.dataTracker.set(FREDERICK_COLOR_ROLLED, true);
      }
    } else {
      this.dataTracker.set(FREDERICK_COLOR_ROLLED, false);
    }
  }
}
