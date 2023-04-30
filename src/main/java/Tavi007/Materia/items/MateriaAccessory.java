package Tavi007.Materia.items;

import Tavi007.Materia.util.MateriaToolHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class MateriaAccessory extends TieredItem implements IMateriaTool, ICurioItem {

    // change these later
    private final int[] topCollectionSizes;
    private final int[] botCollectionSizes;

    public MateriaAccessory(Tier tier, Properties properties, int[] topCollectionSizes, int[] botCollectionSizes) {
        super(tier, properties);
        if (MateriaToolHelper.isCollectionSizesValid(topCollectionSizes)) {
            this.topCollectionSizes = topCollectionSizes;
        } else {
            this.topCollectionSizes = new int[] { 0 }; // might need to change this to 1
        }
        if (MateriaToolHelper.isCollectionSizesValid(botCollectionSizes)) {
            this.botCollectionSizes = botCollectionSizes;
        } else {
            this.botCollectionSizes = new int[] { 0 };
        }
    }

    @Override
    public int[] getTopCollectionSizes() {
        return topCollectionSizes;
    }

    @Override
    public int[] getBotCollectionSizes() {
        return botCollectionSizes;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

}
