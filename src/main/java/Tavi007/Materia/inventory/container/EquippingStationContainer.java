package Tavi007.Materia.inventory.container;

import javax.annotation.Nonnull;

import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.ContainerTypeList;
import Tavi007.Materia.inventory.EquippingStationInventory;
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
	private final EquippingStationInventory inputInventory = new EquippingStationInventory(this);

	public EquippingStationContainer(final int windowId, final PlayerInventory playerInventory, final World world, final BlockPos pos) {
		super(ContainerTypeList.EQUIPPING_STATION.get(), windowId);
		this.canInteractWithCallable = IWorldPosCallable.of(world, pos);

		// GUI Slots (Id 36-45)
//		this.addSlot(new MateriaToolSlot(new MateriaToolHandler(), 36, 8, 33));
		this.addSlot(new Slot(inputInventory, 0, 8, 33));
		for(int column=0; column<4; column++) {
			this.addSlot(new Slot(inputInventory, column + 1, 34 + column*19, 23));
			this.addSlot(new Slot(inputInventory, column + 5, 34 + column*19, 45));
		}

		// Player Inventory (Id 9-35)
		int startX = 8;
		int startY = 84;
		int slotSizePlus2 = 18;
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 9; column++) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
			}
		}

		// Hotbar (Id 0-8)
		for (int column = 0; column < 9; column++) {
			this.addSlot(new Slot(playerInventory, column, startX + (column * slotSizePlus2), 142));
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
		return ItemStack.EMPTY;



//		Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
//		if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;  //EMPTY_ITEM
//		ItemStack sourceStack = sourceSlot.getStack();
//		ItemStack copyOfSourceStack = sourceStack.copy();
//
//
//
//
//
//		// If stack size == 0 (the entire stack was moved) set slot contents to null
//		if (sourceStack.getCount() == 0) {
//			sourceSlot.putStack(ItemStack.EMPTY);
//		} else {
//			sourceSlot.onSlotChanged();
//		}
//
//		sourceSlot.onTake(player, sourceStack);
//		return copyOfSourceStack;
	}

}
