package Tavi007.Materia.common.inventory.containers;

import Tavi007.Materia.common.inventory.slots.MateriaItemSlot;
import Tavi007.Materia.common.items.MateriaItem;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MateriaSlotConnection  {
    private final List<Integer> slotIndices;
    private List<String> effects; // will be updated by the function updateEffects(List<MateriaItemSlot> materiaSlots)

    public MateriaSlotConnection(List<Integer> slotIndices) {
        this.slotIndices = slotIndices;
        effects = new ArrayList<>();
    }


    public void updateEffects(List<MateriaItemSlot> materiaSlots) {
        effects = new ArrayList<>();

        //  get relevant MateriaItems from Container
        List<ItemStack> connectedMateria = slotIndices.stream().map(materiaSlots::get).map(MateriaItemSlot::getItem).toList();
        //Compute Effects

    }

}
