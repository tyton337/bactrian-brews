package com.tyton.bactrian_brews.util;

import com.tyton.bactrian_brews.advancement.custom.ColorAdvancement;
import net.minecraft.util.math.random.Random;

import java.util.HashMap;
import java.util.Map;

public class CamelColorUtil {

  public static final int WHITE_MOCHA = 0xFFF2DE;
  public static final int CHAMOMILE = 0xFCCD7D;
  public static final int PUMPKIN_SPICE = 0xCD7056;
  public static final int TURKISH_COFFEE = 0x4B3322;
  public static final int HOJICHA = 0x222222;

  private static final int[] NATURAL_SPAWNS = {WHITE_MOCHA, CHAMOMILE, PUMPKIN_SPICE, TURKISH_COFFEE, HOJICHA};

  // Single source of truth: which two base colors each blend name comes from.
  // COLOR_ADVANCEMENTS and the name->hex lookup are both derived from this.
  private record BlendPair(String id, int colorA, int colorB) {}

  private static final BlendPair[] BLEND_PAIRS = new BlendPair[] {
      new BlendPair("matcha_latte",  WHITE_MOCHA, CHAMOMILE),
      new BlendPair("rooibos_chai",  WHITE_MOCHA, PUMPKIN_SPICE),
      new BlendPair("london_fog",    WHITE_MOCHA, TURKISH_COFFEE),
      new BlendPair("earl_grey",     WHITE_MOCHA, HOJICHA),
      new BlendPair("masala_chai",   CHAMOMILE, PUMPKIN_SPICE),
      new BlendPair("darjeeling",    CHAMOMILE, TURKISH_COFFEE),
      new BlendPair("oolong_gold",   CHAMOMILE, HOJICHA),
      new BlendPair("cafe_mocha",    PUMPKIN_SPICE, TURKISH_COFFEE),
      new BlendPair("french_roast",  PUMPKIN_SPICE, HOJICHA),
      new BlendPair("ristretto",     TURKISH_COFFEE, HOJICHA),
  };

  public static final ColorAdvancement[] COLOR_ADVANCEMENTS;
  private static final Map<String, Integer> BLEND_NAME_TO_COLOR;

  static {
    COLOR_ADVANCEMENTS = new ColorAdvancement[BLEND_PAIRS.length];
    BLEND_NAME_TO_COLOR = new HashMap<>();
    for (int i = 0; i < BLEND_PAIRS.length; i++) {
      BlendPair pair = BLEND_PAIRS[i];
      int blended = blendParentColors(pair.colorA(), pair.colorB());
      COLOR_ADVANCEMENTS[i] = new ColorAdvancement(pair.id(), blended);
      BLEND_NAME_TO_COLOR.put(pair.id(), blended);
    }
  }

  public static int getRandomNaturalColor(Random random) {
    return NATURAL_SPAWNS[random.nextInt(NATURAL_SPAWNS.length)];
  }

  public static int getRandomHexColor(Random random) {
    return random.nextInt(0x1000000);
  }

  public static int blendParentColors(int colorA, int colorB) {
    int r1 = (colorA >> 16) & 0xFF;
    int g1 = (colorA >> 8) & 0xFF;
    int b1 = colorA & 0xFF;
    int r2 = (colorB >> 16) & 0xFF;
    int g2 = (colorB >> 8) & 0xFF;
    int b2 = colorB & 0xFF;
    return (((r1 + r2) / 2) << 16) | (((g1 + g2) / 2) << 8) | ((b1 + b2) / 2);
  }

  public static int getColorFromName(String name) {
    if (name == null) return CHAMOMILE;
    String key = name.toLowerCase();
    return switch (key) {
      case "white_mocha" -> WHITE_MOCHA;
      case "chamomile" -> CHAMOMILE;
      case "pumpkin_spice" -> PUMPKIN_SPICE;
      case "turkish_coffee" -> TURKISH_COFFEE;
      case "hojicha" -> HOJICHA;
      default -> BLEND_NAME_TO_COLOR.getOrDefault(key, CHAMOMILE);
    };
  }
}