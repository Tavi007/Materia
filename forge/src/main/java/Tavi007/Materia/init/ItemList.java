package Tavi007.Materia.init;

import java.util.Arrays;
import java.util.function.Supplier;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.blocks.EquippingStationBlock;
import Tavi007.Materia.common.blocks.MateriaIncubatorBlock;
import Tavi007.Materia.common.init.ModBlocks;
import Tavi007.Materia.common.init.ModItems;
import Tavi007.Materia.items.AbilityPointBottleItem;
import Tavi007.Materia.items.MateriaAccessory;
import Tavi007.Materia.items.MateriaAxe;
import Tavi007.Materia.items.MateriaHoe;
import Tavi007.Materia.common.items.MateriaItem;
import Tavi007.Materia.items.MateriaPickaxe;
import Tavi007.Materia.items.MateriaShovel;
import Tavi007.Materia.items.MateriaSword;
import Tavi007.Materia.items.MateriaWand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemList {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    // only used as Icon for Item group
    private static final Properties singleStack = new Properties().stacksTo(1);
    private static final Properties fullStack = new Properties().stacksTo(64);

    static {
        ModItems.BASE_MATERIA = register(Constants.BASE_MATERIA, () -> new MateriaItem(singleStack));

        // materia
        ModItems.FIRE_MATERIA = register(Constants.FIRE_MATERIA, () -> new MateriaItem(singleStack));
        ModItems.ICE_MATERIA = register(Constants.ICE_MATERIA, () -> new MateriaItem(singleStack));
        ModItems.SIZE_UP_MATERIA = register(Constants.SIZE_UP_MATERIA, () -> new MateriaItem(singleStack));

        ModItems.WIDTH_UP_MATERIA = register(Constants.WIDTH_UP_MATERIA, () -> new MateriaItem(singleStack));
        ModItems.RANGE_UP_MATERIA = register(Constants.RANGE_UP_MATERIA, () -> new MateriaItem(singleStack));
        ModItems.HEIGHT_UP_MATERIA = register(Constants.HEIGHT_UP_MATERIA, () -> new MateriaItem(singleStack));

        ModItems.SPEED_UP_MATERIA = register(Constants.SPEED_UP_MATERIA, () -> new MateriaItem(singleStack));
        ModItems.TARGET_MATERIA = register(Constants.TARGET_MATERIA, () -> new MateriaItem(singleStack));

        // pickaxes
        ModItems.MATERIA_DIAMOND_PICKAXE = register(Constants.MATERIA_DIAMOND_PICKAXE,
                () -> new MateriaPickaxe(Tiers.DIAMOND, 1, -2.8F, singleStack, Arrays.asList(3), Arrays.asList(1)));

        // axes
        ModItems.MATERIA_DIAMOND_AXE = register(Constants.MATERIA_DIAMOND_AXE,
                () -> new MateriaAxe(Tiers.DIAMOND, 1, -2.8F, singleStack, Arrays.asList(1, 1), Arrays.asList(2)));

        // shovel
        ModItems.MATERIA_DIAMOND_SHOVEL = register(Constants.MATERIA_DIAMOND_SHOVEL,
                () -> new MateriaShovel(Tiers.DIAMOND, 1, -2.8F, singleStack, Arrays.asList(1, 2), Arrays.asList(2, 1)));

        // hoe
        ModItems.MATERIA_DIAMOND_HOE = register(Constants.MATERIA_DIAMOND_HOE,
                () -> new MateriaHoe(Tiers.DIAMOND, 1, -2.8F, singleStack, Arrays.asList(1, 3), Arrays.asList(3, 1)));

        // sword
        ModItems.MATERIA_DIAMOND_SWORD = register(Constants.MATERIA_DIAMOND_SWORD,
                () -> new MateriaSword(Tiers.DIAMOND, 1, -2.8F, singleStack, Arrays.asList(2, 2), Arrays.asList(4)));

        // wand
        ModItems.MATERIA_DIAMOND_WAND = register(Constants.MATERIA_DIAMOND_WAND,
                () -> new MateriaWand(Tiers.DIAMOND, singleStack, Arrays.asList(1, 1, 1), Arrays.asList(1, 1, 2)));

        // accessory
        ModItems.MATERIA_DIAMOND_ACCESSORY = register(Constants.MATERIA_DIAMOND_ACCESSORY,
                () -> new MateriaAccessory(Tiers.DIAMOND, singleStack, Arrays.asList(1, 2, 1), Arrays.asList(2, 1, 1)));

        // misc
        ModItems.ABILITY_POINT_BOTTLE = register(Constants.ABILITY_POINT_BOTTLE,
                () -> new AbilityPointBottleItem(fullStack));

        //blocks
        ModItems.EQUIPPING_STATION = ITEMS.register(Constants.EQUIPPING_STATION,
                () -> {
                return new BlockItem(ModBlocks.EQUIPPING_STATION.get(), fullStack);
                });

        ModItems.MATERIA_INCUBATOR = ITEMS.register(Constants.MATERIA_INCUBATOR,
                () -> new BlockItem(ModBlocks.MATERIA_INCUBATOR.get(), fullStack));
    }

    private static RegistryObject<Item> register(String name, Supplier<Item> supplier) {
        return CreativeTabList.addToTab(ITEMS.register(name, supplier));
    }
}
