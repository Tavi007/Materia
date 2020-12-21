package Tavi007.Materia.inventory.container;

import javax.annotation.Nonnull;

import Tavi007.Materia.inventory.EquippingStationItemHandler;
import Tavi007.Materia.items.BaseMateria;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MateriaContainerSlot extends SlotItemHandler {

	public MateriaContainerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, int slotIndex) {
		super(itemHandler, index, xPosition, yPosition);
		this.slotIndex = slotIndex;
	}

	private final int slotIndex;

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
    	if (getItemHandler() instanceof EquippingStationItemHandler) {
    		return stack.getItem() instanceof BaseMateria && ((EquippingStationItemHandler) getItemHandler()).isMateriaSlotEmpty(slotIndex-1);
    	}
        return stack.getItem() instanceof BaseMateria;
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isEnabled() {
       return true;
    }
}
