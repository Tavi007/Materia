package Tavi007.Materia.inventory.container;

import javax.annotation.Nonnull;

import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.ContainerTypeList;
import Tavi007.Materia.inventory.EquippingStationItemHandler;
import Tavi007.Materia.items.BaseMateria;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.items.MateriaToolSlotCollection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
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
	
	public final boolean[] isMateriaSlotinUse = {false, false, false, false, false, false, false, false}; //8 entries

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
		addSlot(new MateriaToolContainerSlot(stationItemHandler, 0, 8, 33));
		//MateriaSlots will be added on the fly, depending on the tool.
		startX = 34;
		for (int i=0; i<4; i++) {
			addSlot(new MateriaContainerSlot(stationItemHandler, 1+i, 34+19*i, 23));
		}
		for (int i=0; i<4; i++) {
			addSlot(new MateriaContainerSlot(stationItemHandler, 5+i, 34+19*i, 45));
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
}
