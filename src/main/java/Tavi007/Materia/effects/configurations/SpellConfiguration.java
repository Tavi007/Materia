package Tavi007.Materia.effects.configurations;

import java.util.List;

import Tavi007.Materia.effects.Element;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class SpellConfiguration {

    private Element element;
    private Item spellItem;
    private Item sizeItem;

    public SpellConfiguration(Element element, String spellItemRl, String sizeItemRL) {
        this.element = element;
        this.spellItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(spellItemRl));
        this.sizeItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(sizeItemRL));
    }

    public int getSpellLevel(List<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            if (stack.getItem().equals(spellItem)) {
                return CapabilityHelper.getLevelData(stack).level;
            }
        }
        return 0;
    }

    public int getSizeLevel(List<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            if (stack.getItem().equals(sizeItem)) {
                return CapabilityHelper.getLevelData(stack).level;
            }
        }
        return 0;
    }

    public String getElement() {
        return element.getName();
    }
}
