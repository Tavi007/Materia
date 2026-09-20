package Tavi007.Materia.data.pojo.effects.configurations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.data.pojo.effects.SpellEffect;
import Tavi007.Materia.data.pojo.effects.SpellEntityEffect;
import Tavi007.Materia.util.NetworkHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class SpellConfiguration extends AbstractMateriaEffectConfiguration {

    @SerializedName("message_id")
    private String messageId;
    private List<SpellEntityConfiguration> spells;

    private SpellConfiguration() {
        super();
    }

    @Override
    public SpellEffect computeEffect(List<ItemStack> stacks) {
        List<SpellEntityEffect> entityEffects = new ArrayList<>();
        spells.stream()
            .filter(spell -> spell.isSpawnable(stacks))
            .forEach(spell -> entityEffects.add(spell.computeEffect(stacks)));
        return new SpellEffect(getId(), getTooltipColor(), messageId, entityEffects);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        buf.writeUtf(messageId);
        NetworkHelper.writeSpellEntityConfigurationList(buf, spells);
    }

    public SpellConfiguration(FriendlyByteBuf buf) {
        super(buf);
        messageId = buf.readUtf();
        spells = NetworkHelper.readSpellEntityConfigurationList(buf);
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
        return areSpellsValid;
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
                && Objects.equals(spells, otherConfiguration.spells);
        }
        return false;
    }
}
