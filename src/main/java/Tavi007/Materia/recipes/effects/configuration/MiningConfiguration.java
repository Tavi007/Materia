package Tavi007.Materia.recipes.effects.configuration;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class MiningConfiguration extends AbstractMateriaEffectConfiguration {

    @SerializedName("area")
    AreaConfiguration areaConfiguration;
    @SerializedName("vein_miner")
    Boolean veinMiner;

    private MiningConfiguration() {
        super();
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

    public List<ItemStack> mineBlocks(Level worldIn, BlockPos startPos, Vec3 viewVector) {
        List<BlockPos> posList = new ArrayList<BlockPos>();
        List<ItemStack> itemstackList = new ArrayList<ItemStack>();
        Block sourceBlock = worldIn.getBlockState(startPos).getBlock();

        // TODO compute max values depending on line of sight
        int maxDx = 0;
        int maxDy = 0;
        int maxDz = 0;

        for (int dx = -maxDx; dx < maxDx + 1; dx++) {
            for (int dy = -maxDy; dy < maxDy + 1; dy++) {
                for (int dz = -maxDz; dz < maxDz + 1; dz++) {
                    BlockPos pos = new BlockPos(startPos.getX() + dx, startPos.getY() + dy, startPos.getZ() + dz);
                    Block block = worldIn.getBlockState(pos).getBlock();
                    posList.add(pos);

                    if (worldIn instanceof ServerLevel) {
                        if (!worldIn.isEmptyBlock(pos) && block == sourceBlock) {
                            itemstackList.addAll(Block.getDrops(worldIn.getBlockState(pos), (ServerLevel) worldIn, pos, null));
                            worldIn.destroyBlock(pos, false);
                        }
                    }
                }
            }
        }

        return itemstackList;
    }
}
