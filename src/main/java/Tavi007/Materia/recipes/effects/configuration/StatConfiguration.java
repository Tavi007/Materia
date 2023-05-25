package Tavi007.Materia.recipes.effects.configuration;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.effects.Stat;
import Tavi007.Materia.recipes.effects.AbstractMateriaEffectConfiguration;
import net.minecraft.world.item.ItemStack;

public class StatConfiguration extends AbstractMateriaEffectConfiguration {

    private Stat stat;
    @SerializedName("level")
    private LevelConfiguration levelConfiguration;

    private StatConfiguration() {
        super();
    }

    public Stat getStat() {
        return stat;
    }

    public int getLevel(List<ItemStack> stacks) {
        return levelConfiguration.getLevel(stacks);
    }

    @Override
    public AbstractMateriaEffectConfiguration copy() {
        StatConfiguration copy = new StatConfiguration();
        copy.setId(getId());
        copy.stat = this.stat;
        copy.levelConfiguration = levelConfiguration.copy();
        return copy;
    }

    @Override
    public boolean isValid() {
        return stat != null && levelConfiguration.isValid();
    }
}
