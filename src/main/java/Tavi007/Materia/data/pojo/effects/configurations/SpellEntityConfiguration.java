package Tavi007.Materia.data.pojo.effects.configurations;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.data.pojo.effects.SpellEntityEffect;
import Tavi007.Materia.data.pojo.effects.configurations.expressions.ArithmeticExpression;
import Tavi007.Materia.data.pojo.effects.configurations.expressions.BooleanExpression;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class SpellEntityConfiguration {

    private String texture;
    @SerializedName("trail_texture")
    private String trailTexture;
    private String element;
    private ArithmeticExpression damage;
    private ArithmeticExpression speed;
    private BooleanExpression spawnable;

    private SpellEntityConfiguration() {
        super();
    }

    public boolean isSpawnable(List<ItemStack> stacks) {
        return spawnable.evaluate(stacks);
    }

    public SpellEntityEffect computeEffect(List<ItemStack> stacks) {
        return new SpellEntityEffect(texture, trailTexture, element, damage.evaluateToFloat(stacks), speed.evaluateToFloat(stacks));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(texture);
        buf.writeUtf(trailTexture);
        buf.writeUtf(element);
        damage.encode(buf);
        speed.encode(buf);
        spawnable.encode(buf);
    }

    public SpellEntityConfiguration(FriendlyByteBuf buf) {
        texture = buf.readUtf();
        trailTexture = buf.readUtf();
        element = buf.readUtf();
        damage = new ArithmeticExpression(buf);
        speed = new ArithmeticExpression(buf);
        spawnable = new BooleanExpression(buf);
    }

    public boolean isValid() {
        return texture != null
            && element != null
            && trailTexture != null
            && damage != null && damage.isValid()
            && speed != null && speed.isValid()
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
                && Objects.equals(trailTexture, otherConfiguration.trailTexture)
                && Objects.equals(element, otherConfiguration.element)
                && Objects.equals(damage, otherConfiguration.damage)
                && Objects.equals(speed, otherConfiguration.speed)
                && Objects.equals(spawnable, otherConfiguration.spawnable);
        }
        return false;
    }
}
