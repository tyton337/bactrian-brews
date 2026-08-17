package com.tyton.bactrian_brews.datagen;

import com.tyton.bactrian_brews.advancement.custom.BredColorCamelCriterion;
import com.tyton.bactrian_brews.advancement.custom.ColorAdvancement;
import com.tyton.bactrian_brews.advancement.custom.NamedCamelFrederickCriterion;
import com.tyton.bactrian_brews.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

import static com.tyton.bactrian_brews.util.CamelColorUtil.COLOR_ADVANCEMENTS;

public class ModAdvancementProvider extends FabricAdvancementProvider {

  public ModAdvancementProvider(FabricDataOutput output) {
    super(output);
  }

  @Override
  public void generateAdvancement(Consumer<Advancement> consumer) {
    BredColorCamelCriterion.Conditions rootConditions =
        new BredColorCamelCriterion.Conditions(LootContextPredicate.EMPTY, null);
    AdvancementCriterion rootCriterion = new AdvancementCriterion(rootConditions);

    Advancement tabRoot = Advancement.Builder.create()
        .display(
            new ItemStack(ModItems.ICON_ROOT),
            Text.translatable("advancements.bactrian_brews.root.title"),
            Text.translatable("advancements.bactrian_brews.root.description"),
            new Identifier("bactrian_brews", "textures/gui/advancements/backgrounds/bactrian_brews.png"),
            AdvancementFrame.TASK,
            true,
            true,
            false
        )
        .criterion("bred_camel", rootCriterion)
        .build(consumer, "bactrian_brews:root");

    NamedCamelFrederickCriterion.Conditions frederickConditions =
        new NamedCamelFrederickCriterion.Conditions(LootContextPredicate.EMPTY);
    AdvancementCriterion frederickCriterion = new AdvancementCriterion(frederickConditions);

    Advancement.Builder.create()
        .parent(tabRoot)
        .display(
            new ItemStack(Items.NAME_TAG),
            Text.translatable("advancements.bactrian_brews.named_camel_frederick.title"),
            Text.translatable("advancements.bactrian_brews.named_camel_frederick.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            true
        )
        .criterion("named_frederick", frederickCriterion)
        .build(consumer, "bactrian_brews:husbandry/named_camel_frederick");

    for (ColorAdvancement colorAdv : COLOR_ADVANCEMENTS) {
      BredColorCamelCriterion.Conditions conditions =
          new BredColorCamelCriterion.Conditions(LootContextPredicate.EMPTY, colorAdv.targetColor());
      AdvancementCriterion criterion = new AdvancementCriterion(conditions);

      Advancement.Builder.create()
          .parent(tabRoot)
          .display(
              new ItemStack(ModItems.COLOR_ICONS.get(colorAdv.id())),
              Text.translatable("advancements.bactrian_brews." + colorAdv.id() + ".title"),
              Text.translatable("advancements.bactrian_brews." + colorAdv.id() + ".description"),
              null,
              AdvancementFrame.TASK,
              true,
              true,
              false
          )
          .criterion("bred_color_camel", criterion)
          .build(consumer, "bactrian_brews:husbandry/" + colorAdv.id());
    }
  }
}
