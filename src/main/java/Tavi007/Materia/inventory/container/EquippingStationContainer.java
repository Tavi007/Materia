package Tavi007.Materia.inventory.container;

import javax.annotation.Nonnull;

import Tavi007.Materia.Materia;
import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.ContainerTypeList;
import Tavi007.Materia.inventory.EquippingStationItemHandler;
import Tavi007.Materia.items.MateriaItem;
import Tavi007.Materia.util.CapabilityHelper;
import Tavi007.Materia.util.MateriaEffectHelper;
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

	//Helper for Index counts
	private final int hotbarInvStart = 0;
	private final int hotbarInvEnd = 8; 
	private final int playerInvStart = 9;
	private final int playerInvEnd = 35; 
	private final int materiaInvStart = 36;
	private final int materiaInvEnd = 43;
	private final int toolInvId = 44;

	public EquippingStationContainer(final int windowId, final PlayerInventory playerInventory, final World world, final BlockPos pos) {
		super(ContainerTypeList.EQUIPPING_STATION.get(), windowId);
		this.canInteractWithCallable = IWorldPosCallable.of(world, pos);

		// Hotbar (Id 0-8)
		int startX = 8;
		int startY = 168;
		int slotSizePlus2 = 18;
		for (int column = 0; column < 9; column++) {
			addSlot(new Slot(playerInventory, column, startX + (column * slotSizePlus2), startY));
		}
		// Player Inventory (Id 9-35)
		startY = 110;
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 9; column++) {
				addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
			}
		}
		
		//MateriaSlots (Id 36-43)
		startX = 49;
		startY = 20;
		for (int i=0; i<4; i++) {
			addSlot(new MateriaContainerSlot(stationItemHandler, i, startX+20*i, startY));
		}
		startY = 75;
		for (int i=0; i<4; i++) {
			addSlot(new MateriaContainerSlot(stationItemHandler, 4+i, startX+20*i, startY));
		}

		// MateriaToolSlot (Id 44)
		startX = 80;
		startY = 48;
		addSlot(new MateriaToolContainerSlot(stationItemHandler, 8, startX, startY));
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
			if (sourceStack.getItem() instanceof MateriaItem) {
				if (!mergeItemStack(sourceStack, materiaInvStart, materiaInvEnd+1, false)) {
					onSuccessfulTransfer();
				} else if (!mergeItemStack(sourceStack, playerInvStart, playerInvEnd+1, false)) {
					onSuccessfulTransfer();
				}
			} else if (sourceStack.getItem() instanceof IMateriaTool) {
				if (!mergeItemStack(sourceStack, toolInvId, toolInvId+1, false)) {
					onSuccessfulTransfer();
				} else if (!mergeItemStack(sourceStack, playerInvStart, playerInvEnd+1, false)) {
					onSuccessfulTransfer();
				}
			} else {
				if (!mergeItemStack(sourceStack, playerInvStart, playerInvEnd+1, false)) {
					onSuccessfulTransfer();
				}
			}
		} else if (sourceSlotIndex>=playerInvStart && sourceSlotIndex<=playerInvEnd) { //playerInventorySlot clicked
			if (sourceStack.getItem() instanceof MateriaItem) {
				if (!mergeItemStack(sourceStack, materiaInvStart, materiaInvEnd+1, false)) {
					onSuccessfulTransfer();
				} else if (!mergeItemStack(sourceStack, hotbarInvStart, hotbarInvEnd+1, false)) {
					onSuccessfulTransfer();
				}
			} else if (sourceStack.getItem() instanceof IMateriaTool) {
				if (!mergeItemStack(sourceStack, toolInvId, toolInvId+1, false)) {
					onSuccessfulTransfer();
				} else if (!mergeItemStack(sourceStack, hotbarInvStart, hotbarInvEnd+1, false)) {
					onSuccessfulTransfer();
				}
			} else {
				if (!mergeItemStack(sourceStack, hotbarInvStart, hotbarInvEnd+1, false)) {
					onSuccessfulTransfer();
				}
			}
		} else if (sourceSlotIndex == toolInvId) { // ToolSlot clicked
			if (!mergeItemStack(sourceStack, hotbarInvStart, playerInvEnd+1, false)) {
				onSuccessfulTransfer();
			}
		} else if (sourceSlotIndex>=materiaInvStart && sourceSlotIndex<=materiaInvEnd) { //MateriaSlot clicked
			if (!mergeItemStack(sourceStack, hotbarInvStart, playerInvEnd+1, false)) {
				onSuccessfulTransfer();
			} 
		} else {
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

	private ItemStack onSuccessfulTransfer() {
		ItemStack stack = getMateriaToolStack();
		CapabilityHelper.getMateriaCollection(stack).markDirty();
		MateriaEffectHelper.computeEffectsAndApplyCurrent(stack);
		return ItemStack.EMPTY;
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
		return this.getInventory().get(toolInvId);
	}

	public boolean isMateriaToolSlotEmpty() {
		return getMateriaToolStack().isEmpty();
	}
}
