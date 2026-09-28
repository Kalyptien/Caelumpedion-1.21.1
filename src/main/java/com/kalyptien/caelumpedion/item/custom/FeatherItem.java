package com.kalyptien.caelumpedion.item.custom;

import com.kalyptien.caelumpedion.util.FeatherColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FeatherItem extends Item {
    private int colorVariant;

    public FeatherItem(Item.Properties properties, FeatherColor variant) {
        super(properties);
        this.colorVariant = variant.getColor();
    }

    public int getColor() {
        return this.colorVariant;
    }

    public static int getFeatherColor(ItemStack stack, int index) {
        if (index != 0) return -1;
        return ((FeatherItem) stack.getItem()).getColor();
    }
}
