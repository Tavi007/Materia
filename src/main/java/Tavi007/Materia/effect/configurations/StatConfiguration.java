package Tavi007.Materia.effect.configurations;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.effect.configurations.expressions.ArithmeticExpression;
import net.minecraft.network.FriendlyByteBuf;

public class StatConfiguration extends AbstractMateriaEffectConfiguration {

    private Stat stat;
    @SerializedName("value")
    private ArithmeticExpression value;

    private StatConfiguration() {
        super();
    }

    public Stat getStat() {
        return stat;
    }

    @Override
    public AbstractMateriaEffectConfiguration copy() {
        StatConfiguration copy = new StatConfiguration();
        copy.setId(getId());
        copy.setTooltipColor(getTooltipColor());
        copy.stat = this.stat;
        copy.value = value.copy();
        return copy;
    }

    @Override
    public boolean isValid() {
        return stat != null && value.isValid();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        value.encode(buf);
        buf.writeEnum(stat);
    }

    public StatConfiguration(FriendlyByteBuf buf) {
        super(buf);
        value = new ArithmeticExpression(buf);
        stat = buf.readEnum(Stat.class);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof StatConfiguration otherConfiguration) {
            return super.equals(otherConfiguration)
                && ((value == null && otherConfiguration.value == null)
                    || value.equals(otherConfiguration.value))
                && ((stat == null && otherConfiguration.stat == null)
                    || stat.equals(otherConfiguration.stat));
        }
        return false;
    }
}
