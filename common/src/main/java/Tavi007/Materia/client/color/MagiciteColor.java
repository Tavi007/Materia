package Tavi007.Materia.client.color;

import Tavi007.Materia.common.items.Magicite;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class MagiciteColor implements ItemColor {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1 && stack.getItem() instanceof Magicite item) {
            return item.getColor();
        }
        return 0xFFFFFFFF;
    }
}
