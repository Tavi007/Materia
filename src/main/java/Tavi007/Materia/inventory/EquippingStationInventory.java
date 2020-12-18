package Tavi007.Materia.inventory;

import java.util.ArrayList;

import Tavi007.Materia.inventory.container.EquippingStationContainer;
import Tavi007.Materia.items.BaseMateria;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;

public class EquippingStationInventory implements IInventory {
	private final Container eventHandler;
	
	private final ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();
	private boolean hasTool;
	private int noTopMateria;
	private int noBotMateria;
	

	public EquippingStationInventory(EquippingStationContainer container) {
		stackList.add(ItemStack.EMPTY);
		hasTool = false;
		noTopMateria = 0;
		noBotMateria = 0;
		eventHandler = container;
		
//		if(toolStack.isEmpty()) {
//		}
//		else {
//			hasTool = true;
//			ArrayList<ItemStack> materiaStacks = MateriaToolUtil.getMateriaStacksFromSlots( ((IMateriaTool) stackList.get(0).getItem()).getTopSlots() );
//			stackList.addAll(materiaStacks);
//			noTopMateria = materiaStacks.size();
//			materiaStacks = MateriaToolUtil.getMateriaStacksFromSlots( ((IMateriaTool) stackList.get(0).getItem()).getBotSlots() );
//			noBotMateria = materiaStacks.size();
//			stackList.addAll(materiaStacks);
//		}
		
	}

	@Override
	public void clear() {
		stackList.clear();
	}

	@Override
	public int getSizeInventory() {
		return 1 + noTopMateria + noBotMateria;
	}

	@Override
	public boolean isEmpty() {
		return stackList.isEmpty();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < getSizeInventory()) {
			return stackList.get(index);
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemstack = ItemStackHelper.getAndSplit(stackList, index, count);
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

	public boolean hasEmptyMateriaSlot() {
		//check possible MateriaSlots, if at least one is empty
		//might need some little tweak, once the number of slots depends on the tool
		for(int id=1; id<this.getSizeInventory(); id++) {
			if (this.stackList.get(id).isEmpty()) return true;
		}
		return false;
	}

	public boolean hasEmptyMateriaToolSlot() {
		return !hasTool;
	}

	public void addMateria(ItemStack stack) {
		if (stack.getItem() instanceof BaseMateria)	{
			for(int id=1; id<this.getSizeInventory(); id++) {
				if (this.stackList.get(id).isEmpty()) {
					this.stackList.set(id, stack);
					return;
				}
			}
		}
	}

	public void setTool(ItemStack stack) {
		this.stackList.set(0, stack);
	}

}
