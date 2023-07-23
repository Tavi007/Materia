package Tavi007.Materia.recipes.effects.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.Materia;
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

    public Map<BlockPos, List<ItemStack>> mineBlocks(Level worldIn, BlockPos startPos, Vec3 viewVector, List<ItemStack> stacks) {
        Map<BlockPos, List<ItemStack>> blockPosToStacksMap = new HashMap<>();
        if (worldIn instanceof ServerLevel serverLevel) {

            // define local coordinate system depending on the view vector
            Vec3 vRange = viewVector.normalize();
            Vec3 vHeight;
            if (vRange.x() != 0 || vRange.y() != 0) {
                vHeight = new Vec3(-vRange.x() * vRange.y(), vRange.x() * vRange.x() + vRange.z() * vRange.z(), -vRange.z() * vRange.y()).normalize();
            } else {
                vHeight = new Vec3(0, 0, 1).normalize();
            }
            Vec3 vWidth = vRange.cross(vHeight).normalize();

            Materia.LOGGER.debug("CoordinateSystem when mining: ");
            Materia.LOGGER.debug("vRange: {}", vRange);
            Materia.LOGGER.debug("vHeight: {}", vHeight);
            Materia.LOGGER.debug("vWidth: {}", vWidth);

            // center of new coordinate system + areaConfiguration defines a cuboid to be mined
            int rangeLevel = areaConfiguration.getRangeLevel(stacks);
            int heightLevel = areaConfiguration.getHeightLevel(stacks);
            int widthLevel = areaConfiguration.getWidthLevel(stacks);
            for (int dvRange = 0; dvRange <= rangeLevel * 2; dvRange++) {
                for (int dvHeight = -heightLevel; dvHeight <= heightLevel; dvHeight++) {
                    for (int dvWidth = -widthLevel; dvWidth <= widthLevel; dvWidth++) {

                        long dx = Math.round(vRange.x() * dvRange + vHeight.x() * dvHeight + vWidth.x() * dvWidth);
                        long dy = Math.round(vRange.y() * dvRange + vHeight.y() * dvHeight + vWidth.y() * dvWidth);
                        long dz = Math.round(vRange.z() * dvRange + vHeight.z() * dvHeight + vWidth.z() * dvWidth);

                        BlockPos pos = new BlockPos(startPos.getX() + dx, startPos.getY() + dy, startPos.getZ() + dz);
                        if (!serverLevel.isEmptyBlock(pos) && !blockPosToStacksMap.containsKey(pos)) {
                            blockPosToStacksMap.put(pos, Block.getDrops(worldIn.getBlockState(pos), serverLevel, pos, null));
                            serverLevel.destroyBlock(pos, false);
                        }
                    }
                }
            }
        }
        return blockPosToStacksMap;
    }
}
