package Tavi007.Materia.inventory;

import java.util.ArrayList;

import Tavi007.Materia.inventory.container.EquippingStationContainer;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.util.MateriaToolUtil;
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
				IMateriaTool tool = (IMateriaTool) getMateriaToolStack().getItem(); //the tool
				MateriaToolUtil.setMateriaStack(tool, ItemStack.EMPTY, slot);
			}
			else {
				// Materia got inserted
				IMateriaTool tool = (IMateriaTool) getMateriaToolStack().getItem(); //the tool
				MateriaToolUtil.setMateriaStack(tool, stack, slot);
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
				IMateriaTool tool = (IMateriaTool) stack.getItem();
				ArrayList<ItemStack> topStacks = MateriaToolUtil.getMateriaStacks(tool.getTopSlots());
				for(int i=0; i<topStacks.size(); i++) {
					stacks.set(i, topStacks.get(i));
				}
				ArrayList<ItemStack> botStacks = MateriaToolUtil.getMateriaStacks(tool.getBotSlots());
				for(int i=0; i<botStacks.size(); i++) {
					stacks.set(i+4, botStacks.get(i));
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
		IMateriaTool tool = (IMateriaTool) itemstack.getItem();
		int noSlots;
		if(slotIndex<4) {
			noSlots = MateriaToolUtil.getNumberOfSlots(tool.getTopSlots());
			if(slotIndex<noSlots) {
				return true;
			}
		}
		else {
			noSlots = MateriaToolUtil.getNumberOfSlots(tool.getBotSlots());
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
