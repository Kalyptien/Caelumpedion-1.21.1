package com.kalyptien.caelumpedion.util;

public enum FeatherColor {
    White("white", "minecraft:feather", 0),
    LightGray("light_gray", "caelumpedion:light_gray_feather", 0xFFababab),
    Gray("gray", "caelumpedion:gray_feather", 0xFF636363),
    Black("black", "caelumpedion:black_feather", 0xFF2e2e2e),
    Brown("brown", "caelumpedion:brown_feather", 0xFF7d634b),
    Red("red", "caelumpedion:red_feather", 0xFFde3c3c),
    Orange("orange", "caelumpedion:orange_feather", 0xFFd9a25b),
    Yellow("yellow", "caelumpedion:yellow_feather", 0xFFe6e48e),
    Lime("lime", "caelumpedion:lime_feather", 0xFFabe356),
    Green("green", "caelumpedion:green_feather", 0xFF60d15c),
    LightBlue("light_blue", "caelumpedion:light_blue_feather", 0xFF71d9d6),
    Cyan("cyan", "caelumpedion:cyan_feather", 0xFF5caad1),
    Blue("blue", "caelumpedion:blue_feather", 0xFF5d6bc9),
    Purple("purple", "caelumpedion:purple_feather", 0xFF9e73c9),
    Magenta("magenta", "caelumpedion:magenta_feather", 0xFFc24fb8),
    Pink("pink", "caelumpedion:pink_feather", 0xFFdeabc8),
    Special("special", "caelumpedion:glitch_feather", 0);

    private final String colorName;
    private final String featherItemId;
    private final int color;

    FeatherColor(String colorName, String featherItemId, int color) {
        this.colorName = colorName;
        this.featherItemId = featherItemId;
        this.color = color;
    }

    public String getColorName() {
        return colorName;
    }

    public String getFeatherItemId() {
        return featherItemId;
    }

    public int getColor(){
        return color;
    }
}
