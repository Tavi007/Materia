package Tavi007.Materia.inventory;

import java.util.ArrayList;

import Tavi007.Materia.inventory.container.EquippingStationContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class EquippingStationItemHandler implements IItemHandler {
	private final EquippingStationContainer container;
	
	private final ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();
	

	public EquippingStationItemHandler(EquippingStationContainer container) {
		for (int i=0; i<8; i++) {
			stackList.add(ItemStack.EMPTY);
		}
		this.container = container;
	}


	@Override
	public int getSlots() {
		return 0;
	}


	@Override
	public ItemStack getStackInSlot(int slot) {
		return null;
	}


	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		return null;
	}


	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return null;
	}


	@Override
	public int getSlotLimit(int slot) {
		return 0;
	}


	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return false;
	}
}
