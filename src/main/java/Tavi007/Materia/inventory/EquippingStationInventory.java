package Tavi007.Materia.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class EquippingStationInventory implements IInventory {
	   private final NonNullList<ItemStack> stackList;
	   private final Container eventHandler;

	public EquippingStationInventory(Container eventHandler) { 
		this.stackList = NonNullList.withSize(9, ItemStack.EMPTY);
		this.eventHandler = eventHandler;
	}
	
	@Override
	public void clear() {
	      this.stackList.clear();
	}

	@Override
	public int getSizeInventory() {
		return stackList.size();
	}

	@Override
	public boolean isEmpty() {
		return stackList.isEmpty();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
	      return index >= this.getSizeInventory() ? ItemStack.EMPTY : this.stackList.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
	      ItemStack itemstack = ItemStackHelper.getAndSplit(this.stackList, index, count);
	      if (!itemstack.isEmpty()) {
	         this.eventHandler.onCraftMatrixChanged(this);
	      }

	      return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
	      return ItemStackHelper.getAndRemove(this.stackList, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index>=0 && index<this.getSizeInventory()) {
			this.stackList.set(index, stack);
			this.eventHandler.onCraftMatrixChanged(this);
		}
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
	      return true;
	}

}
