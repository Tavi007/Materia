package Tavi007.Materia.recipes.effects;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LevelConfiguration {

    int base = 1;
    List<Item> add = new ArrayList<>();
    List<Item> subtract = new ArrayList<>();

    public LevelConfiguration copy() {
        LevelConfiguration copy = new LevelConfiguration();
        copy.base = this.base;
        copy.add = new ArrayList<>();
        copy.add.addAll(this.add);
        copy.subtract = new ArrayList<>();
        copy.subtract.addAll(this.subtract);
        return copy;
    }

    public int getLevel(List<ItemStack> stacks) {
        int result = base;
        for (ItemStack stack : stacks) {
            if (add.contains(stack.getItem())) {
                base += CapabilityHelper.getLevelData(stack).level;
            }
            if (subtract.contains(stack.getItem())) {
                base -= CapabilityHelper.getLevelData(stack).level;
            }
        }
        return Math.min(1, result);
    }
}
