package com.kalyptien.caelumpedion.util;

public enum FeatherColor {
    White("white", "minecraft:feather", 0),
    LightGray("light_gray", "caelumpedion:light_gray_feather", 0xFFababab),
    Gray("gray", "caelumpedion:gray_feather", 0xFF595959),
    Black("black", "caelumpedion:black_feather", 0xFF0a0a0a),
    Brown("brown", "caelumpedion:brown_feather", 0xFF2b1f14),
    Red("red", "caelumpedion:red_feather", 0xFFdb1212),
    Orange("orange", "caelumpedion:orange_feather", 0xFFde8614),
    Yellow("yellow", "caelumpedion:yellow_feather", 0xFFe3e014),
    Lime("lime", "caelumpedion:lime_feather", 0xFF8fe014),
    Green("green", "caelumpedion:green_feather", 0xFF36e014),
    LightBlue("light_blue", "caelumpedion:light_blue_feather", 0xFF18dbd5),
    Cyan("cyan", "caelumpedion:cyan_feather", 0xFF1e97d4),
    Blue("blue", "caelumpedion:blue_feather", 0xFF1932d4),
    Purple("purple", "caelumpedion:purple_feather", 0xFF781fd1),
    Magenta("magenta", "caelumpedion:magenta_feather", 0xFFc71eb9),
    Pink("pink", "caelumpedion:pink_feather", 0xFFe37fb8),
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
