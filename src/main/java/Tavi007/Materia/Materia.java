package Tavi007.Materia;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.ContainerTypeList;
import Tavi007.Materia.init.ItemList;
import Tavi007.Materia.init.ScreenList;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("materia")
public class Materia 
{
	public static Materia instance;
	public static final String MOD_ID = "materia";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	public static final ItemGroup MATERIA_GROUP = new Materia.MateriaItemGroup("materia_group");
	
	
	@SuppressWarnings("deprecation")
	public Materia() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		//register common
		ItemList.ITEMS.register(modEventBus);
		BlockList.BLOCKS.register(modEventBus);
		BlockList.ITEMS.register(modEventBus);
		ContainerTypeList.CONTAINER_TYPES.register(modEventBus);
		//register client only
		DistExecutor.runWhenOn(Dist.CLIENT, () -> Materia::registerClientOnlyEvents);
		
		instance = this;
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private static void registerClientOnlyEvents() {
		FMLJavaModLoadingContext.get().getModEventBus().register(ScreenList.class);
		LOGGER.info("clientRegistries method registered.");	
	}
	
	/*
	 * This is an inner class, you don't need to do this but I have in this case
	 * We are creating an ItemGroup (previously known as creative tabs)
	 */	
	public static class MateriaItemGroup extends ItemGroup {

		public MateriaItemGroup(String name) {
			super(name);
		}

		@Override
		public ItemStack createIcon() {
			return new ItemStack(ItemList.BASE_MATERIA.get());
		}
		
	}
	
}
