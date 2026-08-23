package com.tyton.bactrian_brews.screen;

import com.tyton.bactrian_brews.BactrianBrews;
import com.tyton.bactrian_brews.entity.custom.BactrianCamelEntity;
import com.tyton.bactrian_brews.screen.custom.BactrianCamelScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
  public static final ScreenHandlerType<BactrianCamelScreenHandler> BACTRIAN_CAMEL_SCREEN_HANDLER =
      Registry.register(
          Registries.SCREEN_HANDLER,
          new Identifier(BactrianBrews.MOD_ID, "bactrian_camel"),
          new ExtendedScreenHandlerType<>((syncId, playerInventory, buf) -> {
            int entityId = buf.readVarInt();
            Entity entity = playerInventory.player.getWorld().getEntityById(entityId);
            if (!(entity instanceof BactrianCamelEntity camel)) {
              return null;
            }
            return new BactrianCamelScreenHandler(syncId, playerInventory, camel.getItems(), camel);
          })
      );

  public static void register() {
  }
}