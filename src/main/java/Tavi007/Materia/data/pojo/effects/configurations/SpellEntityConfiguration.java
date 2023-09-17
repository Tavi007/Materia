package Tavi007.Materia.data.pojo.effects.configurations;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.data.pojo.effects.SpellEntityEffect;
import Tavi007.Materia.data.pojo.effects.configurations.expressions.ArithmeticExpression;
import Tavi007.Materia.data.pojo.effects.configurations.expressions.BooleanExpression;
import Tavi007.Materia.util.NetworkHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

//TODO make nullpointer proof. isValid method might be unnecessary.
public class SpellEntityConfiguration {

    private String texture;
    @SerializedName("trail_texture")
    private String trailTexture;

    private String element;
    private ArithmeticExpression damage;
    private ArithmeticExpression speed;

    private BooleanExpression spawnable;
    private BooleanExpression homing;

    // TODO change to CommandConfiguration
    @SerializedName("on_hit_commands")
    private List<String> onHitCommands;
    @SerializedName("on_living_entity_hit_commands")
    private List<String> onLivingEntityHitCommands;
    @SerializedName("on_block_hit_commands")
    private List<String> onBlockHitCommands;

    private SpellEntityConfiguration() {
        super();
    }

    public SpellEntityEffect computeEffect(List<ItemStack> stacks) {
        return new SpellEntityEffect(
            texture,
            trailTexture,
            element,
            damage.evaluateToFloat(stacks),
            speed.evaluateToFloat(stacks),
            homing.evaluate(stacks),
            onHitCommands,
            onLivingEntityHitCommands,
            onBlockHitCommands);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(texture);
        buf.writeUtf(trailTexture);
        buf.writeUtf(element);
        damage.encode(buf);
        speed.encode(buf);
        spawnable.encode(buf);
        homing.encode(buf);
        NetworkHelper.writeStringList(buf, onHitCommands);
        NetworkHelper.writeStringList(buf, onLivingEntityHitCommands);
        NetworkHelper.writeStringList(buf, onBlockHitCommands);
    }

    public SpellEntityConfiguration(FriendlyByteBuf buf) {
        texture = buf.readUtf();
        trailTexture = buf.readUtf();
        element = buf.readUtf();
        damage = new ArithmeticExpression(buf);
        speed = new ArithmeticExpression(buf);
        spawnable = new BooleanExpression(buf);
        homing = new BooleanExpression(buf);
        onHitCommands = NetworkHelper.readStringList(buf);
        onLivingEntityHitCommands = NetworkHelper.readStringList(buf);
        onBlockHitCommands = NetworkHelper.readStringList(buf);
    }

    public boolean isValid() {
        return texture != null
            && element != null
            && trailTexture != null
            && damage != null && damage.isValid()
            && speed != null && speed.isValid()
            && spawnable != null && spawnable.isValid()
            && homing != null && homing.isValid()
            && onHitCommands != null
            && onLivingEntityHitCommands != null
            && onBlockHitCommands != null;
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
                && Objects.equals(spawnable, otherConfiguration.spawnable)
                && Objects.equals(homing, otherConfiguration.homing)
                && Objects.equals(onHitCommands, otherConfiguration.onHitCommands)
                && Objects.equals(onLivingEntityHitCommands, otherConfiguration.onLivingEntityHitCommands)
                && Objects.equals(onBlockHitCommands, otherConfiguration.onBlockHitCommands);
        }
        return false;
    }

    public boolean isSpawnable(List<ItemStack> stacks) {
        return spawnable.evaluate(stacks);
    }
}
