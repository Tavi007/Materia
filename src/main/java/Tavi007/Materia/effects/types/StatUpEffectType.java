package Tavi007.Materia.effects.types;

import java.util.List;

import Tavi007.Materia.effects.Stat;
import Tavi007.Materia.effects.configurations.LevelConfiguration;
import Tavi007.Materia.effects.configurations.StatConfiguration;
import net.minecraft.world.item.ItemStack;

public class StatUpEffectType<T extends StatConfiguration> extends AbstractMateriaEffectType<T> {

    private Stat stat;
    private LevelConfiguration levelConfiguration;

    public int getLevel(List<ItemStack> stacks) {
        return levelConfiguration.getLevel(stacks);
    }

    public Stat getStat() {
        return stat;
    }
}
