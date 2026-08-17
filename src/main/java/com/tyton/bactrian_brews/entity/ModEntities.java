package com.tyton.bactrian_brews.entity;

import com.tyton.bactrian_brews.BactrianBrews;
import com.tyton.bactrian_brews.entity.custom.BactrianCamelEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
  public static final EntityType<BactrianCamelEntity> BACTRIAN_CAMEL = Registry.register(Registries.ENTITY_TYPE,
      new Identifier(BactrianBrews.MOD_ID, "bactrian_camel"),
      FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BactrianCamelEntity::new)
          .dimensions(EntityDimensions.changing(1.7F, 2.375F)).build());
}
