package com.tyton.bactrian_brews.datagen;

import com.tyton.bactrian_brews.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {

  public ModModelProvider(FabricDataOutput output) {
    super(output);
  }

  @Override
  public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
  }

  @Override
  public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    itemModelGenerator.register(ModItems.ICON_ROOT, Models.GENERATED);

    for (Item iconItem : ModItems.COLOR_ICONS.values()) {
      itemModelGenerator.register(iconItem, Models.GENERATED);
    }
    itemModelGenerator.register(ModItems.BACTRIAN_CAMEL_SPAWN_EGG,
        new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty()));
  }
}