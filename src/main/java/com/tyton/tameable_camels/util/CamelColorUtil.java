package com.tyton.tameable_camels.util;

import com.tyton.tameable_camels.advancement.custom.ColorAdvancement;
import net.minecraft.util.math.random.Random;

public class CamelColorUtil {

  public static final int BEIGE = 0xFFF2DE;
  public static final int NORMAL = 0xFCCD7D;
  public static final int RED = 0xCD7056;
  public static final int DARK_BROWN = 0x4B3322;
  public static final int BLACK = 0x222222;

  public static final ColorAdvancement[] COLOR_ADVANCEMENTS = new ColorAdvancement[] {
      new ColorAdvancement("bred_beige_normal",      0xD5BD95),
      new ColorAdvancement("bred_beige_red",         0xC39986),
      new ColorAdvancement("bred_beige_dark_brown",  0x998776),
      new ColorAdvancement("bred_beige_black",       0x857F76),
      new ColorAdvancement("bred_normal_red",        0xB07A51),
      new ColorAdvancement("bred_normal_dark_brown", 0x866841),
      new ColorAdvancement("bred_normal_black",      0x726041),
      new ColorAdvancement("bred_red_dark_brown",    0x744432),
      new ColorAdvancement("bred_red_black",         0x603C32),
      new ColorAdvancement("bred_dark_brown_black",  0x362A22),
  };
  // Lookup array for random world spawning pool selections
  private static final int[] NATURAL_SPAWNS = { BEIGE, NORMAL, RED, DARK_BROWN, BLACK };

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
    if (name == null) return NORMAL;

    return switch (name.toLowerCase()) {
      case "beige", "light_beige", "lightbeige" -> BEIGE;
      case "normal", "default" -> NORMAL;
      case "red", "reddish" -> RED;
      case "dark_brown", "darkbrown", "brown" -> DARK_BROWN;
      case "black", "charcoal" -> BLACK;
      default -> NORMAL;
    };
  }
}
