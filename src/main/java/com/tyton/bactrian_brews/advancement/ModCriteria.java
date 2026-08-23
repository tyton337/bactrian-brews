package com.tyton.bactrian_brews.advancement;

import com.tyton.bactrian_brews.BactrianBrews;
import com.tyton.bactrian_brews.advancement.custom.BredColorCamelCriterion;
import com.tyton.bactrian_brews.advancement.custom.NamedCamelFrederickCriterion;
import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {
  public static final NamedCamelFrederickCriterion NAMED_CAMEL_FREDERICK =
      Criteria.register(new NamedCamelFrederickCriterion());
  public static final BredColorCamelCriterion BRED_COLOR_CAMEL =
      Criteria.register(new BredColorCamelCriterion());

  public static void register() {
    BactrianBrews.LOGGER.info("Registering Mod Criteria for " + BactrianBrews.MOD_ID);
  }
}