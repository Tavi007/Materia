package Tavi007.Materia.inventory.container;

import javax.annotation.Nonnull;

import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MateriaToolContainerSlot extends SlotItemHandler {

	private final int slotIndex;
	
	public MateriaToolContainerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.slotIndex = index;
	}

    @Override
    public void putStack(@Nonnull ItemStack stack) {
		if (getItemHandler().isItemValid(slotIndex, stack)) {
			getItemHandler().insertItem(slotIndex, stack, false);
		}
    }
    
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return stack.getItem() instanceof IMateriaTool;
    }

}
