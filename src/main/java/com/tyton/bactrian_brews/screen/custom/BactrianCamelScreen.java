package com.tyton.bactrian_brews.screen.custom;

import com.tyton.bactrian_brews.BactrianBrews;
import com.tyton.bactrian_brews.entity.custom.BactrianCamelEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BactrianCamelScreen extends HandledScreen<BactrianCamelScreenHandler> {

  private static final Identifier TEXTURE_CHEST = new Identifier(BactrianBrews.MOD_ID, "textures/gui/container/camel_inventory.png");
  private static final Identifier TEXTURE_NO_CHEST = new Identifier(BactrianBrews.MOD_ID, "textures/gui/container/camel_no_inventory.png");

  private final BactrianCamelEntity entity;
  private final Identifier texture;
  private float mouseX;
  private float mouseY;

  public BactrianCamelScreen(BactrianCamelScreenHandler handler, PlayerInventory inventory, Text title) {
    super(handler, inventory, title);
    this.entity = handler.getCamelEntity();

    if (handler.hasCamelStorage()) {
      this.texture = TEXTURE_CHEST;
      this.backgroundWidth = 176;
      this.backgroundHeight = 224;
      this.playerInventoryTitleY = 67;
    } else {
      this.texture = TEXTURE_NO_CHEST;
      this.backgroundWidth = 176;
      this.backgroundHeight = 166;
      this.playerInventoryTitleY = 74;
    }
  }

  @Override
  protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
    int i = (this.width - this.backgroundWidth) / 2;
    int j = (this.height - this.backgroundHeight) / 2;
    context.drawTexture(this.texture, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);

    int saddleSlotX = i + 98;
    int saddleSlotY = j + 18;
    context.fill(saddleSlotX - 1, saddleSlotY - 1, saddleSlotX + 17, saddleSlotY + 17, 0xFF8B8B8B);
    context.fill(saddleSlotX, saddleSlotY, saddleSlotX + 16, saddleSlotY + 16, 0xFF373737);

    int chestSlotX = i + 98;
    int chestSlotY = j + 36;
    context.fill(chestSlotX - 1, chestSlotY - 1, chestSlotX + 17, chestSlotY + 17, 0xFF8B8B8B);
    context.fill(chestSlotX, chestSlotY, chestSlotX + 16, chestSlotY + 16, 0xFF373737);

    int dyeSlotX = i + 98;
    int dyeSlotY = j + 54;
    context.fill(dyeSlotX - 1, dyeSlotY - 1, dyeSlotX + 17, dyeSlotY + 17, 0xFF8B8B8B);
    context.fill(dyeSlotX, dyeSlotY, dyeSlotX + 16, dyeSlotY + 16, 0xFF373737);

    if (this.entity != null) {
      InventoryScreen.drawEntity(context, i + 141, j + 60, 20,
          (float)(i + 141) - this.mouseX, (float)(j + 75 - 50) - this.mouseY, this.entity);
    }
  }

  @Override
  public void render(DrawContext context, int mouseX, int mouseY, float delta) {
    this.renderBackground(context);
    this.mouseX = (float) mouseX;
    this.mouseY = (float) mouseY;
    super.render(context, mouseX, mouseY, delta);
    this.drawMouseoverTooltip(context, mouseX, mouseY);
  }

  @Override
  protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
    super.drawMouseoverTooltip(context, x, y);

    if (this.focusedSlot != null && !this.focusedSlot.hasStack()) {
      Text label = switch (this.focusedSlot.id) {
        case 0 -> Text.translatable("gui.bactrian_brews.slot.saddle");
        case 1 -> Text.translatable("gui.bactrian_brews.slot.chest");
        case 2 -> Text.translatable("gui.bactrian_brews.slot.dye");
        default -> null;
      };
      if (label != null) {
        context.drawTooltip(this.textRenderer, label, x, y);
      }
    }
  }
}