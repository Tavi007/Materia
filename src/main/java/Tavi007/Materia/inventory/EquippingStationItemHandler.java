package Tavi007.Materia.inventory;

import org.jline.utils.Log;

import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.inventory.menu.EquippingStationMenu;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.util.CapabilityHelper;
import Tavi007.Materia.util.MateriaToolHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class EquippingStationItemHandler extends ItemStackHandler {

    private final EquippingStationMenu menu;

    public EquippingStationItemHandler(EquippingStationMenu menu) {
        super(9);
        this.menu = menu;
    }

    @Override
    public void onContentsChanged(int slot) {
        if (slot >= 0 && slot < 8) {
            // BaseMateria
            ItemStack stack = getStackInSlot(slot);
            if (stack.isEmpty()) {
                // Materia got removed
                CapabilityHelper.getMateriaCollectionHandler(getMateriaToolStack()).setStackInSlot(slot, ItemStack.EMPTY);
            } else {
                // Materia got inserted
                CapabilityHelper.getMateriaCollectionHandler(getMateriaToolStack()).setStackInSlot(slot, stack);
            }
            ItemStack toolStack = getMateriaToolStack();
            CapabilityHelper.getMateriaCollectionHandler(toolStack).computeEffects();
        } else if (slot == 8) {
            // IMateriaTool
            ItemStack stack = getStackInSlot(slot);
            if (stack.isEmpty()) {
                // IMateriaTool got removed
                for (int i = 0; i < 8; i++) {
                    stacks.set(i, ItemStack.EMPTY);
                }
            } else {
                // IMateriaTool got inserted
                MateriaCollectionHandler collection = CapabilityHelper.getMateriaCollectionHandler(stack);
                for (int i = 0; i < collection.getSlots(); i++) {
                    stacks.set(i, collection.getStackInSlot(i));
                }
            }
        } else {
            Log.warn("Slot index is greater than 8. This should not have happened");
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public boolean isItemValid(int slotId, ItemStack stack) {
        return menu.getSlot(slotId).mayPlace(stack);
    }

    public boolean isMateriaSlotEnabled(int slotIndex) {
        ItemStack itemstack = menu.getMateriaToolStack();
        if (itemstack.isEmpty()) {
            return false;
        }
        int noSlots;
        if (slotIndex < 4) {
            noSlots = MateriaToolHelper.getNoCollectionSlot(((IMateriaTool) itemstack.getItem()).getTopCollectionSizes());
            if (slotIndex < noSlots) {
                return true;
            }
        } else {
            noSlots = MateriaToolHelper.getNoCollectionSlot(((IMateriaTool) itemstack.getItem()).getBotCollectionSizes());
            if (slotIndex - 4 < noSlots) {
                return true;
            }
        }
        return false;
    }

    public ItemStack getMateriaToolStack() {
        return stacks.get(8);
    }
}
