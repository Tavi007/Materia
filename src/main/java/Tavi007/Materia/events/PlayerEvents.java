package Tavi007.Materia.events;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.effects.EffectCapability;
import Tavi007.Materia.capabilities.effects.EffectList;
import Tavi007.Materia.capabilities.effects.IMateriaEffectArea;
import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {

	@SubscribeEvent
	public static void breakBlockMateriaEvent(BlockEvent.BreakEvent event){

		if(!event.getWorld().isRemote()){
			ItemStack stack = event.getPlayer().getHeldItemMainhand();
			Item activeItem = stack.getItem();
			//check if activeItem is a MateriaTool
			if(!(activeItem instanceof IMateriaTool)) {

				//get effectList
				EffectList effectList = (EffectList) stack.getCapability(EffectCapability.EFFECT_CAPABILITY, null).orElse(new EffectList());
				int maxAreaLevel = effectList.getMaxAreaLevel();
				
				//the plan:
				//generate drops (but keep in mind, which level they correspond to)
				//apply recipe effects
				//destroy mined blocks

				//
				ServerWorld world = (ServerWorld) event.getWorld();
				BlockPos sourcePos = event.getPos();
				
				//decides which additional block should be mined
				Vector3d lookVec = event.getPlayer().getLookVec();
				List<BlockPos> posList = new ArrayList<BlockPos>();
				
				Block sourceBlock = world.getBlockState(sourcePos).getBlock();
				for (int dx=-maxAreaLevel; dx<maxAreaLevel+1; dx++) {
					for (int dy=-maxAreaLevel; dy<maxAreaLevel+1; dy++) {
						for (int dz=-maxAreaLevel; dz<maxAreaLevel+1; dz++) {
							BlockPos pos = new BlockPos(sourcePos.getX()+dx, sourcePos.getY()+dy, sourcePos.getZ()+dz);
							Block block = world.getBlockState(pos).getBlock();
							posList.add(pos);
							
							if(!world.isAirBlock(pos) && block == sourceBlock) {
								List<ItemStack> itemstackList = Block.getDrops(world.getBlockState(pos), world, pos, null);
								world.destroyBlock(pos, false);
							}
						}	
					}
				}
				
				
			}
		}
	}
}
