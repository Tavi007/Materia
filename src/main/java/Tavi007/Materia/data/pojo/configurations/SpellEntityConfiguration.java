package Tavi007.Materia.data.pojo.configurations;

import java.util.List;
import java.util.Objects;

import Tavi007.Materia.data.pojo.configurations.expressions.ArithmeticExpression;
import Tavi007.Materia.data.pojo.configurations.expressions.BooleanExpression;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class SpellEntityConfiguration {

    private String msgId;
    private String texture;
    private String element;
    private ArithmeticExpression damage;
    private BooleanExpression spawnable;

    private SpellEntityConfiguration() {
        super();
    }

    public String getMsgId() {
        return msgId;
    }

    public String getTexture() {
        return texture;
    }

    public String getElement() {
        return element;
    }

    public float getDamage(List<ItemStack> stacks) {
        return damage.evaluateToFloat(stacks);
    }

    public boolean isSpawnable(List<ItemStack> stacks) {
        return spawnable.evaluate(stacks);
    }

    public SpellEntityConfiguration copy() {
        SpellEntityConfiguration copy = new SpellEntityConfiguration();
        copy.msgId = msgId;
        copy.texture = texture;
        copy.element = element;
        copy.damage = damage.copy();
        copy.spawnable = spawnable.copy();
        return copy;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(msgId);
        buf.writeUtf(texture);
        buf.writeUtf(element);
        damage.encode(buf);
        spawnable.encode(buf);
    }

    public SpellEntityConfiguration(FriendlyByteBuf buf) {
        msgId = buf.readUtf();
        texture = buf.readUtf();
        element = buf.readUtf();
        damage = new ArithmeticExpression(buf);
        spawnable = new BooleanExpression(buf);
    }

    public boolean isValid() {
        return msgId != null
            && texture != null
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
                && Objects.equals(msgId, otherConfiguration.msgId)
                && Objects.equals(texture, otherConfiguration.texture)
                && Objects.equals(element, otherConfiguration.element)
                && Objects.equals(damage, otherConfiguration.damage)
                && Objects.equals(spawnable, otherConfiguration.spawnable);
        }
        return false;
    }
}
