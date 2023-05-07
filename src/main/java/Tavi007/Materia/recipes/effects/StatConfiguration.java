package Tavi007.Materia.recipes.effects;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.effects.Stat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class StatConfiguration extends AbstractMateriaEffectConfiguration {

    private Stat stat;
    @SerializedName("level")
    private LevelConfiguration levelConfiguration;

    public StatConfiguration(ResourceLocation id) {
        super(id);
    }

    public Stat getStat() {
        return stat;
    }

    public int getLevel(List<ItemStack> stacks) {
        return levelConfiguration.getLevel(stacks);
    }
}
