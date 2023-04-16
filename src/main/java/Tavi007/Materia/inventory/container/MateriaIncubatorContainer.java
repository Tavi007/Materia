package Tavi007.Materia.inventory.container;

import javax.annotation.Nonnull;

import Tavi007.Materia.Materia;
import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.ContainerTypeList;
import Tavi007.Materia.inventory.MateriaIncubatorItemHandler;
import Tavi007.Materia.items.MateriaItem;
import Tavi007.Materia.tileentity.MateriaIncubatorTileentity;
import net.minecraft.core.BlockPos;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class MateriaIncubatorContainer extends Container {

    private final IWorldPosCallable canInteractWithCallable;

    // ItemStackHandler
    private final MateriaIncubatorItemHandler incubatorItemHandler;

    // Helper for Index counts
    private final int hotbarInvStart = 0;
    private final int hotbarInvEnd = 8;
    private final int playerInvStart = 9;
    private final int playerInvEnd = 35;
    private final int materiaSlotId = 36;
    private final int fuelSlotId = 37;

    public MateriaIncubatorContainer(final int windowId, final PlayerInventory playerInventory, final World world, final BlockPos pos) {
        super(ContainerTypeList.MATERIA_INCUBATOR.get(), windowId);
        this.canInteractWithCallable = IWorldPosCallable.of(world, pos);
        this.incubatorItemHandler = new MateriaIncubatorItemHandler(this, new MateriaIncubatorTileentity());

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

        // MateriaSlots (Id 36)
        startX = 80;
        startY = 71;
        addSlot(new MateriaContainerSlot(incubatorItemHandler, 0, startX, startY));
        // MateriaSlots (Id 37)
        startX = 80;
        startY = 21;
        addSlot(new SlotItemHandler(incubatorItemHandler, 1, startX, startY));
    }

    public MateriaIncubatorContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, playerInventory.player.world, new BlockPos(playerInventory.player.getPositionVec()));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockList.MATERIA_INCUBATOR_BLOCK.get());
    }

    // shift-left click handling
    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int sourceSlotIndex) {
        Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.getHasStack())
            return ItemStack.EMPTY; // EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (sourceSlotIndex >= hotbarInvStart && sourceSlotIndex <= hotbarInvEnd) { // HotBarSlot clicked
            if (sourceStack.getItem() instanceof MateriaItem) {
                if (!mergeItemStack(sourceStack, materiaSlotId, materiaSlotId + 1, false)) {
                    onSuccessfulTransfer();
                } else if (!mergeItemStack(sourceStack, fuelSlotId, fuelSlotId + 1, false)) {
                    onSuccessfulTransfer();
                } else if (!mergeItemStack(sourceStack, playerInvStart, playerInvEnd + 1, false)) {
                    onSuccessfulTransfer();
                }
            } else {
                if (!mergeItemStack(sourceStack, fuelSlotId, fuelSlotId + 1, false)) {
                    onSuccessfulTransfer();
                } else if (!mergeItemStack(sourceStack, playerInvStart, playerInvEnd + 1, false)) {
                    onSuccessfulTransfer();
                }
            }
        } else if (sourceSlotIndex >= playerInvStart && sourceSlotIndex <= playerInvEnd) { // playerInventorySlot clicked
            if (sourceStack.getItem() instanceof MateriaItem) {
                if (!mergeItemStack(sourceStack, materiaSlotId, materiaSlotId + 1, false)) {
                    onSuccessfulTransfer();
                } else if (!mergeItemStack(sourceStack, fuelSlotId, fuelSlotId + 1, false)) {
                    onSuccessfulTransfer();
                } else if (!mergeItemStack(sourceStack, hotbarInvStart, hotbarInvEnd + 1, false)) {
                    onSuccessfulTransfer();
                }
            } else {
                if (!mergeItemStack(sourceStack, fuelSlotId, fuelSlotId + 1, false)) {
                    onSuccessfulTransfer();
                } else if (!mergeItemStack(sourceStack, hotbarInvStart, hotbarInvEnd + 1, false)) {
                    onSuccessfulTransfer();
                }
            }
        } else if (sourceSlotIndex >= materiaSlotId && sourceSlotIndex <= fuelSlotId) {
            if (!mergeItemStack(sourceStack, hotbarInvStart, playerInvEnd + 1, false)) {
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

    private void onSuccessfulTransfer() {
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
