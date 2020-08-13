package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.objects.items.MateriaItem;
import Tavi007.Materia.objects.items.MateriaItemArea;
import Tavi007.Materia.objects.items.MateriaItemFire;
import Tavi007.Materia.objects.items.MateriaItemIce;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemList
{

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Materia.MOD_ID);

	// only used as Icon for Item group
	public static final RegistryObject<Item> BASE_MATERIA = ITEMS.register("base_materia", () -> new MateriaItem(new Item.Properties().group(Materia.MATERIA_GROUP)));
	
	// materia
	public static final RegistryObject<Item> FIRE_MATERIA = ITEMS.register("fire_materia", () -> new MateriaItemFire(new Item.Properties().group(Materia.MATERIA_GROUP)));
	public static final RegistryObject<Item> ICE_MATERIA = ITEMS.register("ice_materia", () -> new MateriaItemIce(new Item.Properties().group(Materia.MATERIA_GROUP)));
	public static final RegistryObject<Item> AREA_MATERIA = ITEMS.register("area_materia", () -> new MateriaItemArea(new Item.Properties().group(Materia.MATERIA_GROUP)));
	
	// tools
	
	
}
