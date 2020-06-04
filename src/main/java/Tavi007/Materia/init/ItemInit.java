package Tavi007.Materia.init;


import Tavi007.Materia.Materia;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Materia.MOD_ID)
public class ItemInit 
{
	public static final Item firemateria_item = null;
//	public static final Item icemateria_item = null;
//	public static final Item watermateria_item = null;
//	public static final Item thundermateria_item = null;
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) 
	{
		event.getRegistry().register(new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName("firemateria_item"));
//		event.getRegistry().register(new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName("icemateria_item"));
//		event.getRegistry().register(new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName("watermateria_item"));
//		event.getRegistry().register(new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName("thundermateria_item"));
	}
}
