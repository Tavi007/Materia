package Tavi007.Materia.inventory.menus;

import javax.annotation.Nonnull;

import Tavi007.Materia.Materia;
import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.MenuList;
import Tavi007.Materia.inventory.EquippingStationItemHandler;
import Tavi007.Materia.inventory.menus.slots.MateriaContainerSlot;
import Tavi007.Materia.inventory.menus.slots.MateriaToolContainerSlot;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.items.MateriaItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EquippingStationMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess access;

    // ItemStackHandler
    private final EquippingStationItemHandler stationItemHandler = new EquippingStationItemHandler(this);

    // Helper for Index counts
    private final int hotbarInvStart = 0;
    private final int hotbarInvEnd = 8;
    private final int playerInvStart = 9;
    private final int playerInvEnd = 35;
    private final int materiaInvStart = 36;
    private final int materiaInvEnd = 43;
    private final int toolInvId = 44;

    public EquippingStationMenu(final int windowId, final Inventory playerInventory, final Level world, final BlockPos pos) {
        super(MenuList.EQUIPPING_STATION.get(), windowId);
        this.access = ContainerLevelAccess.create(world, pos);

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

        // MateriaSlots (Id 36-43)
        startX = 49;
        startY = 20;
        for (int i = 0; i < 4; i++) {
            addSlot(new MateriaContainerSlot(stationItemHandler, i, startX + 20 * i, startY));
        }
        startY = 75;
        for (int i = 0; i < 4; i++) {
            addSlot(new MateriaContainerSlot(stationItemHandler, 4 + i, startX + 20 * i, startY));
        }

        // MateriaToolSlot (Id 44)
        startX = 80;
        startY = 48;
        addSlot(new MateriaToolContainerSlot(stationItemHandler, 8, startX, startY));
    }

    public EquippingStationMenu(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowId, playerInventory, playerInventory.player.level, new BlockPos(playerInventory.player.getOnPos()));
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(this.access, playerIn, BlockList.EQUIPPING_STATION_BLOCK.get());
    }

    // shift-left click handling
    @Nonnull
    @Override
    public ItemStack quickMoveStack(Player player, int sourceSlotIndex) {
        Slot sourceSlot = slots.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.hasItem())
            return ItemStack.EMPTY; // EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();

        if (sourceSlotIndex >= hotbarInvStart && sourceSlotIndex <= hotbarInvEnd) { // HotBarSlot clicked
            if (sourceStack.getItem() instanceof MateriaItem) {
                if (moveItemStackTo(sourceStack, materiaInvStart, materiaInvEnd + 1, false)) {
                    return onSuccesfulQuickMove(sourceStack, sourceSlot, player);
                } else if (moveItemStackTo(sourceStack, playerInvStart, playerInvEnd + 1, false)) {
                    return onSuccesfulQuickMove(sourceStack, sourceSlot, player);
                }
            } else if (sourceStack.getItem() instanceof IMateriaTool) {
                if (moveItemStackTo(sourceStack, toolInvId, toolInvId + 1, false)) {
                    return onSuccesfulQuickMove(sourceStack, sourceSlot, player);
                } else if (moveItemStackTo(sourceStack, playerInvStart, playerInvEnd + 1, false)) {
                    return onSuccesfulQuickMove(sourceStack, sourceSlot, player);
                }
            } else {
                if (moveItemStackTo(sourceStack, playerInvStart, playerInvEnd + 1, false)) {
                    return onSuccesfulQuickMove(sourceStack, sourceSlot, player);
                }
            }
        } else if (sourceSlotIndex >= playerInvStart && sourceSlotIndex <= playerInvEnd) { // playerInventorySlot clicked
            if (sourceStack.getItem() instanceof MateriaItem) {
                if (moveItemStackTo(sourceStack, materiaInvStart, materiaInvEnd + 1, false)) {
                    return onSuccesfulQuickMove(sourceStack, sourceSlot, player);
                } else if (moveItemStackTo(sourceStack, hotbarInvStart, hotbarInvEnd + 1, false)) {
                    return onSuccesfulQuickMove(sourceStack, sourceSlot, player);
                }
            } else if (sourceStack.getItem() instanceof IMateriaTool) {
                if (moveItemStackTo(sourceStack, toolInvId, toolInvId + 1, false)) {
                    return onSuccesfulQuickMove(sourceStack, sourceSlot, player);
                } else if (moveItemStackTo(sourceStack, hotbarInvStart, hotbarInvEnd + 1, false)) {
                    return onSuccesfulQuickMove(sourceStack, sourceSlot, player);
                }
            } else {
                if (moveItemStackTo(sourceStack, hotbarInvStart, hotbarInvEnd + 1, false)) {
                    return onSuccesfulQuickMove(sourceStack, sourceSlot, player);
                }
            }
        } else if (sourceSlotIndex >= materiaInvStart && sourceSlotIndex <= materiaInvEnd) { // MateriaSlot clicked
            if (moveItemStackTo(sourceStack, hotbarInvStart, playerInvEnd + 1, false)) {
                return onSuccesfulQuickMove(sourceStack, sourceSlot, player);
            }
        } else if (sourceSlotIndex == toolInvId) { // ToolSlot clicked
            if (moveItemStackTo(sourceStack, hotbarInvStart, playerInvEnd + 1, false)) {
                ItemStack copy = onSuccesfulQuickMove(sourceStack, sourceSlot, player);
                // empties materia slots
                for (int i = materiaInvStart; i <= materiaInvEnd; i++) {
                    slots.get(i).remove(1);
                }
                return copy;
            }
        } else {
            Materia.LOGGER.warn("Invalid slotIndex:" + sourceSlotIndex);
        }
        return ItemStack.EMPTY;
    }

    private ItemStack onSuccesfulQuickMove(ItemStack sourceStack, Slot sourceSlot, Player player) {
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(player, sourceStack);

        return sourceStack.copy();

    }

    // only drop MateriaTool ItemStack. It should have all the added effects
    @Override
    public void removed(Player playerIn) {
        ItemStack stack = getMateriaToolStack();
        if (!stack.isEmpty()) {
            playerIn.drop(stack, false);
        }
    }

    public ItemStack getMateriaToolStack() {
        return this.getItems().get(toolInvId);
    }

    public boolean isMateriaToolSlotEmpty() {
        return getMateriaToolStack().isEmpty();
    }
}
