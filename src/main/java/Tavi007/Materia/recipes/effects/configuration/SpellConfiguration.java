package Tavi007.Materia.recipes.effects.configuration;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.recipes.effects.AbstractMateriaEffectConfiguration;
import net.minecraft.world.item.ItemStack;

public class SpellConfiguration extends AbstractMateriaEffectConfiguration {

    private String element;
    @SerializedName("spell_level")
    private LevelConfiguration levelConfigurationSpell;
    @SerializedName("size_level")
    private LevelConfiguration levelConfigurationSize;

    private SpellConfiguration() {
        super();
    }

    public String getElement() {
        return element;
    }

    public int getSpellLevel(List<ItemStack> stacks) {
        return levelConfigurationSpell.getLevel(stacks);
    }

    public int getSizeLevel(List<ItemStack> stacks) {
        return levelConfigurationSize.getLevel(stacks);
    }

    @Override
    public AbstractMateriaEffectConfiguration copy() {
        SpellConfiguration copy = new SpellConfiguration();
        copy.setId(getId());
        copy.element = new String(element);
        copy.levelConfigurationSpell = levelConfigurationSpell.copy();
        copy.levelConfigurationSize = levelConfigurationSize.copy();
        return copy;
    }

    @Override
    public boolean isValid() {
        return element != null && levelConfigurationSpell.isValid() && levelConfigurationSize.isValid();
    }
}
