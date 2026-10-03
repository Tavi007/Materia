package Tavi007.Materia.common.inventory.containers;

import Tavi007.Materia.common.inventory.slots.MateriaItemSlot;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class MateriaToolContainer implements Container {

    private final List<MateriaItemSlot> materiaSlots;
    private final List<MateriaSlotConnection> connections;

    public MateriaToolContainer(List<MateriaItemSlot> materiaSlots, List<MateriaSlotConnection> connections) {
        this.materiaSlots = materiaSlots;
        this.connections = connections;
    }

    @Override
    public int getContainerSize() {
        return materiaSlots.size();
    }

    @Override
    public boolean isEmpty() {
        for (MateriaItemSlot slot : materiaSlots) {
            if (!slot.getItem().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int i) {
        return materiaSlots.get(i).getItem();
    }

    @Override
    public ItemStack removeItem(int i, int amount) {
        if (i < 0 || i >= materiaSlots.size() || amount <= 0) {
            return ItemStack.EMPTY;
        }

        MateriaItemSlot slot = materiaSlots.get(i);
        ItemStack stack = slot.getItem();

        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        int removedAmount = Math.min(amount, stack.getCount());

        ItemStack result = stack.copy();
        result.setCount(removedAmount);

        stack.shrink(removedAmount);

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        }

        setChanged();

        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        if (i < 0 || i >= materiaSlots.size()) {
            return ItemStack.EMPTY;
        }

        MateriaItemSlot slot = materiaSlots.get(i);
        ItemStack stack = slot.getItem();

        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        slot.set(ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        if (i < 0 || i >= materiaSlots.size()) {
            return;
        }

        MateriaItemSlot slot = materiaSlots.get(i);

        if (slot.mayPlace(itemStack)) {
            slot.set(itemStack);
            setChanged();
        }
    }

    @Override
    public void setChanged() {
        // Recompute effects of every connection. Speed up possible here, if I know which connection needs the update.
        for(MateriaSlotConnection connection : connections) {
            connection.updateEffects(this);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        for (MateriaItemSlot slot : materiaSlots) {
            slot.set(ItemStack.EMPTY);
        }

        setChanged();
    }
}
