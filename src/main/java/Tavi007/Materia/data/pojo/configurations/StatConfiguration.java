package Tavi007.Materia.data.pojo.configurations;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.data.pojo.configurations.expressions.ArithmeticExpression;
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
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof StatConfiguration otherConfiguration) {
            return super.equals(otherConfiguration)
                && Objects.equals(value, otherConfiguration.value)
                && Objects.equals(stat, otherConfiguration.stat);
        }
        return false;
    }
}
