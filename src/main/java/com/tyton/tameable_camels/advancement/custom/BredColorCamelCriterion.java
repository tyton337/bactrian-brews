package com.tyton.tameable_camels.advancement.custom;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class BredColorCamelCriterion extends AbstractCriterion<BredColorCamelCriterion.Conditions> {

  static final Identifier ID = new Identifier("tameable_camels", "bred_color_camel");

  @Override
  public Identifier getId() {
    return ID;
  }

  @Override
  protected Conditions conditionsFromJson(JsonObject obj, LootContextPredicate playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
    Integer targetColor = obj.has("color") ? Integer.parseInt(JsonHelper.getString(obj, "color"), 16) : null;
    return new Conditions(playerPredicate, targetColor);
  }

  public void trigger(ServerPlayerEntity player, int bredColor) {
    this.trigger(player, conditions -> conditions.matches(bredColor));
  }

  public static class Conditions extends AbstractCriterionConditions {
    private final Integer targetColor; // null = matches any bred color

    public Conditions(LootContextPredicate playerPredicate, Integer targetColor) {
      super(ID, playerPredicate);
      this.targetColor = targetColor;
    }

    public boolean matches(int bredColor) {
      return this.targetColor == null || bredColor == this.targetColor;
    }
  }
}