package Tavi007.Materia;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.ItemList;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("materia")
public class Materia 
{
	public static Materia instance;
	public static final String MOD_ID = "materia";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	public static final ItemGroup MATERIA_GROUP = new Materia.MateriaItemGroup("materia_group");
	
	
	public Materia()
	{
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		
		ItemList.ITEMS.register(modEventBus);
		BlockList.BLOCKS.register(modEventBus);
		BlockList.ITEMS.register(modEventBus);
		
		instance = this;
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event)
	{
		LOGGER.info("setup method registered.");
	}
	
	private void doClientStuff(final FMLClientSetupEvent event)
	{
		LOGGER.info("clientRegistries method registered.");	
	}
	
	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event)
	{
		
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
