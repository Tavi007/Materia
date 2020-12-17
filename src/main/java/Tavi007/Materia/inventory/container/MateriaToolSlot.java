package Tavi007.Materia.inventory.container;

import javax.annotation.Nonnull;

import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class MateriaToolSlot extends Slot {

	public MateriaToolSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return stack.getItem() instanceof IMateriaTool;
    }

}
