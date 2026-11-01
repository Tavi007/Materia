package Tavi007.Materia.common.inventory.menus;

import Tavi007.Materia.client.gui.screens.ParaMagicExtractorScreen;
import Tavi007.Materia.common.init.ModMenus;
import Tavi007.Materia.common.inventory.slots.MateriaItemSlot;
import Tavi007.Materia.common.inventory.slots.ParaMagicExtractorResultSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ParaMagicExtractorMenu extends AbstractContainerMenu {

    private static final int INPUT = 0;
    private static final int EMPTY_BOTTLE = 1;
    private static final int PHIAL_OUTPUT_START = 2;
    private static final int PHIAL_OUTPUT_END = 8;
    private static final int INV_SLOT_START = 8;
    private static final int INV_SLOT_END = 35;
    private static final int HOTBAR_SLOT_START = 35;
    private static final int HOTBAR_SLOT_END = 44;

    private final Container paraMagicExtractor;

    public ParaMagicExtractorMenu(
            int containerId,
            Inventory playerInventory,
            FriendlyByteBuf extraData
    ) {
        super(ModMenus.MATERIA_INCUBATOR.get(), containerId);
        paraMagicExtractor = new SimpleContainer(8);

        // Input
        this.addSlot(new Slot(paraMagicExtractor,
            INPUT,
            ParaMagicExtractorScreen.INPUT_SLOT_X,
            ParaMagicExtractorScreen.INPUT_SLOT_Y)
        );

        // Empty bottle
        this.addSlot(new Slot(paraMagicExtractor,
            EMPTY_BOTTLE,
            ParaMagicExtractorScreen.BOTTLE_SLOT_X,
            ParaMagicExtractorScreen.BOTTLE_SLOT_Y));

        // Output
        addOutput(paraMagicExtractor, playerInventory.player);

        // Player inventory
        addPlayerInventory(playerInventory);

        // Player hotbar
        addPlayerHotbar(playerInventory);
    }

    private void addOutput(Container container, Player player) {
        for(int j = 0; j < 2; ++j) {
            for(int k = 0; k < 3; ++k) {
                this.addSlot(new ParaMagicExtractorResultSlot(
                        player,
                        container,
                        k + j * 3 + PHIAL_OUTPUT_START,
                        ParaMagicExtractorScreen.OUTPUT_SLOT_X + k * 18,
                        ParaMagicExtractorScreen.OUTPUT_SLOT_Y + j * 18));
            }
        }
    }

    private void addPlayerInventory(Inventory inventory) {
        for(int j = 0; j < 3; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(
                        inventory,
                        k + j * 9 + 9,
                        8 + k * 18, ParaMagicExtractorScreen.IMAGE_HEIGHT - 82 + j * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {
        for(int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(
                    inventory,
                    j,
                    8 + j * 18,
                    ParaMagicExtractorScreen.IMAGE_HEIGHT-24));
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
                        INPUT,
                        INPUT + 1,
                        false
                )) {
                    // Successfully moved into the materia slot.
                } else if (this.getBottleSlot().mayPlace(sourceStack)) {
                    if (!this.moveItemStackTo(
                            sourceStack,
                            EMPTY_BOTTLE,
                            EMPTY_BOTTLE + 1,
                            false
                    )) {
                        return empty;
                    }
                } else {
                    return empty;
                }
            }
            // Otherwise, try the fuel slot.
            else if (this.getBottleSlot().mayPlace(sourceStack)) {
                if (!this.moveItemStackTo(
                        sourceStack,
                        EMPTY_BOTTLE,
                        EMPTY_BOTTLE + 1,
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

    private Slot getBottleSlot() {
        return slots.get(EMPTY_BOTTLE);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.paraMagicExtractor.stillValid(player);
    }
}

