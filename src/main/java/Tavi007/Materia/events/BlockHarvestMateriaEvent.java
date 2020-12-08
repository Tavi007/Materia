package Tavi007.Materia.events;

import java.util.ArrayList;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.effects.IMateriaEffect;
import Tavi007.Materia.capabilities.effects.MateriaEffectFire;
import Tavi007.Materia.capabilities.effects.MateriaEffectIce;
import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.item.Item;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Bus.FORGE)
public class BlockHarvestMateriaEvent {

//	@SubscribeEvent
//	public static void blockHarvestMateriaEvent(BlockEvent.HarvestDropsEvent event){
//
//		Item activeItem = event.getHarvester().getHeldItemMainhand().getItem();
//		//check if activeItem is a MateriaTool
//		if(activeItem instanceof IMateriaTool) {
//			
//			//get effectList
//			ArrayList<IMateriaEffect> effectList = ((IMateriaTool) activeItem).getMateriaEffectList();
//			
//			//testing 
//			effectList.add(new MateriaEffectFire(null));
//			effectList.add(new MateriaEffectIce(null));
//
//			//apply effects
//			effectList.forEach(effect -> {
//			});
//		}
//
//	}
}
