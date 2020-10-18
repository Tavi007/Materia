package Tavi007.Materia.events;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.Materia;
import Tavi007.Materia.effects.IMateriaEffect;
import Tavi007.Materia.effects.IMateriaEffectArea;
import Tavi007.Materia.effects.MateriaEffectFire;
import Tavi007.Materia.effects.MateriaEffectIce;
import Tavi007.Materia.objects.items.IMateriaTool;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Bus.FORGE)
public class BreakBlockMateriaEvent {

	@SubscribeEvent
	public static void breakBlockMateriaEvent(BlockEvent.BreakEvent event){

		if(!event.getWorld().isRemote()){
			Item activeItem = event.getPlayer().getHeldItemMainhand().getItem();
			//check if activeItem is a MateriaTool
			if(!(activeItem instanceof IMateriaTool)) {

				//get effectList
				//ArrayList<IMateriaEffect> effectList = ((IMateriaTool) activeItem).getMateriaEffectList();
				ArrayList<IMateriaEffect> effectList = new ArrayList<IMateriaEffect>();
				//testing 
				effectList.add(new MateriaEffectFire(null));
				effectList.add(new MateriaEffectIce(null));

				//the plan:
				//get area level per effect
				//generate drops (but keep in mind, which level the correspond to)
				//apply recipe effects
				//destroy mined blocks
				
				int[] maxAreaLevel = {1};
				effectList.forEach(effect -> {
					if(effect instanceof IMateriaEffectArea) {
						maxAreaLevel[0] = Math.max(maxAreaLevel[0], ((IMateriaEffectArea) effect).getAreaLevel());
					}
				});

				//
				ServerWorld world = (ServerWorld) event.getWorld();
				BlockPos sourcePos = event.getPos();
				Block sourceBlock = world.getBlockState(sourcePos).getBlock();
				for (int dx=-maxAreaLevel[0]; dx<maxAreaLevel[0]+1; dx++) {
					for (int dy=-maxAreaLevel[0]; dy<maxAreaLevel[0]+1; dy++) {
						for (int dz=-maxAreaLevel[0]; dz<maxAreaLevel[0]+1; dz++) {
							BlockPos pos = new BlockPos(sourcePos.getX()+dx, sourcePos.getY()+dy, sourcePos.getZ()+dz);
							Block block = world.getBlockState(pos).getBlock();
							System.out.println("Block: " + block.toString() + " at Pos: " + pos.toString());
							if(!world.isAirBlock(pos) && block == sourceBlock) {
								List<ItemStack> itemstackList = Block.getDrops(world.getBlockState(pos), world, pos, null);
								
								world.destroyBlock(pos, true);
							}
						}	
					}
				}
				
				
			}
		}
	}
}
