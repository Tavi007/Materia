package Tavi007.Materia.recipes.effects;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SpellConfiguration extends AbstractMateriaEffectConfiguration {

    private String element;
    @SerializedName("spell_level")
    private LevelConfiguration levelConfigurationSpell;
    @SerializedName("size_level")
    private LevelConfiguration levelConfigurationSize;

    public SpellConfiguration(ResourceLocation id) {
        super(id);
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
}
