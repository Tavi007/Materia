package Tavi007.Materia.client.registry;

import Tavi007.Materia.client.color.MagicColor;
import Tavi007.Materia.common.registry.ModItems;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModColors {

    public static final Map<ItemColor, List<ItemLike>> ITEM_COLORS = new HashMap<>();

    static {
        ITEM_COLORS.put(new MagicColor(), List.of(
                ModItems.FIRE_MAGICITE.get(),
                ModItems.FIRE_PHIAL.get(),
                ModItems.ICE_MAGICITE.get(),
                ModItems.ICE_PHIAL.get(),
                ModItems.LIGHTNING_MAGICITE.get(),
                ModItems.LIGHTNING_PHIAL.get(),
                ModItems.AQUA_MAGICITE.get(),
                ModItems.AQUA_PHIAL.get(),
                ModItems.BIO_MAGICITE.get(),
                ModItems.BIO_PHIAL.get()
                )
        );
    }


}
