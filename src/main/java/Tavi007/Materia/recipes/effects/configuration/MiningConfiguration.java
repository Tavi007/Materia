package Tavi007.Materia.recipes.effects.configuration;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class MiningConfiguration extends AbstractMateriaEffectConfiguration {

    @SerializedName("area")
    AreaConfiguration areaConfiguration;
    @SerializedName("vein_miner")
    Boolean veinMiner;

    private MiningConfiguration() {
        super();
    }

    public int getWidthLevel(List<ItemStack> stacks) {
        return areaConfiguration.getWidthLevel(stacks);
    }

    public int getRangeLevel(List<ItemStack> stacks) {
        return areaConfiguration.getRangeLevel(stacks);
    }

    public int getHeightLevel(List<ItemStack> stacks) {
        return areaConfiguration.getHeightLevel(stacks);
    }

    public boolean hashVeinMiner() {
        return veinMiner;
    }

    @Override
    public AbstractMateriaEffectConfiguration copy() {
        MiningConfiguration copy = new MiningConfiguration();
        copy.setId(getId());
        copy.setTooltipColor(getTooltipColor());
        copy.areaConfiguration = areaConfiguration.copy();
        copy.veinMiner = veinMiner;
        return copy;
    }

    @Override
    public boolean isValid() {
        return veinMiner != null
            && areaConfiguration.isValid();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        areaConfiguration.encode(buf);
        buf.writeBoolean(veinMiner);
    }

    public MiningConfiguration(FriendlyByteBuf buf) {
        super(buf);
        areaConfiguration = new AreaConfiguration(buf);
        veinMiner = buf.readBoolean();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof MiningConfiguration otherConfiguration) {
            return super.equals(otherConfiguration)
                && ((areaConfiguration == null && otherConfiguration.areaConfiguration == null)
                    || areaConfiguration.equals(otherConfiguration.areaConfiguration))
                && ((veinMiner == null && otherConfiguration.veinMiner == null)
                    || veinMiner.equals(otherConfiguration.veinMiner));
        }
        return false;
    }
}
