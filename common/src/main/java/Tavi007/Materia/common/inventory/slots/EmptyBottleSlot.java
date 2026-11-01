package Tavi007.Materia.common.inventory.slots;

import Tavi007.Materia.common.items.ParaMagicPhial;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EmptyBottleSlot extends Slot {

    public EmptyBottleSlot(
            Container container,
            int slot,
            int x,
            int y
    ) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return mayPlaceItem(stack);
    }

    public static boolean mayPlaceItem(ItemStack stack) {return stack.is(Items.GLASS_BOTTLE);}
}
