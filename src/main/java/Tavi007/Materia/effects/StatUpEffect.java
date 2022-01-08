package Tavi007.Materia.effects;

import java.util.List;

import Tavi007.Materia.Materia;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class StatUpEffect extends MateriaEffect {

    private Stat stat;
    private Integer statLevel;

    public StatUpEffect(Stat stat) {
        super(new ResourceLocation(Materia.MOD_ID, stat.toString().toLowerCase()));
        this.stat = stat;
    }

    public StatUpEffect(StatUpEffect effect, Integer statLevel) {
        this(effect.stat);
        this.statLevel = statLevel;
    }

    @Override
    public MateriaEffect initializeCopy(List<ItemStack> itemstacks) {
        if (itemstacks.size() != 1) {
            return null;
        }
        return new StatUpEffect(this, CapabilityHelper.getLevelData(itemstacks.get(0)).level);
    }

    @Override
    public String getDefaultTooltip() {
        return stat.getTooltip() + " " + statLevel;
    }

    public boolean isMiningSpeed() {
        return stat.equals(Stat.MINING_SPEED);
    }

    public int getLevel() {
        return statLevel;
    }

}
