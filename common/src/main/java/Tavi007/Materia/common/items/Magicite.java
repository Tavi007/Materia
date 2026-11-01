package Tavi007.Materia.common.items;

import Tavi007.Materia.client.color.MagicColor;
import net.minecraft.world.item.Item;

// can be drawn from enemies.
// contains a lot of para magic, that can be bottled into a phial
// color should change depending on the content similar to potions
public class Magicite extends Item {

    private final String magic;
    private Integer color;

    public Magicite(Properties properties, String magic) {
        super(properties);
        this.magic = magic;
    }

    public int getColor() {
        if(color == null) {
            color = MagicColor.getColor(magic);
        }
        return color;
    }
}
