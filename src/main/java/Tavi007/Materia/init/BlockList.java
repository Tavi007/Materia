package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.blocks.EquippingStationBlock;
import Tavi007.Materia.blocks.MateriaIncubatorBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockList {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Materia.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Materia.MOD_ID);

    private static Properties standardItemProperties = new Item.Properties().stacksTo(64);

    public static final RegistryObject<Block> EQUIPPING_STATION_BLOCK = BLOCKS.register("equipping_station",
        () -> new EquippingStationBlock(Block.Properties.of(Material.HEAVY_METAL)));
    public static final RegistryObject<Item> EQUIPPING_STATION_ITEM = ITEMS.register("equipping_station",
        () -> new BlockItem(EQUIPPING_STATION_BLOCK.get(), standardItemProperties));

    public static final RegistryObject<Block> MATERIA_INCUBATOR_BLOCK = BLOCKS.register("materia_incubator",
        () -> new MateriaIncubatorBlock(Block.Properties.of(Material.HEAVY_METAL)));
    public static final RegistryObject<Item> MATERIA_INCUBATOR_ITEM = ITEMS.register("materia_incubator",
        () -> new BlockItem(MATERIA_INCUBATOR_BLOCK.get(), standardItemProperties));

}
