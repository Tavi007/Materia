package Tavi007.Materia.common.inventory.slots;

import Tavi007.Materia.common.init.ModItems;
import Tavi007.Materia.common.items.MateriaItem;
import Tavi007.Materia.common.items.ParaMagicPhial;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ParaMagicPhialSlot extends Slot {

    public ParaMagicPhialSlot(
            Container container,
            int slot,
            int x,
            int y
    ) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return mayPlaceItem(stack);
    }

    public static boolean mayPlaceItem(ItemStack stack) {return stack.is(ModItems.PARA_MAGIC_PHIAL.get());}
}
