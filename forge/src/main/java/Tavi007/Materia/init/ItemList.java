package Tavi007.Materia.init;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.init.ModItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemList {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    static {
        ModItems.ITEM_MAP.forEach(ITEMS::register);
    }
}
