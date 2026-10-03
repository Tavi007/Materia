package Tavi007.Materia.common.inventory.slots;

import Tavi007.Materia.common.items.MateriaItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MateriaItemSlot extends Slot {

    public MateriaItemSlot(
            Container container,
            int slot,
            int x,
            int y
    ) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof MateriaItem;
    }
}
