package Tavi007.Materia.inventory;

import Tavi007.Materia.capabilities.toolslots.MateriaCollection;
import Tavi007.Materia.inventory.container.EquippingStationContainer;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.util.CapabilityHelper;
import Tavi007.Materia.util.MateriaToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class EquippingStationItemHandler extends ItemStackHandler {
	private final EquippingStationContainer container;

	public EquippingStationItemHandler(EquippingStationContainer container) {
		super(9);
		this.container = container;
	}
	
	@Override
	public void onContentsChanged(int slot) {
		if(slot>=0 && slot < 8) {
			// BaseMateria
			ItemStack stack = getStackInSlot(slot);
			if (stack.isEmpty()) {
				// Materia got removed
				CapabilityHelper.getMateriaCollection(getMateriaToolStack()).setStackInSlot(slot, ItemStack.EMPTY);
			}
			else {
				// Materia got inserted
				CapabilityHelper.getMateriaCollection(getMateriaToolStack()).setStackInSlot(slot, stack);
			}
			
		}
		else if(slot == 8) {
			// IMateriaTool
			ItemStack stack = getStackInSlot(slot);
			if (stack.isEmpty()) {
				// IMateriaTool got removed
				for(int i=0; i<8; i++) {
					stacks.set(i, ItemStack.EMPTY);
				}
			}
			else {
				// IMateriaTool got inserted
				MateriaCollection collection = CapabilityHelper.getMateriaCollection(stack);
				for(int i=0; i<collection.getSlots(); i++) {
					stacks.set(i, collection.getStackInSlot(i));
				}
			}
		}
		else {
			// Should not have happened
		}
	}


	@Override
	public int getSlotLimit(int slot) {
		return 1;
	}


	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return container.inventorySlots.get(slot).isItemValid(stack);
	}

	public boolean isMateriaSlotEnabled(int slotIndex) {
		ItemStack itemstack = container.getMateriaToolStack();
		if(itemstack.isEmpty()) {
			return false;
		}
		int noSlots;
		if(slotIndex<4) {
			noSlots = MateriaToolHelper.getNoCollectionSlot(((IMateriaTool) itemstack.getItem()).getTopCollectionSizes());
			if(slotIndex<noSlots) {
				return true;
			}
		}
		else {
			noSlots = MateriaToolHelper.getNoCollectionSlot(((IMateriaTool) itemstack.getItem()).getBotCollectionSizes());
			if(slotIndex-4<noSlots) {
				return true;
			}
		}
		return false;
	}
	
	public ItemStack getMateriaToolStack() {
		return stacks.get(8);
	}
}
