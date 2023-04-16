package Tavi007.Materia.effects;

import java.util.List;

import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.world.item.ItemStack;

public class AreaSpellEffect extends SpellEffect implements IAreaEffect {

    private Integer areaLevel;

    public AreaSpellEffect(Element element) {
        super(element);
    }

    public AreaSpellEffect(AreaSpellEffect effect, Integer areaLevel, Integer spellLevel) {
        super(effect, spellLevel);
        this.areaLevel = areaLevel;
    }

    @Override
    public MateriaEffect initializeCopy(List<ItemStack> itemstacks) {
        if (itemstacks.size() != 2) {
            return null;
        }
        Integer level0 = CapabilityHelper.getLevelData(itemstacks.get(0)).level;
        Integer level1 = CapabilityHelper.getLevelData(itemstacks.get(1)).level;

        return new AreaSpellEffect(this, level0, level1);
    }

    @Override
    public int getAreaLevel() {
        return areaLevel;
    }

    @Override
    public String getDefaultTooltip() {
        return element.getTooltip() + " " + spellLevel + " " + areaLevel;
    }

}
