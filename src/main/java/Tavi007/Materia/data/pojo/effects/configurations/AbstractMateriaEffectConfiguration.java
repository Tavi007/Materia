package Tavi007.Materia.data.pojo.effects.configurations;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.data.pojo.effects.AbstractMateriaEffect;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractMateriaEffectConfiguration {

    private ResourceLocation id;
    @SerializedName("tooltip_color")
    private Integer tooltipColor;

    protected AbstractMateriaEffectConfiguration() {
    }

    public abstract AbstractMateriaEffect computeEffect(List<ItemStack> stacks);

    public abstract boolean isValid();

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    protected void setTooltipColor(Integer tooltipColor) {
        this.tooltipColor = tooltipColor;
    }

    protected int getTooltipColor() {
        return tooltipColor;
    }

    public ResourceLocation getId() {
        return id;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(id);
        buf.writeInt(tooltipColor);
    };

    public AbstractMateriaEffectConfiguration(FriendlyByteBuf buf) {
        id = buf.readResourceLocation();
        tooltipColor = buf.readInt();
    };

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof AbstractMateriaEffectConfiguration otherConfiguration) {
            return Objects.equals(id, otherConfiguration.id)
                && Objects.equals(tooltipColor, otherConfiguration.tooltipColor);
        }
        return false;
    }

}
