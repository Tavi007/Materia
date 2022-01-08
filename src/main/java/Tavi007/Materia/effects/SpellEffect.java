package Tavi007.Materia.effects;

import java.util.List;

import Tavi007.Materia.Materia;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SpellEffect extends MateriaEffect {

    protected Element element;
    protected Integer spellLevel;

    public SpellEffect(Element element) {
        super(new ResourceLocation(Materia.MOD_ID, element.getName()));
        this.element = element;
    }

    public SpellEffect(SpellEffect effect, Integer level) {
        this(effect.element);
        this.spellLevel = level;
    }

    @Override
    public MateriaEffect initializeCopy(List<ItemStack> itemstacks) {
        if (itemstacks.size() != 1) {
            return null;
        }
        return new SpellEffect(this, CapabilityHelper.getLevelData(itemstacks.get(0)).level);
    }

    @Override
    public String getDefaultTooltip() {
        return element.getTooltip() + " " + spellLevel;
    }

}
