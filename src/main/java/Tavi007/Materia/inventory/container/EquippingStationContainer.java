package Tavi007.Materia.inventory.container;

import javax.annotation.Nonnull;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.toolslots.MateriaCollection;
import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.ContainerTypeList;
import Tavi007.Materia.inventory.EquippingStationItemHandler;
import Tavi007.Materia.items.BaseMateria;
import Tavi007.Materia.items.IMateriaTool;
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
	
	//ItemStackHandler
	private final EquippingStationItemHandler stationItemHandler = new EquippingStationItemHandler(this);
	private final MateriaCollection materiaItemHandler = new MateriaCollection();
	
	//Helper for Index counts
	private final int materiaInvStart = 0;
	private final int materiaInvEnd = 7;
	
	private final int toolInvId = 8;

	private final int hotbarInvStart = 9;
	private final int hotbarInvEnd = 18; 
	private final int playerInvStart = 19;
	private final int playerInvEnd = 44; 

	
	public EquippingStationContainer(final int windowId, final PlayerInventory playerInventory, final World world, final BlockPos pos) {
		super(ContainerTypeList.EQUIPPING_STATION.get(), windowId);
		this.canInteractWithCallable = IWorldPosCallable.of(world, pos);

		//MateriaSlots (Id 0-7)
		int startX = 9;
		int startY = 20;
		for (int i=0; i<4; i++) {
			addSlot(new MateriaContainerSlot(stationItemHandler, i, startX+20*i, startY));
		}
		startY = 75;
		for (int i=0; i<4; i++) {
			addSlot(new MateriaContainerSlot(stationItemHandler, 4+i, startX+20*i, startY));
		}

		// MateriaToolSlot (Id 8)
		startX = 40;
		startY = 48;
		addSlot(new MateriaToolContainerSlot(stationItemHandler, toolInvId, startX, startY));

		// Hotbar (Id 9-18)
		startX = 8;
		startY = 168;
		int slotSizePlus2 = 18;
		for (int column = 0; column < 9; column++) {
			addSlot(new Slot(playerInventory, column, startX + (column * slotSizePlus2), startY));
		}
		// Player Inventory (Id 19-44)
		startY = 110;
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 9; column++) {
				addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
			}
		}
	}

	public EquippingStationContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, playerInventory.player.world, new BlockPos(playerInventory.player.getPositionVec()) );
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockList.EQUIPPING_STATION_BLOCK.get());
	}

	//shift-left click handling
	@Nonnull
	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int sourceSlotIndex) {
		Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
		if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;  //EMPTY_ITEM
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();

		if (sourceSlotIndex>=hotbarInvStart && sourceSlotIndex<=hotbarInvEnd) { //HotBarSlot clicked
			if (sourceStack.getItem() instanceof BaseMateria) {
				if (!mergeItemStack(sourceStack, materiaInvStart, materiaInvEnd+1, false)) {return ItemStack.EMPTY;}
				else if (!mergeItemStack(sourceStack, playerInvStart, playerInvEnd+1, false)) {return ItemStack.EMPTY;}
			}
			else if (sourceStack.getItem() instanceof IMateriaTool) {
				if (!mergeItemStack(sourceStack, toolInvId, toolInvId+1, false)) {return ItemStack.EMPTY;}
				else if (!mergeItemStack(sourceStack, playerInvStart, playerInvEnd+1, false)) {return ItemStack.EMPTY;}
			}
			else {
				if (!mergeItemStack(sourceStack, playerInvStart, playerInvEnd+1, false)) {return ItemStack.EMPTY;}
			}
		} 
		else if (sourceSlotIndex>=playerInvStart && sourceSlotIndex<=playerInvEnd) { //playerInventorySlot clicked
			if (sourceStack.getItem() instanceof BaseMateria) {
				if (!mergeItemStack(sourceStack, materiaInvStart, materiaInvEnd+1, false)) {return ItemStack.EMPTY;}
				else if (!mergeItemStack(sourceStack, hotbarInvStart, hotbarInvEnd+1, false)) {return ItemStack.EMPTY;}
			}
			else if (sourceStack.getItem() instanceof IMateriaTool) {
				if (!mergeItemStack(sourceStack, toolInvId, toolInvId+1, false)) {return ItemStack.EMPTY;}
				else if (!mergeItemStack(sourceStack, hotbarInvStart, hotbarInvEnd+1, false)) {return ItemStack.EMPTY;}
			}
			else {
				if (!mergeItemStack(sourceStack, hotbarInvStart, hotbarInvEnd+1, false)) {return ItemStack.EMPTY;}
			}
		} 
		else if (sourceSlotIndex == toolInvId) { // ToolSlot clicked
			if (!mergeItemStack(sourceStack, hotbarInvStart, playerInvEnd+1, false)) {return ItemStack.EMPTY;} 
		} 
		else if (sourceSlotIndex>=materiaInvStart && sourceSlotIndex<=materiaInvEnd) { //MateriaSlot clicked
			if (!mergeItemStack(sourceStack, hotbarInvStart, playerInvEnd+1, false)) {return ItemStack.EMPTY;} 
		} 
		else {
			Materia.LOGGER.warn("Invalid slotIndex:" + sourceSlotIndex);
			return ItemStack.EMPTY;
		}


		// If stack size == 0 (the entire stack was moved) set slot contents to null
		if (sourceStack.getCount() == 0) {
			sourceSlot.putStack(ItemStack.EMPTY);
		} else {
			sourceSlot.onSlotChanged();
		}

		sourceSlot.onTake(player, sourceStack);


		return copyOfSourceStack;
	}


	//only drop MateriaTool ItemStack. It should have all the added effects
	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		ItemStack stack = getMateriaToolStack();
		if (!stack.isEmpty()) {
			playerIn.dropItem(stack, false);
		}
	}

	public ItemStack getMateriaToolStack() {
		return this.getInventory().get(8);
	}

	public boolean isMateriaToolSlotEmpty() {
		return getMateriaToolStack().isEmpty();
	}
}
