package Tavi007.Materia.inventory;

import java.util.ArrayList;

import Tavi007.Materia.inventory.container.EquippingStationContainer;
import Tavi007.Materia.items.BaseMateria;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.util.MateriaToolUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;

public class EquippingStationInventory implements IInventory {
	private final Container eventHandler;
	
	private final ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();
	private boolean hasTool;
	private int noTopMateriaSlots;
	private int noBotMateriaSlots;
	

	public EquippingStationInventory(EquippingStationContainer container) {
		stackList.add(ItemStack.EMPTY);
		hasTool = false;
		noTopMateriaSlots = 0;
		noBotMateriaSlots = 0;
		eventHandler = container;
	}

	@Override
	public void clear() {
		stackList.clear();
	}

	@Override
	public int getSizeInventory() {
		return 1 + noTopMateriaSlots + noBotMateriaSlots;
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
			eventHandler.onCraftMatrixChanged(this);
		}

		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(stackList, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index == 0) {
			setMateriaToolStack(stack);
		}
		else if (index>0 && index<getSizeInventory()) {
			stackList.set(index, stack);
		}
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return true;
	}

	
	public void setMateriaToolStack(ItemStack stack) {
		clear();
		
		hasTool = false;
		noTopMateriaSlots = 0;
		noBotMateriaSlots = 0;
		stackList.add(stack);
		if (stack.getItem() instanceof IMateriaTool) {
			IMateriaTool tool = (IMateriaTool) stack.getItem();
			hasTool = true;
			noTopMateriaSlots = MateriaToolUtil.getMateriaStacksFromSlots(tool.getTopSlots()).size();
			noBotMateriaSlots = MateriaToolUtil.getMateriaStacksFromSlots(tool.getBotSlots()).size();
			stackList.addAll(MateriaToolUtil.getMateriaStacksFromTool(tool));
		}
	}

	public void shiftClickAddMateria(ItemStack stack) {
		if (stack.getItem() instanceof BaseMateria)	{
			for(int id=1; id<this.getSizeInventory(); id++) {
				if (stackList.get(id).isEmpty()) {
					stackList.set(id, stack);
					return;
				}
			}
		}
	}
	
	public void removeMateria(int id) {
		if (id>1 && id<getSizeInventory()) {
			stackList.set(id, ItemStack.EMPTY);
		}
	}
	
	public boolean hasEmptyMateriaSlot() {
		//check possible MateriaSlots, if at least one is empty
		//might need some little tweak, once the number of slots depends on the tool
		for(int id=1; id<getSizeInventory(); id++) {
			if (stackList.get(id).isEmpty()) return true;
		}
		return false;
	}

	public boolean isMateriaToolSlotEmpty() {
		return !hasTool;
	}

	// this can be an empty stack!
	public ItemStack getMateriaToolStack() {
		return stackList.get(0);
	}
}
