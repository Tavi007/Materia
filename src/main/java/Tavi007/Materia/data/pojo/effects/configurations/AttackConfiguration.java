package Tavi007.Materia.data.pojo.effects.configurations;

import java.util.List;
import java.util.Objects;

import Tavi007.Materia.data.pojo.effects.AttackEffect;
import Tavi007.Materia.data.pojo.effects.configurations.expressions.ArithmeticExpression;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class AttackConfiguration extends AbstractMateriaEffectConfiguration {

    private String element;
    private ArithmeticExpression damage;
    private AreaConfiguration area;

    private AttackConfiguration() {
        super();
    }

    @Override
    public AttackEffect computeEffect(List<ItemStack> stacks) {
        return new AttackEffect(getId(), getTooltipColor(), element, damage.evaluateToFloat(stacks), area.computeEffect(stacks));
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
        damage = new ArithmeticExpression(buf);
        area = new AreaConfiguration(buf);
        element = buf.readUtf();
    }

    @Override
    public boolean isValid() {
        return element != null
            && damage != null && damage.isValid()
            && area != null && area.isValid();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof AttackConfiguration otherConfiguration) {
            return super.equals(otherConfiguration)
                && Objects.equals(damage, otherConfiguration.damage)
                && Objects.equals(area, otherConfiguration.area)
                && Objects.equals(element, otherConfiguration.element);
        }
        return false;
    }
}
