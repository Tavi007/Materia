package Tavi007.Materia.data.pojo.configurations;

import java.util.List;

import Tavi007.Materia.data.pojo.configurations.expressions.ArithmeticEvaluator;
import Tavi007.Materia.data.pojo.configurations.expressions.Expression;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class AttackConfiguration extends AbstractMateriaEffectConfiguration {

    private String element;
    private Expression damage;
    private AreaConfiguration area;

    private AttackConfiguration() {
        super();
    }

    public String getElement() {
        return element;
    }

    public double getDamage(List<ItemStack> stacks) {
        return (int) Math.round(new ArithmeticEvaluator(damage.getFinalExpression(stacks)).parseArithmetic());
    }

    @Override
    public AbstractMateriaEffectConfiguration copy() {
        AttackConfiguration copy = new AttackConfiguration();
        copy.setId(getId());
        copy.setTooltipColor(getTooltipColor());
        copy.element = new String(element);
        copy.damage = damage.copy();
        copy.area = area.copy();
        return copy;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        damage.encode(buf);
        area.encode(buf);
        buf.writeUtf(element);
    }

    public AttackConfiguration(FriendlyByteBuf buf) {
        super(buf);
        damage = new Expression(buf);
        area = new AreaConfiguration(buf);
        element = buf.readUtf();
    }

    @Override
    public boolean isValid() {
        return element != null && damage.isValid() && area.isValid();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AttackConfiguration otherConfiguration) {
            return super.equals(otherConfiguration)
                && ((damage == null && otherConfiguration.damage == null)
                    || damage.equals(otherConfiguration.damage))
                && ((area == null && otherConfiguration.area == null)
                    || area.equals(otherConfiguration.area))
                && ((element == null && otherConfiguration == null)
                    || element.equals(otherConfiguration.element));
        }
        return false;
    }
}
