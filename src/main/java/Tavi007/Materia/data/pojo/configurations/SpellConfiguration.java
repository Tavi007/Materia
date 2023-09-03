package Tavi007.Materia.data.pojo.configurations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.data.pojo.configurations.expressions.ArithmeticExpression;
import Tavi007.Materia.util.NetworkHelper;
import net.minecraft.network.FriendlyByteBuf;

public class SpellConfiguration extends AbstractMateriaEffectConfiguration {

    private List<SpellEntityConfiguration> spells;
    @SerializedName("spell_delay")
    private ArithmeticExpression spellDelay;
    private ArithmeticExpression cooldown;

    private SpellConfiguration() {
        super();
    }

    @Override
    public SpellConfiguration copy() {
        SpellConfiguration copy = new SpellConfiguration();
        copy.setId(getId());
        copy.setTooltipColor(getTooltipColor());

        copy.spells = new ArrayList<>();
        spells.forEach(spell -> copy.spells.add(spell.copy()));

        copy.spellDelay = spellDelay.copy();
        copy.cooldown = cooldown.copy();
        return copy;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        NetworkHelper.writeSpellEntityConfigurationList(buf, spells);
        spellDelay.encode(buf);
        cooldown.encode(buf);
    }

    public SpellConfiguration(FriendlyByteBuf buf) {
        super(buf);
        spells = NetworkHelper.readSpellEntityConfigurationList(buf);
        spellDelay = new ArithmeticExpression(buf);
        cooldown = new ArithmeticExpression(buf);
    }

    @Override
    public boolean isValid() {
        boolean areSpellsValid = true;
        for (SpellEntityConfiguration spell : spells) {
            if (spell == null || !spell.isValid()) {
                areSpellsValid = false;
                break;
            }
        }
        return areSpellsValid
            && spellDelay != null && spellDelay.isValid()
            && cooldown != null && cooldown.isValid();
    }

    public List<SpellEntityConfiguration> getEntityConfigurations() {
        return spells;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof SpellConfiguration otherConfiguration) {
            return super.equals(otherConfiguration)
                && Objects.equals(spells, otherConfiguration.spells)
                && Objects.equals(spellDelay, otherConfiguration.spellDelay)
                && Objects.equals(cooldown, otherConfiguration.cooldown);
        }
        return false;
    }
}
