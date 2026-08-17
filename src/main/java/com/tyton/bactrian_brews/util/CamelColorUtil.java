package com.tyton.bactrian_brews.util;

import com.tyton.bactrian_brews.advancement.custom.ColorAdvancement;
import net.minecraft.util.math.random.Random;

public class CamelColorUtil {

  public static final int WHITE_MOCHA = 0xFFF2DE;
  public static final int CHAMOMILE = 0xFCCD7D;
  public static final int PUMPKIN_SPICE = 0xCD7056;
  public static final int TURKISH_COFFEE = 0x4B3322;
  public static final int HOJICHA = 0x222222;

  public static final ColorAdvancement[] COLOR_ADVANCEMENTS = new ColorAdvancement[] {
      new ColorAdvancement("matcha_latte",  0xFDDFAD), // white_mocha + chamomile
      new ColorAdvancement("rooibos_chai",  0xE6B19A), // white_mocha + pumpkin_spice
      new ColorAdvancement("london_fog",    0xA59280), // white_mocha + turkish_coffee
      new ColorAdvancement("earl_grey",     0x908A80), // white_mocha + hojicha
      new ColorAdvancement("masala_chai",   0xE49E69), // chamomile + pumpkin_spice
      new ColorAdvancement("darjeeling",    0xA3804F), // chamomile + turkish_coffee
      new ColorAdvancement("oolong_gold",   0x8F774F), // chamomile + hojicha
      new ColorAdvancement("cafe_mocha",    0x8C513C), // pumpkin_spice + turkish_coffee
      new ColorAdvancement("french_roast",  0x77493C), // pumpkin_spice + hojicha
      new ColorAdvancement("ristretto",     0x362A22), // turkish_coffee + hojicha
  };

  // Lookup array for random world spawning pool selections
  private static final int[] NATURAL_SPAWNS = {WHITE_MOCHA, CHAMOMILE, PUMPKIN_SPICE, TURKISH_COFFEE, HOJICHA};

  /**
   * Grabs a completely random color integer index from the 5 natural variant pools.
   */
  public static int getRandomNaturalColor(Random random) {
    return NATURAL_SPAWNS[random.nextInt(NATURAL_SPAWNS.length)];
  }

  public static int getRandomHexColor(Random random) {
    return random.nextInt(0x1000000); // 0x000000 - 0xFFFFFF inclusive
  }

  /**
   * Blends the hex colors of Parent A and Parent B via 50/50 linear RGB bitwise averaging.
   */
  public static int blendParentColors(int colorA, int colorB) {
    int r1 = (colorA >> 16) & 0xFF;
    int g1 = (colorA >> 8) & 0xFF;
    int b1 = colorA & 0xFF;
    int r2 = (colorB >> 16) & 0xFF;
    int g2 = (colorB >> 8) & 0xFF;
    int b2 = colorB & 0xFF;
    int mixedRed = (r1 + r2) / 2;
    int mixedGreen = (g1 + g2) / 2;
    int mixedBlue = (b1 + b2) / 2;

    return (mixedRed << 16) | (mixedGreen << 8) | mixedBlue;
  }

  public static int getColorFromName(String name) {
    if (name == null) return CHAMOMILE;

    return switch (name.toLowerCase()) {
      case "white_mocha" -> WHITE_MOCHA;
      case "chamomile" -> CHAMOMILE;
      case "pumpkin_spice" -> PUMPKIN_SPICE;
      case "turkish_coffee" -> TURKISH_COFFEE;
      case "hojicha" -> HOJICHA;
      case "matcha_latte" -> 0xFDDFAD;
      case "rooibos_chai" -> 0xE6B19A;
      case "london_fog" -> 0xA59280;
      case "earl_grey" -> 0x908A80;
      case "masala_chai" -> 0xE49E69;
      case "darjeeling" -> 0xA3804F;
      case "oolong_gold" -> 0x8F774F;
      case "café_mocha", "cafe_mocha" -> 0x8C513C;
      case "french_roast" -> 0x77493C;
      case "ristretto" -> 0x362A22;
      default -> CHAMOMILE;
    };
  }
}
