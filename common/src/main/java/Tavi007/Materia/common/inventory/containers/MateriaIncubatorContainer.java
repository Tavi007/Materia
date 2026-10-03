package Tavi007.Materia.common.inventory.containers;

import Tavi007.Materia.common.inventory.slots.IncubatorFuelSlot;
import Tavi007.Materia.common.inventory.slots.MateriaItemSlot;
import Tavi007.Materia.common.items.MateriaItem;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;


public class MateriaIncubatorContainer implements Container  {
    private ItemStack materia; // slotId = 0
    private ItemStack fuel; // slotId = 1

    public MateriaIncubatorContainer() {
        this.materia = ItemStack.EMPTY;
        this.fuel = ItemStack.EMPTY;
    }

    @Override
    public int getContainerSize() {
        return 2;
    }


    @Override
    public boolean isEmpty() {
        return materia.isEmpty() && fuel.isEmpty();
    }

    @Override
    public ItemStack getItem(int i) {
        return switch (i) {
            case 0 -> materia;
            case 1 -> fuel;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public ItemStack removeItem(int i, int amount) {
        ItemStack result = switch (i) {
            case 0 -> materia = ItemStack.EMPTY;
            case 1 -> fuel = ItemStack.EMPTY;
            default -> ItemStack.EMPTY;
        };

        setChanged();
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return switch (i) {
            case 0 -> materia = ItemStack.EMPTY;
            case 1 -> fuel = ItemStack.EMPTY;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        switch (i) {
            case 0 -> {
                if (itemStack.getItem() instanceof MateriaItem) {
                    materia = itemStack;
                }
            }
            case 1 -> {
                fuel = itemStack;
            }
        }

        setChanged();
    }

    @Override
    public void setChanged() {
        // Nothing to do unless this container needs to notify another object.
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        materia = ItemStack.EMPTY;
        fuel = ItemStack.EMPTY;
        setChanged();
    }
}
