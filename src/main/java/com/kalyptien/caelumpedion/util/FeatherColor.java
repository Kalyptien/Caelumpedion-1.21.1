package com.kalyptien.caelumpedion.util;

public enum FeatherColor {
    White("white", "???"),
    LightGray("light_gray", "???"),
    Gray("gray", "???"),
    Black("black", "???"),
    Brown("brown", "???"),
    Red("red", "???"),
    Orange("orange", "???"),
    Yellow("yellow", "???"),
    Lime("lime", "???"),
    Green("green", "???"),
    LightBlue("light_blue", "???"),
    Cyan("cyan", "???"),
    Blue("blue", "???"),
    Purple("purple", "???"),
    Magenta("magenta", "???"),
    Pink("pink", "???"),
    Special("special", "eat_my_pant");

    private final String colorName;
    private final String featherItemId;

    FeatherColor(String colorName, String featherItemId) {
        this.colorName = colorName;
        this.featherItemId = featherItemId;
    }

    public String getColorName() {
        return colorName;
    }

    public String getFeatherItemId() {
        return featherItemId;
    }
}
