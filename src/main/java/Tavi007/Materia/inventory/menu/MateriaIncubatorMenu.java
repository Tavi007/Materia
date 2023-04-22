package Tavi007.Materia.inventory.menu;

import javax.annotation.Nonnull;

import Tavi007.Materia.Materia;
import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.MenuList;
import Tavi007.Materia.inventory.MateriaIncubatorItemHandler;
import Tavi007.Materia.items.MateriaItem;
import Tavi007.Materia.tileentity.MateriaIncubatorTileentity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.SlotItemHandler;

public class MateriaIncubatorMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess access;

    // ItemStackHandler
    private final MateriaIncubatorItemHandler incubatorItemHandler;

    // Helper for Index counts
    private final int hotbarInvStart = 0;
    private final int hotbarInvEnd = 8;
    private final int playerInvStart = 9;
    private final int playerInvEnd = 35;
    private final int materiaSlotId = 36;
    private final int fuelSlotId = 37;

    public MateriaIncubatorMenu(final int windowId, final Inventory playerInventory, final Level world, final BlockPos pos) {
        super(MenuList.MATERIA_INCUBATOR.get(), windowId);
        this.access = ContainerLevelAccess.create(world, pos);
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

    public MateriaIncubatorMenu(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowId, playerInventory, playerInventory.player.level, new BlockPos(playerInventory.player.getOnPos()));
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(this.access, playerIn, BlockList.MATERIA_INCUBATOR_BLOCK.get());
    }

    // shift-left click handling
    @Nonnull
    @Override
    public ItemStack quickMoveStack(Player player, int sourceSlotIndex) {
        Slot sourceSlot = slots.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.hasItem())
            return ItemStack.EMPTY; // EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (sourceSlotIndex >= hotbarInvStart && sourceSlotIndex <= hotbarInvEnd) { // HotBarSlot clicked
            if (sourceStack.getItem() instanceof MateriaItem) {
                if (!moveItemStackTo(sourceStack, materiaSlotId, materiaSlotId + 1, false)) {
                    onSuccessfulTransfer();
                } else if (!moveItemStackTo(sourceStack, fuelSlotId, fuelSlotId + 1, false)) {
                    onSuccessfulTransfer();
                } else if (!moveItemStackTo(sourceStack, playerInvStart, playerInvEnd + 1, false)) {
                    onSuccessfulTransfer();
                }
            } else {
                if (!moveItemStackTo(sourceStack, fuelSlotId, fuelSlotId + 1, false)) {
                    onSuccessfulTransfer();
                } else if (!moveItemStackTo(sourceStack, playerInvStart, playerInvEnd + 1, false)) {
                    onSuccessfulTransfer();
                }
            }
        } else if (sourceSlotIndex >= playerInvStart && sourceSlotIndex <= playerInvEnd) { // playerInventorySlot clicked
            if (sourceStack.getItem() instanceof MateriaItem) {
                if (!moveItemStackTo(sourceStack, materiaSlotId, materiaSlotId + 1, false)) {
                    onSuccessfulTransfer();
                } else if (!moveItemStackTo(sourceStack, fuelSlotId, fuelSlotId + 1, false)) {
                    onSuccessfulTransfer();
                } else if (!moveItemStackTo(sourceStack, hotbarInvStart, hotbarInvEnd + 1, false)) {
                    onSuccessfulTransfer();
                }
            } else {
                if (!moveItemStackTo(sourceStack, fuelSlotId, fuelSlotId + 1, false)) {
                    onSuccessfulTransfer();
                } else if (!moveItemStackTo(sourceStack, hotbarInvStart, hotbarInvEnd + 1, false)) {
                    onSuccessfulTransfer();
                }
            }
        } else if (sourceSlotIndex >= materiaSlotId && sourceSlotIndex <= fuelSlotId) {
            if (!moveItemStackTo(sourceStack, hotbarInvStart, playerInvEnd + 1, false)) {
                onSuccessfulTransfer();
            }
        } else {
            Materia.LOGGER.warn("Invalid slotIndex:" + sourceSlotIndex);
            return ItemStack.EMPTY;
        }

        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(player, sourceStack);

        return copyOfSourceStack;
    }

    private void onSuccessfulTransfer() {
    }

    public ItemStack getMateriaStack() {
        return getSlot(materiaSlotId).getItem();
    }

    public boolean isMateriaSlotEmpty() {
        return getMateriaStack().isEmpty();
    }

    public ItemStack getFuelStack() {
        return getSlot(fuelSlotId).getItem();
    }

    public boolean isFuelSlotEmpty() {
        return getMateriaStack().isEmpty();
    }
}
