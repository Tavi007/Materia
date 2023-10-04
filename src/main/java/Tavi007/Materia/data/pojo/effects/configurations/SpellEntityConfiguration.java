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
import Tavi007.Materia.data.pojo.effects.configurations.expressions.StringExpression;
import Tavi007.Materia.util.DefaultResourceLocation;
import Tavi007.Materia.util.NetworkHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class SpellEntityConfiguration {

    private StringExpression texture;
    @SerializedName("trail_texture")
    private StringExpression trailTexture;

    private String element;
    private ArithmeticExpression damage;
    private ArithmeticExpression speed;

    private BooleanExpression spawnable;
    private BooleanExpression homing;

    private ArithmeticExpression cooldown;

    // TODO change to CommandConfiguration
    @SerializedName("on_hit_commands")
    private List<String> onHitCommands;
    @SerializedName("on_living_entity_hit_commands")
    private List<String> onLivingEntityHitCommands;
    @SerializedName("on_block_hit_commands")
    private List<String> onBlockHitCommands;

    private ArithmeticExpression repeat;

    private SpellEntityConfiguration() {
        super();
    }

    public SpellEntityEffect computeEffect(List<ItemStack> stacks) {
        return new SpellEntityEffect(
            getTexture().evaluate(stacks),
            getTrailTexture().evaluate(stacks),
            getElement(),
            getDamage().evaluateToFloat(stacks),
            getSpeed().evaluateToFloat(stacks),
            getCooldown().evaluateToInt(stacks),
            getHoming().evaluate(stacks),
            getOnHitCommands(),
            getOnLivingEntityHitCommands(),
            getOnBlockHitCommands(),
            getRepeat().evaluateToInt(stacks));
    }

    public void encode(FriendlyByteBuf buf) {
        getTexture().encode(buf);
        getTrailTexture().encode(buf);
        buf.writeUtf(getElement());
        getDamage().encode(buf);
        getSpeed().encode(buf);
        getCooldown().encode(buf);
        getSpawnable().encode(buf);
        getHoming().encode(buf);
        NetworkHelper.writeStringList(buf, getOnHitCommands());
        NetworkHelper.writeStringList(buf, getOnLivingEntityHitCommands());
        NetworkHelper.writeStringList(buf, getOnBlockHitCommands());
        getRepeat().encode(buf);
    }

    public SpellEntityConfiguration(FriendlyByteBuf buf) {
        texture = new StringExpression(buf);
        trailTexture = new StringExpression(buf);
        element = buf.readUtf();
        damage = new ArithmeticExpression(buf);
        speed = new ArithmeticExpression(buf);
        cooldown = new ArithmeticExpression(buf);
        spawnable = new BooleanExpression(buf);
        homing = new BooleanExpression(buf);
        onHitCommands = NetworkHelper.readStringList(buf);
        onLivingEntityHitCommands = NetworkHelper.readStringList(buf);
        onBlockHitCommands = NetworkHelper.readStringList(buf);
        repeat = new ArithmeticExpression(buf);
    }

    public boolean isValid() {
        return getTexture().isValid()
            && getTrailTexture().isValid()
            && getDamage().isValid()
            && getSpeed().isValid()
            && getSpawnable().isValid()
            && getHoming().isValid()
            && getRepeat().isValid();
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
                && Objects.equals(getCooldown(), otherConfiguration.getCooldown())
                && Objects.equals(getSpawnable(), otherConfiguration.getSpawnable())
                && Objects.equals(getHoming(), otherConfiguration.getHoming())
                && Objects.equals(getOnHitCommands(), otherConfiguration.getOnHitCommands())
                && Objects.equals(getOnLivingEntityHitCommands(), otherConfiguration.getOnLivingEntityHitCommands())
                && Objects.equals(getOnBlockHitCommands(), otherConfiguration.getOnBlockHitCommands())
                && Objects.equals(getRepeat(), otherConfiguration.getRepeat());
        }
        return false;
    }

    private BooleanExpression getSpawnable() {
        return Optional.ofNullable(spawnable).orElse(new BooleanExpression(false));
    }

    public boolean isSpawnable(List<ItemStack> stacks) {
        return getSpawnable().evaluate(stacks);
    }

    private StringExpression getTexture() {
        return Optional.ofNullable(texture)
            .orElse(new StringExpression(null, DefaultResourceLocation.SPELL_TEXTURE.toString()));
    }

    private StringExpression getTrailTexture() {
        return Optional.ofNullable(trailTexture)
            .orElse(new StringExpression(null, DefaultResourceLocation.SPELL_TRAIL_TEXTURE.toString()));
    }

    private String getElement() {
        return Optional.ofNullable(element).orElse(BasePropertiesAPI.getDefaultAttackElement());
    }

    // TODO: do not use configs, because they are not loaded yet
    private ArithmeticExpression getDamage() {
        return Optional.ofNullable(damage).orElse(new ArithmeticExpression(1.0));
        // return Optional.ofNullable(damage).orElse(new ArithmeticExpression(ServerConfig.getDefaultSpellDamage()));
    }

    private ArithmeticExpression getSpeed() {
        return Optional.ofNullable(speed).orElse(new ArithmeticExpression(1.0));
    }

    private ArithmeticExpression getCooldown() {
        return Optional.ofNullable(cooldown).orElse(new ArithmeticExpression(10.0));
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

    private ArithmeticExpression getRepeat() {
        return Optional.ofNullable(repeat).orElse(new ArithmeticExpression(1.0));
    }
}
