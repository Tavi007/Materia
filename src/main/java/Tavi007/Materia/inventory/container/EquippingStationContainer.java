package Tavi007.Materia.inventory.container;

import javax.annotation.Nonnull;

import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.ContainerTypeList;
import Tavi007.Materia.inventory.EquippingStationItemHandler;
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
	private final EquippingStationItemHandler stationItemHandler = new EquippingStationItemHandler(this);

	public EquippingStationContainer(final int windowId, final PlayerInventory playerInventory, final World world, final BlockPos pos) {
		super(ContainerTypeList.EQUIPPING_STATION.get(), windowId);
		this.canInteractWithCallable = IWorldPosCallable.of(world, pos);
		
		
		//MateriaSlots (Id 0-7)
		int startX = 34;
		int startY = 23;
		for (int i=0; i<4; i++) {
			addSlot(new MateriaContainerSlot(stationItemHandler, i, startX+19*i, startY));
		}
		startY = 45;
		for (int i=0; i<4; i++) {
			addSlot(new MateriaContainerSlot(stationItemHandler, 4+i, startX+19*i, startY));
		}

		// MateriaToolSlot (Id 8)
		startX = 8;
		startY = 33;
		addSlot(new MateriaToolContainerSlot(stationItemHandler, 8, startX, startY));
		
		
		startY = 142;
		int slotSizePlus2 = 18;
		// Hotbar (Id 0-8)
		for (int column = 0; column < 9; column++) {
			addSlot(new Slot(playerInventory, column, startX + (column * slotSizePlus2), startY));
		}

		startY = 84;
		// Player Inventory (Id 9-35)
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
		return this.getInventory().get(8);
	}

	public boolean isMateriaToolSlotEmpty() {
		return getMateriaToolStack().isEmpty();
	}
}
