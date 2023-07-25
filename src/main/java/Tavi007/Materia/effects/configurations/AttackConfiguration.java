package Tavi007.Materia.effects.configurations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class AttackConfiguration extends AbstractMateriaEffectConfiguration {

    private String element;
    @SerializedName("spell_level")
    private LevelConfiguration levelConfigurationSpell;
    @SerializedName("size_level")
    private LevelConfiguration levelConfigurationSize;

    private AttackConfiguration() {
        super();
    }

    public String getElement() {
        return element;
    }

    public int getSpellLevel(List<ItemStack> stacks) {
        return levelConfigurationSpell.getLevel(stacks);
    }

    public int getSizeLevel(List<ItemStack> stacks) {
        return levelConfigurationSize.getLevel(stacks);
    }

    @Override
    public AbstractMateriaEffectConfiguration copy() {
        AttackConfiguration copy = new AttackConfiguration();
        copy.setId(getId());
        copy.setTooltipColor(getTooltipColor());
        copy.element = new String(element);
        copy.levelConfigurationSpell = levelConfigurationSpell.copy();
        copy.levelConfigurationSize = levelConfigurationSize.copy();
        return copy;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        levelConfigurationSpell.encode(buf);
        levelConfigurationSize.encode(buf);
        buf.writeUtf(element);
    }

    public AttackConfiguration(FriendlyByteBuf buf) {
        super(buf);
        levelConfigurationSpell = new LevelConfiguration(buf);
        levelConfigurationSize = new LevelConfiguration(buf);
        element = buf.readUtf();
    }

    @Override
    public boolean isValid() {
        return element != null && levelConfigurationSpell.isValid() && levelConfigurationSize.isValid();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AttackConfiguration otherConfiguration) {
            return super.equals(otherConfiguration)
                && ((levelConfigurationSpell == null && otherConfiguration.levelConfigurationSpell == null)
                    || levelConfigurationSpell.equals(otherConfiguration.levelConfigurationSpell))
                && ((levelConfigurationSize == null && otherConfiguration.levelConfigurationSize == null)
                    || levelConfigurationSize.equals(otherConfiguration.levelConfigurationSize))
                && ((element == null && otherConfiguration == null)
                    || element.equals(otherConfiguration.element));
        }
        return false;
    }
}
