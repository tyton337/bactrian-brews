package com.tyton.tameable_camels.advancement;

import com.tyton.tameable_camels.advancement.custom.BredColorCamelCriterion;
import com.tyton.tameable_camels.advancement.custom.NamedCamelFrederickCriterion;
import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {
  public static final NamedCamelFrederickCriterion NAMED_CAMEL_FREDERICK =
      Criteria.register(new NamedCamelFrederickCriterion());
  public static final BredColorCamelCriterion BRED_COLOR_CAMEL =
      Criteria.register(new BredColorCamelCriterion());

  public static void register() {
  }
}