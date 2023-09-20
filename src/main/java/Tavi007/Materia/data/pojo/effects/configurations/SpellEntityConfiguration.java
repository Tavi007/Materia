package Tavi007.Materia.data.pojo.effects.configurations;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.google.gson.annotations.SerializedName;

import Tavi007.ElementalCombat.api.BasePropertiesAPI;
import Tavi007.Materia.data.pojo.effects.SpellEntityEffect;
import Tavi007.Materia.data.pojo.effects.configurations.expressions.ArithmeticExpression;
import Tavi007.Materia.data.pojo.effects.configurations.expressions.BooleanExpression;
import Tavi007.Materia.util.DefaultResourceLocation;
import Tavi007.Materia.util.NetworkHelper;
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
            getTexture(),
            getTrailTexture(),
            getElement(),
            getDamage().evaluateToFloat(stacks),
            getSpeed().evaluateToFloat(stacks),
            getHoming().evaluate(stacks),
            getOnHitCommands(),
            getOnLivingEntityHitCommands(),
            getOnBlockHitCommands());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(getTexture());
        buf.writeUtf(getTrailTexture());
        buf.writeUtf(getElement());
        getDamage().encode(buf);
        getSpeed().encode(buf);
        getSpawnable().encode(buf);
        getHoming().encode(buf);
        NetworkHelper.writeStringList(buf, getOnHitCommands());
        NetworkHelper.writeStringList(buf, getOnLivingEntityHitCommands());
        NetworkHelper.writeStringList(buf, getOnBlockHitCommands());
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
        return getDamage().isValid() && getSpeed().isValid() && getSpawnable().isValid() && getHoming().isValid();
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
                && Objects.equals(getTexture(), otherConfiguration.getTexture())
                && Objects.equals(getTrailTexture(), otherConfiguration.getTrailTexture())
                && Objects.equals(getElement(), otherConfiguration.getElement())
                && Objects.equals(getDamage(), otherConfiguration.getDamage())
                && Objects.equals(getSpeed(), otherConfiguration.getSpeed())
                && Objects.equals(getSpawnable(), otherConfiguration.getSpawnable())
                && Objects.equals(getHoming(), otherConfiguration.getHoming())
                && Objects.equals(getOnHitCommands(), otherConfiguration.getOnHitCommands())
                && Objects.equals(getOnLivingEntityHitCommands(), otherConfiguration.getOnLivingEntityHitCommands())
                && Objects.equals(getOnBlockHitCommands(), otherConfiguration.getOnBlockHitCommands());
        }
        return false;
    }

    private BooleanExpression getSpawnable() {
        return Optional.ofNullable(spawnable).orElse(new BooleanExpression(false));
    }

    public boolean isSpawnable(List<ItemStack> stacks) {
        return getSpawnable().evaluate(stacks);
    }

    private String getTexture() {
        return Optional.ofNullable(texture).orElse(DefaultResourceLocation.SPELL_TEXTURE.toString());
    }

    private String getTrailTexture() {
        return Optional.ofNullable(trailTexture).orElse(DefaultResourceLocation.SPELL_TRAIL_TEXTURE.toString());
    }

    private String getElement() {
        return Optional.ofNullable(element).orElse(BasePropertiesAPI.getDefaultAttackElement());
    }

    // TODO get default values from server config
    private ArithmeticExpression getDamage() {
        return Optional.ofNullable(damage).orElse(new ArithmeticExpression(1.0));
    }

    private ArithmeticExpression getSpeed() {
        return Optional.ofNullable(speed).orElse(new ArithmeticExpression(1.0));
    }

    private BooleanExpression getHoming() {
        return Optional.ofNullable(homing).orElse(new BooleanExpression(false));
    }

    private List<String> getOnHitCommands() {
        return Optional.ofNullable(onHitCommands).orElse(Collections.emptyList());
    }

    private List<String> getOnLivingEntityHitCommands() {
        return Optional.ofNullable(onLivingEntityHitCommands).orElse(Collections.emptyList());
    }

    private List<String> getOnBlockHitCommands() {
        return Optional.ofNullable(onBlockHitCommands).orElse(Collections.emptyList());
    }
}
