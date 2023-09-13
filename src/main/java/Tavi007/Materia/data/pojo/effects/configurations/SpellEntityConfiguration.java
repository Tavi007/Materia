package Tavi007.Materia.data.pojo.effects.configurations;

import java.util.List;
import java.util.Objects;

import Tavi007.Materia.data.pojo.effects.SpellEntityEffect;
import Tavi007.Materia.data.pojo.effects.configurations.expressions.ArithmeticExpression;
import Tavi007.Materia.data.pojo.effects.configurations.expressions.BooleanExpression;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class SpellEntityConfiguration {

    private String texture;
    private String element;
    private ArithmeticExpression damage;
    private BooleanExpression spawnable;

    private SpellEntityConfiguration() {
        super();
    }

    public float getDamage(List<ItemStack> stacks) {
        return damage.evaluateToFloat(stacks);
    }

    public boolean isSpawnable(List<ItemStack> stacks) {
        return spawnable.evaluate(stacks);
    }

    public SpellEntityEffect computeEffect(List<ItemStack> stacks) {
        return new SpellEntityEffect(texture, element, damage.evaluateToFloat(stacks));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(texture);
        buf.writeUtf(element);
        damage.encode(buf);
        spawnable.encode(buf);
    }

    public SpellEntityConfiguration(FriendlyByteBuf buf) {
        texture = buf.readUtf();
        element = buf.readUtf();
        damage = new ArithmeticExpression(buf);
        spawnable = new BooleanExpression(buf);
    }

    public boolean isValid() {
        return texture != null
            && element != null
            && damage != null && damage.isValid()
            && spawnable != null && spawnable.isValid();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof SpellEntityConfiguration otherConfiguration) {
            return super.equals(otherConfiguration)
                && Objects.equals(texture, otherConfiguration.texture)
                && Objects.equals(element, otherConfiguration.element)
                && Objects.equals(damage, otherConfiguration.damage)
                && Objects.equals(spawnable, otherConfiguration.spawnable);
        }
        return false;
    }
}
