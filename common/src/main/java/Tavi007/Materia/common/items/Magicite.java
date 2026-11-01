package Tavi007.Materia.common.items;

import net.minecraft.world.item.Item;

// can be drawn from enemies.
// contains a lot of para magic, that can be bottled into a phial
// color should change depending on the content similar to potions
public class Magicite extends Item {

    private final int color;

    public Magicite(Properties properties, int color) {
        super(properties);
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
