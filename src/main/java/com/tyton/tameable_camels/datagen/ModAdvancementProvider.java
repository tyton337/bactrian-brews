package com.tyton.tameable_camels.datagen;

import com.tyton.tameable_camels.advancement.custom.BredColorCamelCriterion;
import com.tyton.tameable_camels.advancement.custom.ColorAdvancement;
import com.tyton.tameable_camels.advancement.custom.NamedCamelFrederickCriterion;
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

import static com.tyton.tameable_camels.util.CamelColorUtil.COLOR_ADVANCEMENTS;

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
            new ItemStack(Items.NAME_TAG),
            Text.translatable("advancements.tameable_camels.root.title"),
            Text.translatable("advancements.tameable_camels.root.description"),
            new Identifier("tameable_camels", "textures/gui/advancements/backgrounds/camels.png"),
            AdvancementFrame.TASK,
            true,
            true,
            false
        )
        .criterion("bred_camel", rootCriterion)
        .build(consumer, "tameable_camels:root");

    NamedCamelFrederickCriterion.Conditions frederickConditions =
        new NamedCamelFrederickCriterion.Conditions(LootContextPredicate.EMPTY);
    AdvancementCriterion frederickCriterion = new AdvancementCriterion(frederickConditions);

    Advancement.Builder.create()
        .parent(tabRoot)
        .display(
            new ItemStack(Items.NAME_TAG),
            Text.translatable("advancements.tameable_camels.named_camel_frederick.title"),
            Text.translatable("advancements.tameable_camels.named_camel_frederick.description"),
            null,
            AdvancementFrame.GOAL,
            true,
            true,
            false
        )
        .criterion("named_frederick", frederickCriterion)
        .build(consumer, "tameable_camels:husbandry/named_camel_frederick");

    for (ColorAdvancement colorAdv : COLOR_ADVANCEMENTS) {
      BredColorCamelCriterion.Conditions conditions =
          new BredColorCamelCriterion.Conditions(LootContextPredicate.EMPTY, colorAdv.targetColor());
      AdvancementCriterion criterion = new AdvancementCriterion(conditions);

      Advancement.Builder.create()
          .parent(tabRoot)
          .display(
              new ItemStack(Items.NAME_TAG),
              Text.translatable("advancements.tameable_camels." + colorAdv.id() + ".title"),
              Text.translatable("advancements.tameable_camels." + colorAdv.id() + ".description"),
              null,
              AdvancementFrame.TASK,
              true,
              true,
              false
          )
          .criterion("bred_color_camel", criterion)
          .build(consumer, "tameable_camels:husbandry/" + colorAdv.id());
    }
  }
}
