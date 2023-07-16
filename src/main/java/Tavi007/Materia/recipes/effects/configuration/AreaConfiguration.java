package Tavi007.Materia.recipes.effects.configuration;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class AreaConfiguration {

    @SerializedName("width")
    LevelConfiguration levelConfigurationWidth;
    @SerializedName("range")
    LevelConfiguration levelConfigurationRange;
    @SerializedName("height")
    LevelConfiguration levelConfigurationHeight;

    private AreaConfiguration() {
        super();
    }

    public int getWidthLevel(List<ItemStack> stacks) {
        return levelConfigurationWidth.getLevel(stacks);
    }

    public int getRangeLevel(List<ItemStack> stacks) {
        return levelConfigurationRange.getLevel(stacks);
    }

    public int getHeightLevel(List<ItemStack> stacks) {
        return levelConfigurationHeight.getLevel(stacks);
    }

    public AreaConfiguration copy() {
        AreaConfiguration copy = new AreaConfiguration();
        copy.levelConfigurationWidth = levelConfigurationWidth.copy();
        copy.levelConfigurationRange = levelConfigurationRange.copy();
        copy.levelConfigurationHeight = levelConfigurationHeight.copy();
        return copy;
    }

    public boolean isValid() {
        return levelConfigurationWidth.isValid()
            && levelConfigurationRange.isValid()
            && levelConfigurationHeight.isValid();
    }

    public void encode(FriendlyByteBuf buf) {
        levelConfigurationWidth.encode(buf);
        levelConfigurationRange.encode(buf);
        levelConfigurationHeight.encode(buf);
    }

    public AreaConfiguration(FriendlyByteBuf buf) {
        levelConfigurationWidth = new LevelConfiguration(buf);
        levelConfigurationRange = new LevelConfiguration(buf);
        levelConfigurationHeight = new LevelConfiguration(buf);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AreaConfiguration otherConfiguration) {
            return ((levelConfigurationWidth == null && otherConfiguration.levelConfigurationWidth == null)
                || levelConfigurationWidth.equals(otherConfiguration.levelConfigurationWidth))
                && ((levelConfigurationRange == null && otherConfiguration.levelConfigurationRange == null)
                    || levelConfigurationRange.equals(otherConfiguration.levelConfigurationRange))
                && ((levelConfigurationHeight == null && otherConfiguration.levelConfigurationHeight == null)
                    || levelConfigurationHeight.equals(otherConfiguration.levelConfigurationHeight));
        }
        return false;
    }

}
