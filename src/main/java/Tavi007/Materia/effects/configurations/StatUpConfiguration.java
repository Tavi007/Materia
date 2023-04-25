package Tavi007.Materia.effects.configurations;

import java.util.List;

import Tavi007.Materia.effects.Stat;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class StatUpConfiguration {

    private Stat stat;
    private Item levelItem;

    public StatUpConfiguration(String itemRl, Stat stat) {
        this.levelItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemRl));
        this.stat = stat;
    }

    public int getLevel(List<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            if (stack.getItem().equals(levelItem)) {
                return CapabilityHelper.getLevelData(stack).level;
            }
        }
        return 0;
    }

    public Stat getStat() {
        return stat;
    }
}
