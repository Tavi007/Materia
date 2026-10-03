package Tavi007.Materia.common.inventory.menus;

import Tavi007.Materia.common.init.ModMenus;
import Tavi007.Materia.common.inventory.containers.EquippingStationContainer;
import Tavi007.Materia.common.inventory.slots.MateriaItemSlot;
import Tavi007.Materia.common.inventory.slots.MateriaToolSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EquippingStationMenu extends AbstractContainerMenu {

    private static final int toolSlotId = 0;

    public EquippingStationMenu(
            int containerId,
            Inventory playerInventory
    ) {
        super(ModMenus.EQUIPPING_STATION, containerId);

        EquippingStationContainer container = new EquippingStationContainer();


        // Special tool
        this.addSlot(new MateriaToolSlot(
                container,
                toolSlotId,
                26,
                20));

        // Materia slots stored inside the tool
        for (int i = 0; i < 4; i++) {
            this.addSlot(
                new MateriaItemSlot(
                    container,
                    i + 1,
                    44 + i * 18,
                    20
                )
            );
        }

        // Player inventory
        addPlayerInventory(playerInventory);

        // Player hotbar
        addPlayerHotbar(playerInventory);
    }

    public ItemStack getMateriaToolStack() {
        return slots.get(toolSlotId).getItem();
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

        // Station slots
        if (index < 5) {
            // Move into player inventory
            if (!moveItemStackTo(stack, 5, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            // Player inventory -> station

            if (slots.get(toolSlotId).mayPlace(stack)) {
                if (!moveItemStackTo(stack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                boolean moved = false;

                for (int i = 1; i < 5; i++) {
                    Slot materiaSlot = this.slots.get(i);

                    if (materiaSlot.mayPlace(stack)) {
                        if (moveItemStackTo(stack, i, i + 1, false)) {
                            moved = true;
                            break;
                        }
                    }
                }

                if (!moved) {
                    return ItemStack.EMPTY;
                }
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
        return slots.get(toolSlotId).container.stillValid(player);
    }
}