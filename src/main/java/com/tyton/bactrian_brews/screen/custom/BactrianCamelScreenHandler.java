package com.tyton.bactrian_brews.screen.custom;

import com.tyton.bactrian_brews.entity.custom.BactrianCamelEntity;
import com.tyton.bactrian_brews.screen.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BactrianCamelScreenHandler extends ScreenHandler {

  private final Inventory camelInventory;
  private final BactrianCamelEntity camelEntity;
  private final PlayerEntity player;
  private final SimpleInventory chestDisplayInventory = new SimpleInventory(1);
  private final boolean hasChest;

  public BactrianCamelScreenHandler(int syncId, PlayerInventory playerInventory, Inventory camelInventory, BactrianCamelEntity camelEntity) {
    super(ModScreenHandlers.BACTRIAN_CAMEL_SCREEN_HANDLER, syncId);
    this.camelInventory = camelInventory;
    this.camelEntity = camelEntity;
    this.player = playerInventory.player;
    this.hasChest = camelEntity != null && camelEntity.hasChest();
    camelInventory.onOpen(playerInventory.player);

    if (this.hasChest) {
      this.chestDisplayInventory.setStack(0, new ItemStack(Items.CHEST));
    }

    this.addSlot(new Slot(camelInventory, 0, 98, 18) {
      @Override
      public boolean canInsert(ItemStack stack) {
        return stack.isEmpty() || stack.isOf(Items.SADDLE);
      }
    });
    this.addSlot(new Slot(this.chestDisplayInventory, 0, 98, 36) {
      @Override
      public boolean canInsert(ItemStack stack) {
        return camelEntity != null && !camelEntity.hasChest() && stack.isOf(Items.CHEST);
      }
      @Override
      public boolean canTakeItems(PlayerEntity player) {
        return false;
      }
      @Override
      public void markDirty() {
        super.markDirty();
        if (camelEntity != null && !this.getStack().isEmpty() && !camelEntity.hasChest()) {
          camelEntity.setHasChest(true);
          if (!camelEntity.getWorld().isClient()) {
            camelEntity.openInventory(BactrianCamelScreenHandler.this.player);
          }
        }
      }
    });
    this.addSlot(new Slot(camelInventory, 1, 98, 54) {
      @Override
      public boolean canInsert(ItemStack stack) {
        return stack.getItem() instanceof DyeItem;
      }

      @Override
      public void markDirty() {
        super.markDirty();
        if (camelEntity == null) return;
        ItemStack stack = this.getStack();
        if (stack.getItem() instanceof DyeItem dyeItem) {
          camelEntity.setDye(dyeItem.getColor().getName());
        } else if (stack.isEmpty()) {
          camelEntity.setDye("");
        }
      }
    });

    if (this.hasChest) {
      for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 9; col++) {
          int index = 2 + col + row * 9;
          this.addSlot(new Slot(camelInventory, index, 8 + col * 18, 77 + row * 18));
        }
      }
      for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 9; col++) {
          this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 142 + row * 18));
        }
      }
      for (int col = 0; col < 9; col++) {
        this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 200));
      }
    } else {
      for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 9; col++) {
          this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
        }
      }
      for (int col = 0; col < 9; col++) {
        this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
      }
    }
  }

  public boolean hasCamelStorage() {
    return this.hasChest;
  }

  public BactrianCamelEntity getCamelEntity() {
    return this.camelEntity;
  }

  @Override
  public boolean canUse(PlayerEntity player) {
    return this.camelInventory.canPlayerUse(player)
        && this.camelEntity != null
        && this.camelEntity.isAlive()
        && this.camelEntity.distanceTo(player) < 8.0F;
  }

  @Override
  public void onClosed(PlayerEntity player) {
    super.onClosed(player);
    this.camelInventory.onClose(player);
  }

  @Override
  public ItemStack quickMove(PlayerEntity player, int index) {
    ItemStack result = ItemStack.EMPTY;
    Slot slot = this.slots.get(index);
    if (slot == null || !slot.hasStack()) {
      return result;
    }

    ItemStack original = slot.getStack();
    result = original.copy();

    int storageStart = 3;
    int storageEnd = this.hasChest ? storageStart + 27 : storageStart;
    int playerInvStart = storageEnd;
    int playerInvEnd = playerInvStart + 27;
    int hotbarStart = playerInvEnd;
    int hotbarEnd = hotbarStart + 9;

    if (index < 3 || (index >= storageStart && index < storageEnd)) {
      if (!this.insertItem(original, playerInvStart, hotbarEnd, true)) {
        return ItemStack.EMPTY;
      }
    } else {
      if (original.isOf(Items.SADDLE) && !this.getSlot(0).hasStack()) {
        if (!this.insertItem(original, 0, 1, false)) {
          return ItemStack.EMPTY;
        }
      } else if (original.isOf(Items.CHEST) && !this.hasChest) {
        if (!this.insertItem(original, 1, 2, false)) {
          return ItemStack.EMPTY;
        }
      } else if (original.getItem() instanceof DyeItem && !this.getSlot(2).hasStack()) {
        if (!this.insertItem(original, 2, 3, false)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.hasChest || !this.insertItem(original, storageStart, storageEnd, false)) {
        return ItemStack.EMPTY;
      }

      if (index < playerInvEnd) {
        if (!this.insertItem(original, hotbarStart, hotbarEnd, false)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.insertItem(original, playerInvStart, playerInvEnd, false)) {
        return ItemStack.EMPTY;
      }
    }

    if (original.isEmpty()) {
      slot.setStack(ItemStack.EMPTY);
    } else {
      slot.markDirty();
    }
    return result;
  }
}