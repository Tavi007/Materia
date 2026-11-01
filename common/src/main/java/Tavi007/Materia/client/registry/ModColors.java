package Tavi007.Materia.client.registry;

import Tavi007.Materia.client.color.MagiciteColor;
import Tavi007.Materia.common.init.ModItems;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModColors {

    public static final Map<ItemColor, List<ItemLike>> ITEM_COLORS = new HashMap<>();

    static {
        ITEM_COLORS.put(new MagiciteColor(), List.of(
                ModItems.FIRE_MAGICITE.get(),
                ModItems.ICE_MAGICITE.get())
        );
    }


}
