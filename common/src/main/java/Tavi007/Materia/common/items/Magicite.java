package Tavi007.Materia.common.items;

import Tavi007.Materia.client.color.MagicColor;
import net.minecraft.world.item.Item;

// drops from crystal ores
// a lot of para magic can be extracted from these (balanced by recipes)
// this opens up to level up materia from mining
// color depends on the magic
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
