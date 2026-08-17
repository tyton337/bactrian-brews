package com.tyton.bactrian_brews.entity;

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
    addSpawns();
  }
}
