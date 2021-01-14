package Tavi007.Materia.inventory;

import Tavi007.Materia.inventory.container.EquippingStationContainer;
import Tavi007.Materia.util.CapabilityHelper;
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
				CapabilityHelper.getMateriaCollection(getMateriaToolStack()).setMateriaStack(ItemStack.EMPTY, slot);
			}
			else {
				// Materia got inserted
				CapabilityHelper.getMateriaCollection(getMateriaToolStack()).setMateriaStack(stack, slot);
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
				ItemStack[] stackArray = CapabilityHelper.getMateriaCollection(stack).getStacks();
				for(int i=0; i<stackArray.length; i++) {
					stacks.set(i, stackArray[i]);
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
			noSlots = CapabilityHelper.getMateriaCollection(itemstack).getNoTopSlots();
			if(slotIndex<noSlots) {
				return true;
			}
		}
		else {
			noSlots = CapabilityHelper.getMateriaCollection(itemstack).getNoBotSlots();
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
