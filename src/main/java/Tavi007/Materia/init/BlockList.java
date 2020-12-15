package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.blocks.EquippingStationBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockList {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Materia.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Materia.MOD_ID);

	private static Properties standardItemProperties = new Item.Properties().group(Materia.MATERIA_GROUP).maxStackSize(64);
	
	public static final RegistryObject<Block> EQUIPPING_STATION_BLOCK = BLOCKS.register("equipping_station", () -> new EquippingStationBlock(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Item>  EQUIPPING_STATION_ITEM  = ITEMS.register("equipping_station", () -> new BlockItem(EQUIPPING_STATION_BLOCK.get(), standardItemProperties));
}
