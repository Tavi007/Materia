package Tavi007.Materia.effects.configurations;

import java.util.List;

import com.google.gson.JsonObject;

import Tavi007.Materia.effects.Stat;
import net.minecraft.world.item.ItemStack;

public class StatUpConfiguration {

    private Stat stat;
    private LevelConfiguration levelConfiguration;

    // TODO make NullPointer Safe
    public StatUpConfiguration(JsonObject json) {
        this.stat = Stat.valueOf(json.get("stat").getAsString());
        this.levelConfiguration = new LevelConfiguration(json.getAsJsonObject("level"));
    }

    public StatUpConfiguration(Stat stat, LevelConfiguration levelConfiguration) {
        this.stat = stat;
        this.levelConfiguration = levelConfiguration;
    }

    public int getLevel(List<ItemStack> stacks) {
        return levelConfiguration.getLevel(stacks);
    }

    public Stat getStat() {
        return stat;
    }
}
