package Tavi007.Materia.common.inventory.menus;

import Tavi007.Materia.common.init.ModMenus;
import Tavi007.Materia.common.inventory.containers.MateriaIncubatorContainer;
import Tavi007.Materia.common.inventory.slots.IncubatorFuelSlot;
import Tavi007.Materia.common.inventory.slots.MateriaItemSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MateriaIncubatorMenu extends AbstractContainerMenu {

    private static final int materiaSlotId = 0;
    private static final int fuelSlotId = 1;

    public MateriaIncubatorMenu(
            int containerId,
            Inventory playerInventory
    ) {
        super(ModMenus.MATERIA_INCUBATOR, containerId);
        MateriaIncubatorContainer container = new MateriaIncubatorContainer();

        // Materia
        this.addSlot(new MateriaItemSlot(container,
            materiaSlotId,
            26,
            20)
        );

        // Fuel
        this.addSlot(new IncubatorFuelSlot(container,
                materiaSlotId,
                fuelSlotId,
                44,
                20));

        // Player inventory
        addPlayerInventory(playerInventory);

        // Player hotbar
        addPlayerHotbar(playerInventory);
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(
                        new Slot(
                                inventory,
                                column + row * 9 + 9,
                                8 + column * 18,
                                51 + row * 18
                        )
                );
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {
        for (int column = 0; column < 9; column++) {
            this.addSlot(
                    new Slot(
                            inventory,
                            column,
                            8 + column * 18,
                            109
                    )
            );
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack original = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        original = stack.copy();

        // Incubator slots
        if (index < 2) {
            // Incubator -> player inventory
            if (!moveItemStackTo(stack, 2, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            // Player -> incubator

            // Try materia slot
            if (this.slots.get(materiaSlotId).mayPlace(stack)) {
                if (!moveItemStackTo(stack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            }
            // Try fuel slot
            else if (this.slots.get(fuelSlotId).mayPlace(stack)) {
                if (!moveItemStackTo(stack, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return original;
    }

    @Override
    public boolean stillValid(Player player) {
        return slots.get(materiaSlotId).container.stillValid(player);
    }
}

