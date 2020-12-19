package Tavi007.Materia.inventory.container;

import javax.annotation.Nonnull;

import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.ContainerTypeList;
import Tavi007.Materia.inventory.EquippingStationInventory;
import Tavi007.Materia.items.BaseMateria;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.items.MateriaToolSlotCollection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EquippingStationContainer extends Container {

	private final IWorldPosCallable canInteractWithCallable;
	private final EquippingStationInventory stationInventory = new EquippingStationInventory(this);

	private final int noPlayerInventorySlots = 36;

	public EquippingStationContainer(final int windowId, final PlayerInventory playerInventory, final World world, final BlockPos pos) {
		super(ContainerTypeList.EQUIPPING_STATION.get(), windowId);
		this.canInteractWithCallable = IWorldPosCallable.of(world, pos);

		int startX = 8;
		int startY = 84;
		int slotSizePlus2 = 18;
		// Hotbar (Id 0-8)
		for (int column = 0; column < 9; column++) {
			addSlot(new Slot(playerInventory, column, startX + (column * slotSizePlus2), 142));
		}

		// Player Inventory (Id 9-35)
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 9; column++) {
				addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
			}
		}

		// MateriaToolSlot (Id 36)
		addSlot(new MateriaToolContainerSlot(stationInventory, 0, 8, 33));
		//MateriaSlots will be added on the fly, depending on the tool.
	}

	public EquippingStationContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, playerInventory.player.world, new BlockPos(playerInventory.player.getPositionVec()) );
	}







	@Override
	public void putStackInSlot(int slotId, ItemStack stack) {
		int noStationInvenotrySlots = stationInventory.getInventoryStackLimit();
		if(slotId>=0 && slotId<noPlayerInventorySlots) {
			getSlot(slotId).putStack(stack);
		}
		else if (slotId == noPlayerInventorySlots && stack.getItem() instanceof IMateriaTool) {
			
			
			
			
			
			
			IMateriaTool materiaTool = (IMateriaTool) stack.getItem();
			//start index must be 1 (cause 0 ist for the MateriaTool)
			int materiaSlotCounter = addMateriaSlots(materiaTool.getTopSlots(), 1, 34, 23);
			addMateriaSlots(materiaTool.getBotSlots(), materiaSlotCounter, 34 , 45);
		}
		else if(slotId>noPlayerInventorySlots && slotId<noPlayerInventorySlots+noStationInvenotrySlots && stack.getItem() instanceof BaseMateria) {

		}
	}

	/*
	 * toolSlots are only one row
	 */
	private int addMateriaSlots(MateriaToolSlotCollection[] toolSlots, int startCounter, int startX, int startY) {
		for(int i=0; i<toolSlots.length; i++){
			for (int j=0; j<toolSlots[i].getNoSlots(); j++) {
				addSlot(new MateriaContainerSlot(stationInventory, startCounter, startX, startY));
				startX += 19;
				startCounter += 1;
			}
		}
		return startCounter;
	}





	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockList.EQUIPPING_STATION_BLOCK.get());
	}

	//shift-left click handling
	@Nonnull
	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int sourceSlotIndex) {
		int playerInventorySize = player.inventory.getSizeInventory();

		if (sourceSlotIndex>=0 && sourceSlotIndex<playerInventorySize) {
			// selected slot is from hotbar or playerInventory
			ItemStack stack = player.inventory.getStackInSlot(sourceSlotIndex);
			ItemStack copyStack = stack.copy();
			if (stack.getItem() instanceof IMateriaTool && stationInventory.isMateriaToolSlotEmpty()) {
				stationInventory.setMateriaToolStack(copyStack);
				player.inventory.removeStackFromSlot(sourceSlotIndex);
				return copyStack;
			}
			else if (stack.getItem() instanceof BaseMateria && stationInventory.hasEmptyMateriaSlot()) {
				stationInventory.shiftClickAddMateria(copyStack);
				player.inventory.removeStackFromSlot(sourceSlotIndex);
				return copyStack;
			}
		}
		else if (sourceSlotIndex>playerInventorySize && sourceSlotIndex<stationInventory.getSizeInventory()+playerInventorySize) {
			// selected slot is from stationInventory
			ItemStack stack = stationInventory.getStackInSlot(sourceSlotIndex-playerInventorySize);
			ItemStack copyStack = stack.copy();
			if (player.inventory.addItemStackToInventory(copyStack)) {
				stationInventory.removeStackFromSlot(sourceSlotIndex-playerInventorySize);
				return copyStack;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		for (int i=0; i<stationInventory.getInventoryStackLimit(); i++) {
			ItemStack stack = stationInventory.getStackInSlot(i);
			if (!stack.isEmpty()) {
				playerIn.dropItem(stack, false);
			}
		}
	}

	public boolean isMateriaToolSlotEmpty() {
		return stationInventory.isMateriaToolSlotEmpty();
	}

	public ItemStack getMateriaToolStack() {
		return stationInventory.getMateriaToolStack();
	}
}
