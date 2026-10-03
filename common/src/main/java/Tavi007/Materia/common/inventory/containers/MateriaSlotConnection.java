package Tavi007.Materia.common.inventory.containers;

import Tavi007.Materia.common.items.MateriaItem;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MateriaSlotConnection  {
    private final List<Integer> slotIndices;
    private List<String> effects;

    public MateriaSlotConnection(List<Integer> slotIndices) {
        this.slotIndices = slotIndices;
        effects = new ArrayList<>();
    }

    public void updateEffects(Container container) {
        effects = new ArrayList<>();

        //  get relevant MateriaItems from Container
        List<ItemStack> equippedMateria = new ArrayList<>();
        for (int i : slotIndices) {
            ItemStack stack = container.getItem(i);
            if (stack.getItem() instanceof MateriaItem) {
                equippedMateria.add(stack);
            }
        }

        //Compute Effects

    }

}
