package com.tyton.bactrian_brews.item;

import com.tyton.bactrian_brews.BactrianBrews;
import com.tyton.bactrian_brews.advancement.custom.ColorAdvancement;
import com.tyton.bactrian_brews.entity.ModEntities;
import com.tyton.bactrian_brews.util.CamelColorUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ModItems {

  public static final Item ICON_ROOT = registerIcon("icon_root");

  public static final Map<String, Item> COLOR_ICONS = new HashMap<>();

  public static final Item BACTRIAN_CAMEL_SPAWN_EGG = registerItem("bactrian_camel_spawn_egg",
      new SpawnEggItem(ModEntities.BACTRIAN_CAMEL, 0xFCCD7D, 0x4B3322, new FabricItemSettings()));

  static {
    for (ColorAdvancement colorAdv : CamelColorUtil.COLOR_ADVANCEMENTS) {
      COLOR_ICONS.put(colorAdv.id(), registerIcon("icon_" + colorAdv.id()));
    }
  }

  private static Item registerIcon(String name) {
    Identifier id = new Identifier(BactrianBrews.MOD_ID, name);
    Item item = new Item(new Item.Settings());
    return Registry.register(Registries.ITEM, id, item);
  }

  private static Item registerItem(String name, Item item) {
    return Registry.register(Registries.ITEM, new Identifier(BactrianBrews.MOD_ID, name), item);
  }

  private static void addItemsToSpawnEggItemGroup(FabricItemGroupEntries entries) {
    entries.add(BACTRIAN_CAMEL_SPAWN_EGG);
  }

  public static void register() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(ModItems::addItemsToSpawnEggItemGroup);
  }
}