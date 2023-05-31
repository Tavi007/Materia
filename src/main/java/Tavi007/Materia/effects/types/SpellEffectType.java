package Tavi007.Materia.effects.types;

import java.util.List;

import Tavi007.Materia.recipes.effects.configuration.AttackConfiguration;
import net.minecraft.world.item.ItemStack;

public class SpellEffectType<T extends AttackConfiguration> extends AbstractMateriaEffectType<T> {

    public int getSpellLevel(List<ItemStack> stacks, T configuration) {
        return configuration.getSpellLevel(stacks);
    }

    public int getSizeLevel(List<ItemStack> stacks, T configuration) {
        return configuration.getSizeLevel(stacks);
    }

    public String getElement(T configuration) {
        return configuration.getElement();
    }
}
