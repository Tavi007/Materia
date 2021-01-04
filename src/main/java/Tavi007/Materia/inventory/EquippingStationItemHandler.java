package Tavi007.Materia.inventory;

import java.util.ArrayList;

import Tavi007.Materia.inventory.container.EquippingStationContainer;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.util.MateriaToolUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class EquippingStationItemHandler implements IItemHandler {
	private final EquippingStationContainer container;

	private final ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();


	public EquippingStationItemHandler(EquippingStationContainer container) {
		for (int i=0; i<9; i++) {
			stackList.add(ItemStack.EMPTY);
		}
		this.container = container;
	}


	@Override
	public int getSlots() {
		return stackList.size();
	}


	@Override
	public ItemStack getStackInSlot(int slot) {
		return stackList.get(slot);
	}


	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		ItemStack stackInSlot = getStackInSlot(slot);
		if (ItemStack.areItemsEqual(stackInSlot, ItemStack.EMPTY)) {
			stackList.set(slot, stack);
		}
		else if (ItemStack.areItemsEqual(stackInSlot, stack) && !simulate) {

		}
		return stackInSlot;
	}


	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return getStackInSlot(slot).getStack().split(amount);
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

	public void setMateria(int slotIndex, ItemStack stack) {
		ItemStack itemstack = container.getMateriaToolStack();
		if(itemstack.isEmpty()) {
			return; //should not have happened
		}
		IMateriaTool tool = (IMateriaTool) itemstack.getItem();
		if(slotIndex<4) {

		}
		else {

		}
		
	}
}
