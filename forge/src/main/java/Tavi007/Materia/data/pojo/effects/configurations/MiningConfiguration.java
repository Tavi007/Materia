package Tavi007.Materia.data.pojo.effects.configurations;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.data.pojo.effects.MiningEffect;
import Tavi007.Materia.data.pojo.effects.configurations.expressions.BooleanExpression;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class MiningConfiguration extends AbstractMateriaEffectConfiguration {

    @SerializedName("area")
    AreaConfiguration areaConfiguration;
    @SerializedName("vein_miner")
    BooleanExpression veinMiner;

    private MiningConfiguration() {
        super();
    }

    @Override
    public MiningEffect computeEffect(List<ItemStack> stacks) {
        return new MiningEffect(getId(), getTooltipColor(), areaConfiguration.computeEffect(stacks), veinMiner.evaluate(stacks));
    }

    @Override
    public boolean isValid() {
        return veinMiner != null
            && areaConfiguration != null && areaConfiguration.isValid();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        areaConfiguration.encode(buf);
        veinMiner.encode(buf);
        ;
    }

    public MiningConfiguration(FriendlyByteBuf buf) {
        super(buf);
        areaConfiguration = new AreaConfiguration(buf);
        veinMiner = new BooleanExpression(buf);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof MiningConfiguration otherConfiguration) {
            return super.equals(otherConfiguration)
                && Objects.equals(areaConfiguration, otherConfiguration.areaConfiguration)
                && Objects.equals(veinMiner, otherConfiguration.veinMiner);
        }
        return false;
    }

}
