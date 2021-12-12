package Tavi007.Materia.inventory.container;

import javax.annotation.Nonnull;

import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.ContainerTypeList;
import Tavi007.Materia.inventory.MateriaHibernatorItemHandler;
import Tavi007.Materia.tileentity.MateriaHibernatorTileentity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;

public class MateriaHibernatorContainer extends Container {

	private final IWorldPosCallable canInteractWithCallable;

	//ItemStackHandler
	private final MateriaHibernatorItemHandler hibernatorItemHandler;

	//Helper for Index counts
	private final int hotbarInvStart = 0;
	private final int hotbarInvEnd = 8; 
	private final int playerInvStart = 9;
	private final int playerInvEnd = 35; 
	private final int materiaSlotId = 36;
	private final int fuelSlotId = 43;
	
	public MateriaHibernatorContainer(final int windowId, final PlayerInventory playerInventory, final World world, final BlockPos pos) {
		super(ContainerTypeList.EQUIPPING_STATION.get(), windowId);
		this.canInteractWithCallable = IWorldPosCallable.of(world, pos);
		this.hibernatorItemHandler = new MateriaHibernatorItemHandler(this, new MateriaHibernatorTileentity());

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

		//MateriaSlots (Id 36)
		startX = 50;
		startY = 108;
		addSlot(new MateriaContainerSlot(hibernatorItemHandler, 0, startX, startY));
		//MateriaSlots (Id 37)
		startX = 150;
		startY = 108;
		addSlot(new SlotItemHandler(hibernatorItemHandler, 0, startX, startY));
	}

	public MateriaHibernatorContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
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
		return sourceSlot.getStack();
	}

	public ItemStack getMateriaStack() {
		return this.getInventory().get(materiaSlotId);
	}

	public boolean isMateriaSlotEmpty() {
		return getMateriaStack().isEmpty();
	}

	public ItemStack getFuelStack() {
		return this.getInventory().get(fuelSlotId);
	}

	public boolean isFuelSlotEmpty() {
		return getMateriaStack().isEmpty();
	}
}
