package com.tyton.bactrian_brews.entity.client;

import com.tyton.bactrian_brews.BactrianBrews;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
  public static final EntityModelLayer BACTRIAN_CAMEL =
      new EntityModelLayer(new Identifier(BactrianBrews.MOD_ID, "bactrian_camel"), "main");
  public static final EntityModelLayer BACTRIAN_CAMEL_SADDLE =
      new EntityModelLayer(new Identifier(BactrianBrews.MOD_ID, "bactrian_camel_saddle"), "main");
}
