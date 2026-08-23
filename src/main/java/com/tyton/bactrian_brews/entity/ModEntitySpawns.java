package com.tyton.bactrian_brews.entity;

import com.tyton.bactrian_brews.BactrianBrews;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.tag.BiomeTags;

public class ModEntitySpawns {
  public static void addSpawns() {
    BiomeModifications.addSpawn(
        BiomeSelectors.tag(BiomeTags.IS_BADLANDS), SpawnGroup.CREATURE,
        ModEntities.BACTRIAN_CAMEL, 5, 1, 2);
  }

  public static void register() {
    BactrianBrews.LOGGER.info("Registering ModEntitySpawns for " + BactrianBrews.MOD_ID);
    addSpawns();
  }
}
