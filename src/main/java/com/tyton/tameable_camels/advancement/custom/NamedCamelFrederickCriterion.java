package com.tyton.tameable_camels.advancement.custom;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class NamedCamelFrederickCriterion extends AbstractCriterion<NamedCamelFrederickCriterion.Conditions> {

  static final Identifier ID = new Identifier("tameable_camels", "named_camel_frederick");

  @Override
  public Identifier getId() {
    return ID;
  }

  @Override
  protected Conditions conditionsFromJson(JsonObject obj, LootContextPredicate playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
    return new Conditions(playerPredicate);
  }

  public void trigger(ServerPlayerEntity player) {
    this.trigger(player, conditions -> true);
  }

  public static class Conditions extends AbstractCriterionConditions {
    public Conditions(LootContextPredicate playerPredicate) {
      super(ID, playerPredicate);
    }
  }
}