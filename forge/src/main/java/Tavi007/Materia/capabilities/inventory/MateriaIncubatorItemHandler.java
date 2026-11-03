package Tavi007.Materia.capabilities.inventory;

import Tavi007.Materia.common.blocks.entities.MateriaIncubatorBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

// TODO: fix and register
public class MateriaIncubatorItemHandler implements IItemHandler {

    private final MateriaIncubatorBlockEntity blockEntity;
    private final Direction direction;

    public MateriaIncubatorItemHandler(
            MateriaIncubatorBlockEntity blockEntity,
            Direction direction) {
        this.blockEntity = blockEntity;
        this.direction = direction;
    }

    @Override
    public int getSlots() {
        return blockEntity.getContainerSize();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return blockEntity.getItem(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!isSlotAccessible(slot) ||
                !blockEntity.canPlaceItem(slot, stack)) {
            return stack;
        }

        // Delegate to your common inventory insertion logic.
//        return blockEntity.insertItemInternal(slot, stack, simulate);
        return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (!isSlotAccessible(slot) || slot != MateriaIncubatorBlockEntity.SLOT_RESULT) {
            return ItemStack.EMPTY;
        }

        //return blockEntity.extractItemInternal(slot, amount, simulate);
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return isSlotAccessible(slot) && blockEntity.canPlaceItem(slot, stack);
    }

    private boolean isSlotAccessible(int slot) {
        if (direction == null) {
            return true;
        }

        return switch (direction) {
            case UP -> slot == MateriaIncubatorBlockEntity.SLOT_INPUT;
            case DOWN -> slot == MateriaIncubatorBlockEntity.SLOT_RESULT;
            default -> slot == MateriaIncubatorBlockEntity.SLOT_FUEL;
        };
    }
}
