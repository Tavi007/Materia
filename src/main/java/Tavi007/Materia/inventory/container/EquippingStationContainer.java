package Tavi007.Materia.inventory.container;

import javax.annotation.Nonnull;

import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.ContainerTypeList;
import Tavi007.Materia.inventory.EquippingStationInventory;
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
	private final EquippingStationInventory stationInventory = new EquippingStationInventory(this);

	public EquippingStationContainer(final int windowId, final PlayerInventory playerInventory, final World world, final BlockPos pos) {
		super(ContainerTypeList.EQUIPPING_STATION.get(), windowId);
		this.canInteractWithCallable = IWorldPosCallable.of(world, pos);

		int startX = 8;
		int startY = 84;
		int slotSizePlus2 = 18;
		// Hotbar (Id 0-8)
		for (int column = 0; column < 9; column++) {
			this.addSlot(new Slot(playerInventory, column, startX + (column * slotSizePlus2), 142));
		}

		// Player Inventory (Id 9-35)
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 9; column++) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
			}
		}

		// GUI Slots (Id 36-45)
		//		this.addSlot(new MateriaToolSlot(new MateriaToolHandler(), 36, 8, 33));
		this.addSlot(new Slot(stationInventory, 0, 8, 33));
		for(int column=0; column<4; column++) {
			this.addSlot(new Slot(stationInventory, column + 1, 34 + column*19, 23));
		}
		for(int column=0; column<4; column++) {
			this.addSlot(new Slot(stationInventory, column + 5, 34 + column*19, 45));
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

		// selected slot is hotbar or playerInventory
		if (sourceSlotIndex>=0 && sourceSlotIndex<35) {
			ItemStack stack = player.inventory.getStackInSlot(sourceSlotIndex);
			ItemStack copyStack = stack.copy();
			if (stack.getItem() instanceof IMateriaTool && stationInventory.getStackInSlot(0).isEmpty()) {
				stationInventory.setTool(copyStack);
				player.inventory.removeStackFromSlot(sourceSlotIndex);
				return copyStack;
			}
			else if (stack.getItem() instanceof BaseMateria && stationInventory.hasEmptyMateriaSlot()) {
				stationInventory.addMateria(copyStack);
				player.inventory.removeStackFromSlot(sourceSlotIndex);
				return copyStack;
			}
		}
		else if (sourceSlotIndex >= 36 && sourceSlotIndex<45) {
			ItemStack stack = stationInventory.getStackInSlot(sourceSlotIndex-36);
			ItemStack copyStack = stack.copy();
			if (player.inventory.addItemStackToInventory(copyStack)) {
				stationInventory.removeStackFromSlot(sourceSlotIndex-36);
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

}
