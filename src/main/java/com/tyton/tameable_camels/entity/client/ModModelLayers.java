package com.tyton.tameable_camels.entity.client;

import com.tyton.tameable_camels.TameableCamels;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
  public static final EntityModelLayer BACTRIAN_CAMEL =
      new EntityModelLayer(new Identifier(TameableCamels.MOD_ID, "bactrian_camel"), "main");
  public static final EntityModelLayer BACTRIAN_CAMEL_SADDLE =
      new EntityModelLayer(new Identifier(TameableCamels.MOD_ID, "bactrian_camel_saddle"), "main");
}
