package Tavi007.Materia.inventory.menu;

import javax.annotation.Nonnull;

import Tavi007.Materia.inventory.EquippingStationItemHandler;
import Tavi007.Materia.items.MateriaItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MateriaContainerSlot extends SlotItemHandler {

    private final int slotIndex;

    public MateriaContainerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.slotIndex = index;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        if (getItemHandler() instanceof EquippingStationItemHandler) {
            return stack.getItem() instanceof MateriaItem && ((EquippingStationItemHandler) getItemHandler()).isMateriaSlotEnabled(slotIndex);
        }
        return stack.getItem() instanceof MateriaItem;
    }

    @Override
    public void set(@Nonnull ItemStack stack) {
        if (getItemHandler().isItemValid(slotIndex, stack)) {
            getItemHandler().insertItem(slotIndex, stack, false);
        }
    }

    // @Override
    // @OnlyIn(Dist.CLIENT)
    // public boolean isEnabled() {
    // if (getItemHandler() instanceof EquippingStationItemHandler) {
    // return ((EquippingStationItemHandler) getItemHandler()).isMateriaSlotEnabled(slotIndex);
    // } else if (getItemHandler() instanceof MateriaIncubatorItemHandler) {
    // return true;
    // }
    // return false;
    // }
}
