package Tavi007.Materia.common.inventory.containers;

import Tavi007.Materia.common.inventory.slots.MateriaToolSlot;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class EquippingStationContainer implements Container {

    private ItemStack tool;

    public EquippingStationContainer() {
        tool = ItemStack.EMPTY;
    }

    @Override
    public int getContainerSize() {
        if(tool.isEmpty()) {
            //TODO compute additional container size of tool
        }
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return tool.isEmpty();
    }

    @Override
    public ItemStack getItem(int i) {
        if (i == 0) {
            return tool;
        }
        if (i < 0) {
            return null;
        }
        if (tool.isEmpty()) {
            return null;
        }
        //TODO get materia item from tool
        return null;
    }

    @Override
    public ItemStack removeItem(int i, int i1) {
        if (i == 0) {
            tool = ItemStack.EMPTY;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        if (i == 0) {
            tool = ItemStack.EMPTY;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        if (i == 0) {
            tool = itemStack;
        }
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    @Override
    public void clearContent() {
        tool = ItemStack.EMPTY;
        setChanged();
    }
}
