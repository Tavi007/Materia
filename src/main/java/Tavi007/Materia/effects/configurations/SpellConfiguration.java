package Tavi007.Materia.effects.configurations;

import java.util.List;

import com.google.gson.JsonObject;

import Tavi007.Materia.effects.Element;
import net.minecraft.world.item.ItemStack;

public class SpellConfiguration {

    private Element element;
    private LevelConfiguration levelConfigurationSpell;
    private LevelConfiguration levelConfigurationSize;

    // TODO make NullPointer Safe
    public SpellConfiguration(JsonObject json) {
        this.element = Element.valueOf(json.get("element").getAsString());
        this.levelConfigurationSpell = new LevelConfiguration(json.getAsJsonObject("spell_level"));
        this.levelConfigurationSize = new LevelConfiguration(json.getAsJsonObject("size_level"));
    }

    public SpellConfiguration(Element element, LevelConfiguration levelConfigurationSpell, LevelConfiguration levelConfigurationSize) {
        this.element = element;
        this.levelConfigurationSpell = levelConfigurationSpell;
        this.levelConfigurationSize = levelConfigurationSize;
    }

    public int getSpellLevel(List<ItemStack> stacks) {
        return levelConfigurationSpell.getLevel(stacks);
    }

    public int getSizeLevel(List<ItemStack> stacks) {
        return levelConfigurationSize.getLevel(stacks);
    }

    public String getElement() {
        return element.getName();
    }
}
