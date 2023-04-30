package Tavi007.Materia.capabilities.toolslots;

import javax.annotation.Nonnull;

import Tavi007.Materia.items.MateriaItem;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class MateriaCollection extends ItemStackHandler {

    private Boolean dirty = false;

    private final static int maxItemStackSlots = 8;

    public MateriaCollection() {
        super(maxItemStackSlots);
    }

    public boolean isDirty() {
        return dirty;
    }

    public void markCleaned() {
        dirty = false;
    }

    public void markDirty() {
        dirty = true;
    }

    public void addAp(int ap) {
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                int[] apToNextLevel = ((MateriaItem) stack.getItem()).getApToNextLevel();
                CapabilityHelper.getLevelData(stack).addAP(ap, apToNextLevel);
            }
        }
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return stack.getItem() instanceof MateriaItem;
    }
}
