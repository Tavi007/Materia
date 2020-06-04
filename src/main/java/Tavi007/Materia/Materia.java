package Tavi007.Materia;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
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
	private static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	public Materia()
	{
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		
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
	
}
