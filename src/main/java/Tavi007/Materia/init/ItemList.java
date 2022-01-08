package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.items.MateriaAccessory;
import Tavi007.Materia.items.MateriaAxe;
import Tavi007.Materia.items.MateriaHoe;
import Tavi007.Materia.items.MateriaItem;
import Tavi007.Materia.items.MateriaPickaxe;
import Tavi007.Materia.items.MateriaShovel;
import Tavi007.Materia.items.MateriaSword;
import Tavi007.Materia.items.MateriaWand;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemList {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Materia.MOD_ID);

    // only used as Icon for Item group
    private static Properties singleStack = new Item.Properties().group(Materia.MATERIA_GROUP).maxStackSize(1);
    public static final RegistryObject<Item> BASE_MATERIA = ITEMS.register("base_materia", () -> new MateriaItem(singleStack, new int[] {}));

    // materia
    public static final RegistryObject<Item> FIRE_MATERIA = ITEMS.register("fire_materia", () -> new MateriaItem(singleStack, new int[] { 10, 25 }));
    public static final RegistryObject<Item> ICE_MATERIA = ITEMS.register("ice_materia", () -> new MateriaItem(singleStack, new int[] { 10, 25 }));
    public static final RegistryObject<Item> AREA_MATERIA = ITEMS.register("area_materia", () -> new MateriaItem(singleStack, new int[] { 50 }));

    public static final RegistryObject<Item> SPEED_UP_MATERIA = ITEMS.register("speed_up_materia", () -> new MateriaItem(singleStack, new int[] { 100 }));

    // pickaxes
    public static final RegistryObject<Item> MATERIA_DIAMOND_PICKAXE = ITEMS.register("materia_diamond_pickaxe",
        () -> new MateriaPickaxe(ItemTier.DIAMOND, 1, -2.8F, singleStack, new int[] { 1 }, new int[] { 3 }));

    // axes
    public static final RegistryObject<Item> MATERIA_DIAMOND_AXE = ITEMS.register("materia_diamond_axe",
        () -> new MateriaAxe(ItemTier.DIAMOND, 1, -2.8F, singleStack, new int[] { 1, 1 }, new int[] { 2 }));

    // shovel
    public static final RegistryObject<Item> MATERIA_DIAMOND_SHOVEL = ITEMS.register("materia_diamond_shovel",
        () -> new MateriaShovel(ItemTier.DIAMOND, 1, -2.8F, singleStack, new int[] { 1, 2 }, new int[] { 2, 1 }));

    // hoe
    public static final RegistryObject<Item> MATERIA_DIAMOND_HOE = ITEMS.register("materia_diamond_hoe",
        () -> new MateriaHoe(ItemTier.DIAMOND, 1, -2.8F, singleStack, new int[] { 1, 3 }, new int[] { 3, 1 }));

    // sword
    public static final RegistryObject<Item> MATERIA_DIAMOND_SWORD = ITEMS.register("materia_diamond_sword",
        () -> new MateriaSword(ItemTier.DIAMOND, 1, -2.8F, singleStack, new int[] { 2, 2 }, new int[] { 4 }));

    // wand
    public static final RegistryObject<Item> MATERIA_DIAMOND_WAND = ITEMS.register("materia_diamond_wand",
        () -> new MateriaWand(ItemTier.DIAMOND, 1, -2.8F, singleStack, new int[] { 1, 1, 1 }, new int[] { 1, 1, 2 }));

    // accessory
    public static final RegistryObject<Item> MATERIA_DIAMOND_ACCESSORY = ITEMS.register("materia_diamond_accessory",
        () -> new MateriaAccessory(ItemTier.DIAMOND, 1, -2.8F, singleStack, new int[] { 1, 2, 1 }, new int[] { 2, 1, 1 }));

}
