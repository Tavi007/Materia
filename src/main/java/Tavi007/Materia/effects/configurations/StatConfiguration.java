package Tavi007.Materia.effects.configurations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.effects.Stat;
import net.minecraft.network.FriendlyByteBuf;
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
        copy.setTooltipColor(getTooltipColor());
        copy.stat = this.stat;
        copy.levelConfiguration = levelConfiguration.copy();
        return copy;
    }

    @Override
    public boolean isValid() {
        return stat != null && levelConfiguration.isValid();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        levelConfiguration.encode(buf);
        buf.writeEnum(stat);
    }

    public StatConfiguration(FriendlyByteBuf buf) {
        super(buf);
        levelConfiguration = new LevelConfiguration(buf);
        stat = buf.readEnum(Stat.class);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof StatConfiguration otherConfiguration) {
            return super.equals(otherConfiguration)
                && ((levelConfiguration == null && otherConfiguration.levelConfiguration == null)
                    || levelConfiguration.equals(otherConfiguration.levelConfiguration))
                && ((stat == null && otherConfiguration.stat == null)
                    || stat.equals(otherConfiguration.stat));
        }
        return false;
    }
}
