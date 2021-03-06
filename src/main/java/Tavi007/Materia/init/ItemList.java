package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.items.AreaMateria;
import Tavi007.Materia.items.BaseMateria;
import Tavi007.Materia.items.FireMateria;
import Tavi007.Materia.items.IceMateria;
import Tavi007.Materia.items.MateriaPickaxe;
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
	public static final RegistryObject<Item> BASE_MATERIA = ITEMS.register("base_materia", () -> new BaseMateria(singleStack, new int[]{}));
	
	// materia
	public static final RegistryObject<Item> FIRE_MATERIA 	= ITEMS.register("fire_materia", () -> new FireMateria(singleStack, new int[]{10, 25}));
	public static final RegistryObject<Item> ICE_MATERIA 	= ITEMS.register("ice_materia",  () -> new IceMateria(singleStack, 	new int[]{10, 25}));
	public static final RegistryObject<Item> AREA_MATERIA 	= ITEMS.register("area_materia", () -> new AreaMateria(singleStack, new int[]{50}));
	
	// tools
	public static final RegistryObject<Item> MATERIA_DIAMOND_PICKAXE = ITEMS.register("materia_diamond_pickaxe", 
			() -> new MateriaPickaxe(ItemTier.DIAMOND, 1, -2.8F, singleStack, new int[]{1,3}, new int[]{2}));
}
