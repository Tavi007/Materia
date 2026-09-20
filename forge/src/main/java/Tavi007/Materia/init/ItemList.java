package Tavi007.Materia.init;

import java.util.Arrays;

import Tavi007.Materia.Materia;
import Tavi007.Materia.items.AbilityPointBottleItem;
import Tavi007.Materia.items.MateriaAccessory;
import Tavi007.Materia.items.MateriaAxe;
import Tavi007.Materia.items.MateriaHoe;
import Tavi007.Materia.items.MateriaItem;
import Tavi007.Materia.items.MateriaPickaxe;
import Tavi007.Materia.items.MateriaShovel;
import Tavi007.Materia.items.MateriaSword;
import Tavi007.Materia.items.MateriaWand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemList {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Materia.MOD_ID);

    // only used as Icon for Item group
    private static Properties singleStack = new Properties().stacksTo(1);
    private static Properties fullStack = new Properties().stacksTo(64);
    public static final RegistryObject<Item> BASE_MATERIA = ITEMS.register("base_materia", () -> new MateriaItem(singleStack));

    // materia
    public static final RegistryObject<Item> FIRE_MATERIA = ITEMS.register("fire_materia", () -> new MateriaItem(singleStack));
    public static final RegistryObject<Item> ICE_MATERIA = ITEMS.register("ice_materia", () -> new MateriaItem(singleStack));
    public static final RegistryObject<Item> SIZE_UP_MATERIA = ITEMS.register("size_up_materia", () -> new MateriaItem(singleStack));

    public static final RegistryObject<Item> WIDTH_UP_MATERIA = ITEMS.register("width_up_materia", () -> new MateriaItem(singleStack));
    public static final RegistryObject<Item> RANGE_UP_MATERIA = ITEMS.register("range_up_materia", () -> new MateriaItem(singleStack));
    public static final RegistryObject<Item> HEIGHT_UP_MATERIA = ITEMS.register("height_up_materia", () -> new MateriaItem(singleStack));

    public static final RegistryObject<Item> SPEED_UP_MATERIA = ITEMS.register("speed_up_materia", () -> new MateriaItem(singleStack));
    public static final RegistryObject<Item> TARGET_MATERIA = ITEMS.register("target_materia", () -> new MateriaItem(singleStack));

    // pickaxes
    public static final RegistryObject<Item> MATERIA_DIAMOND_PICKAXE = ITEMS.register("materia_diamond_pickaxe",
        () -> new MateriaPickaxe(Tiers.DIAMOND, 1, -2.8F, singleStack, Arrays.asList(3), Arrays.asList(1)));

    // axes
    public static final RegistryObject<Item> MATERIA_DIAMOND_AXE = ITEMS.register("materia_diamond_axe",
        () -> new MateriaAxe(Tiers.DIAMOND, 1, -2.8F, singleStack, Arrays.asList(1, 1), Arrays.asList(2)));

    // shovel
    public static final RegistryObject<Item> MATERIA_DIAMOND_SHOVEL = ITEMS.register("materia_diamond_shovel",
        () -> new MateriaShovel(Tiers.DIAMOND, 1, -2.8F, singleStack, Arrays.asList(1, 2), Arrays.asList(2, 1)));

    // hoe
    public static final RegistryObject<Item> MATERIA_DIAMOND_HOE = ITEMS.register("materia_diamond_hoe",
        () -> new MateriaHoe(Tiers.DIAMOND, 1, -2.8F, singleStack, Arrays.asList(1, 3), Arrays.asList(3, 1)));

    // sword
    public static final RegistryObject<Item> MATERIA_DIAMOND_SWORD = ITEMS.register("materia_diamond_sword",
        () -> new MateriaSword(Tiers.DIAMOND, 1, -2.8F, singleStack, Arrays.asList(2, 2), Arrays.asList(4)));

    // wand
    public static final RegistryObject<Item> MATERIA_DIAMOND_WAND = ITEMS.register("materia_diamond_wand",
        () -> new MateriaWand(Tiers.DIAMOND, singleStack, Arrays.asList(1, 1, 1), Arrays.asList(1, 1, 2)));

    // accessory
    public static final RegistryObject<Item> MATERIA_DIAMOND_ACCESSORY = ITEMS.register("materia_diamond_accessory",
        () -> new MateriaAccessory(Tiers.DIAMOND, singleStack, Arrays.asList(1, 2, 1), Arrays.asList(2, 1, 1)));

    // misc
    public static final RegistryObject<Item> ABILITY_POINT_BOTTLE = ITEMS.register("ability_point_bottle",
        () -> new AbilityPointBottleItem(fullStack));
}
