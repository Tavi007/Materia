package Tavi007.Materia.common.init;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.items.*;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ModItems {

    public static final HashMap<String, Supplier<Item>> ITEM_MAP = new HashMap<>();
    public static final ArrayList<Supplier<Item>> MATERIAS = new ArrayList<>();
    public static final ArrayList<Supplier<Item>> MATERIA_TOOLS = new ArrayList<>();
    public static final ArrayList<Supplier<Item>> MISC = new ArrayList<>();

    // only used as Icon for Item group
    private static final Item.Properties singleStack = new Item.Properties().stacksTo(1);
    private static final Item.Properties fullStack = new Item.Properties().stacksTo(64);

    public static final Supplier<Item> BASE_MATERIA = register(Constants.BASE_MATERIA, () -> new MateriaItem(singleStack));

    // materia
    public static final Supplier<Item> FIRE_MATERIA = registerMateria(Constants.FIRE_MATERIA, () -> new MateriaItem(singleStack));
    public static final Supplier<Item> ICE_MATERIA = registerMateria(Constants.ICE_MATERIA, () -> new MateriaItem(singleStack));
    public static final Supplier<Item> SIZE_UP_MATERIA = registerMateria(Constants.SIZE_UP_MATERIA, () -> new MateriaItem(singleStack));
    public static final Supplier<Item> WIDTH_UP_MATERIA = registerMateria(Constants.WIDTH_UP_MATERIA, () -> new MateriaItem(singleStack));
    public static final Supplier<Item> RANGE_UP_MATERIA = registerMateria(Constants.RANGE_UP_MATERIA, () -> new MateriaItem(singleStack));
    public static final Supplier<Item> HEIGHT_UP_MATERIA = registerMateria(Constants.HEIGHT_UP_MATERIA, () -> new MateriaItem(singleStack));
    public static final Supplier<Item> SPEED_UP_MATERIA = registerMateria(Constants.SPEED_UP_MATERIA, () -> new MateriaItem(singleStack));
    public static final Supplier<Item> TARGET_MATERIA = registerMateria(Constants.TARGET_MATERIA, () -> new MateriaItem(singleStack));

    // pickaxes
    public static final Supplier<Item> MATERIA_DIAMOND_PICKAXE = registerTool(Constants.MATERIA_DIAMOND_PICKAXE,
            () -> new MateriaPickaxe(Tiers.DIAMOND, 1,-2.8F,singleStack, Arrays.asList(3),Arrays. asList(1)));

    // axes
    public static final Supplier<Item> MATERIA_DIAMOND_AXE = registerTool(Constants.MATERIA_DIAMOND_AXE,
            () -> new MateriaAxe(Tiers.DIAMOND, 1,-2.8F,singleStack, Arrays.asList(1, 1),Arrays. asList(2)));

    // shovel
    public static final Supplier<Item> MATERIA_DIAMOND_SHOVEL = registerTool(Constants.MATERIA_DIAMOND_SHOVEL,
            () -> new MateriaShovel(Tiers.DIAMOND, 1,-2.8F,singleStack, Arrays.asList(1, 2),Arrays. asList(2,1)));

    // hoe
    public static final Supplier<Item> MATERIA_DIAMOND_HOE = registerTool(Constants.MATERIA_DIAMOND_HOE,
            () -> new MateriaHoe(Tiers.DIAMOND, 1,-2.8F,singleStack, Arrays.asList(1, 3),Arrays. asList(3,1)));

    // sword
    public static final Supplier<Item> MATERIA_DIAMOND_SWORD = registerTool(Constants.MATERIA_DIAMOND_SWORD,
            () -> new MateriaSword(Tiers.DIAMOND, 1,-2.8F,singleStack, Arrays.asList(2, 2),Arrays. asList(4)));

    // wand
    public static final Supplier<Item> MATERIA_DIAMOND_WAND = registerTool(Constants.MATERIA_DIAMOND_WAND,
            () -> new MateriaWand(Tiers.DIAMOND, singleStack, Arrays.asList(1, 1,1),Arrays.asList(1,1,2)));

    // accessory
    public static final Supplier<Item> MATERIA_DIAMOND_ACCESSORY = registerTool(Constants.MATERIA_DIAMOND_ACCESSORY,
            () -> new MateriaAccessory(Tiers.DIAMOND, singleStack, Arrays.asList(1, 2,1),Arrays.asList(2,1,1)));

    // misc
    public static final Supplier<Item> ABILITY_POINT_BOTTLE = registerMisc(Constants.ABILITY_POINT_BOTTLE,
            () -> new  AbilityPointBottleItem(fullStack));

    //blocks
    public static final Supplier<Item> EQUIPPING_STATION = registerMisc(Constants.EQUIPPING_STATION,
            ()-> new BlockItem(ModBlocks.EQUIPPING_STATION.get(), fullStack));

    public static final Supplier<Item> MATERIA_INCUBATOR = registerMisc(Constants.MATERIA_INCUBATOR,
            ()-> new BlockItem(ModBlocks.MATERIA_INCUBATOR.get(),fullStack));



    private static Supplier<Item> registerMateria(String name, Supplier<Item> supplier) {
        Supplier<Item> memoized = register(name, supplier);
        MATERIAS.add(memoized);
        return memoized;
    }
    private static Supplier<Item> registerTool(String name, Supplier<Item> supplier) {
        Supplier<Item> memoized = register(name, supplier);
        MATERIA_TOOLS.add(memoized);
        return memoized;
    }

    private static Supplier<Item> registerMisc(String name, Supplier<Item> supplier) {
        Supplier<Item> memoized = register(name, supplier);
        MISC.add(memoized);
        return memoized;
    }
    private static Supplier<Item> register(String name, Supplier<Item> memoized) {
        ITEM_MAP.put(name, memoized);
        return memoized;
    }
}
