package com.tyton.tameable_camels.entity;

import com.tyton.tameable_camels.TameableCamels;
import com.tyton.tameable_camels.entity.custom.BactrianCamelEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
  public static final EntityType<BactrianCamelEntity> BACTRIAN_CAMEL = Registry.register(Registries.ENTITY_TYPE,
      new Identifier(TameableCamels.MOD_ID, "bactrian_camel"),
      FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BactrianCamelEntity::new)
          .dimensions(EntityDimensions.changing(1.7F, 2.375F)).build());
}
