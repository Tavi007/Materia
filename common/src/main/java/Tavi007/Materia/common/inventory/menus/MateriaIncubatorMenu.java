package Tavi007.Materia.common.inventory.menus;

import Tavi007.Materia.client.gui.screens.MateriaIncubatorScreen;
import Tavi007.Materia.common.init.ModMenus;
import Tavi007.Materia.common.inventory.slots.MateriaItemSlot;
import Tavi007.Materia.common.inventory.slots.ParaMagicPhialSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MateriaIncubatorMenu extends AbstractContainerMenu {

    private static final int MATERIA_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int INV_SLOT_START = 2;
    private static final int INV_SLOT_END = 29;
    private static final int HOTBAR_SLOT_START = 29;
    private static final int HOTBAR_SLOT_END = 38;

    private final Container materiaIncubator;

    public MateriaIncubatorMenu(
            int containerId,
            Inventory playerInventory,
            FriendlyByteBuf extraData
    ) {
        super(ModMenus.MATERIA_INCUBATOR.get(), containerId);
        materiaIncubator = new SimpleContainer(2);

        // Materia
        this.addSlot(new MateriaItemSlot(materiaIncubator,
            MATERIA_SLOT,
            MateriaIncubatorScreen.MATERIA_SLOT_X,
            MateriaIncubatorScreen.MATERIA_SLOT_Y)
        );

        // Fuel
        this.addSlot(new ParaMagicPhialSlot(materiaIncubator,
            FUEL_SLOT,
            MateriaIncubatorScreen.FUEL_SLOT_X,
            MateriaIncubatorScreen.FUEL_SLOT_Y));

        // Player inventory
        addPlayerInventory(playerInventory);

        // Player hotbar
        addPlayerHotbar(playerInventory);
    }

    private void addPlayerInventory(Inventory inventory) {
        for(int j = 0; j < 3; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, MateriaIncubatorScreen.IMAGE_HEIGHT - 82 + j * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {
        for(int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(inventory, j, 8 + j * 18, MateriaIncubatorScreen.IMAGE_HEIGHT-24));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack empty = ItemStack.EMPTY;

        if (index < 0 || index >= this.slots.size()) {
            return empty;
        }

        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) {
            return empty;
        }

        ItemStack sourceStack = slot.getItem();
        ItemStack originalStack = sourceStack.copy();

        // Machine slots -> player inventory
        if (index < INV_SLOT_START) {
            if (!this.moveItemStackTo(
                    sourceStack,
                    INV_SLOT_START,
                    HOTBAR_SLOT_END,
                    false
            )) {
                return empty;
            }
        }
        // Player inventory -> machine slots
        else {
            // Try the materia slot first.
            if (MateriaItemSlot.mayPlaceItem(sourceStack)) {
                if (this.moveItemStackTo(
                        sourceStack,
                        MATERIA_SLOT,
                        MATERIA_SLOT + 1,
                        false
                )) {
                    // Successfully moved into the materia slot.
                } else if (this.getFuelSlot().mayPlace(sourceStack)) {
                    if (!this.moveItemStackTo(
                            sourceStack,
                            FUEL_SLOT,
                            FUEL_SLOT + 1,
                            false
                    )) {
                        return empty;
                    }
                } else {
                    return empty;
                }
            }
            // Otherwise, try the fuel slot.
            else if (this.getFuelSlot().mayPlace(sourceStack)) {
                if (!this.moveItemStackTo(
                        sourceStack,
                        FUEL_SLOT,
                        FUEL_SLOT + 1,
                        false
                )) {
                    return empty;
                }
            }
            // Neither machine slot accepts the item:
            // move between the player's inventory and hotbar.
            else if (index < HOTBAR_SLOT_START) {
                if (!this.moveItemStackTo(
                        sourceStack,
                        HOTBAR_SLOT_START,
                        HOTBAR_SLOT_END,
                        false
                )) {
                    return empty;
                }
            } else {
                if (!this.moveItemStackTo(
                        sourceStack,
                        INV_SLOT_START,
                        INV_SLOT_END,
                        false
                )) {
                    return empty;
                }
            }
        }

        if (sourceStack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        slot.onTake(player, sourceStack);

        return originalStack;
    }


    private Slot getFuelSlot() {
        return slots.get(FUEL_SLOT);
    }

    private Slot getMateriaSlot() {
        return slots.get(MATERIA_SLOT);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.materiaIncubator.stillValid(player);
    }
}

