package Tavi007.Materia.common.inventory.slots;

import Tavi007.Materia.common.inventory.containers.MateriaIncubatorContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class IncubatorFuelSlot extends Slot {
    private final int materiaSlotId;

    public IncubatorFuelSlot(MateriaIncubatorContainer container, int materiaSlotId, int slot, int x, int y) {
        super(container, slot, x, y);
        this.materiaSlotId = materiaSlotId;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        ItemStack materiaStack = container.getItem(materiaSlotId);
        if(materiaStack.isEmpty()) {
            return false;
        }
        // TODO: look up the allowed ItemTag connected to this item
        return true;
    }

}
