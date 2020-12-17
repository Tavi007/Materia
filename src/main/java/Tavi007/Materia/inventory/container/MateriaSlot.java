package Tavi007.Materia.inventory.container;

import javax.annotation.Nonnull;

import Tavi007.Materia.inventory.EquippingStationInventory;
import Tavi007.Materia.items.BaseMateria;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class MateriaSlot extends Slot {

	public MateriaSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
    	if (this.inventory instanceof EquippingStationInventory) {
    		//add more logic, when the MateriaTool has its own MateriaSlots.
            return stack.getItem() instanceof BaseMateria && !((EquippingStationInventory) this.inventory).hasEmptyMateriaToolSlot();
    	}
        return stack.getItem() instanceof BaseMateria;
    }

}
