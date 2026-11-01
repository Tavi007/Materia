package Tavi007.Materia.common.items;

import Tavi007.Materia.client.color.MagicColor;
import net.minecraft.world.item.Item;

// can be used in the incubator to level up materia
// comes in various sizes and contains pure (used for all materia) or materia specific para magic.
// can be drawn from mobs. Use a tool on an enemy and then stay close to them (risk/reward)
// color change depending on the magic
public class ParaMagicPhial extends Item {

    private final String magic;
    private Integer color;

    public ParaMagicPhial(Properties properties, String magic) {
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
