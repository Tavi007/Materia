package Tavi007.Materia.effects;

import Tavi007.Materia.objects.items.MateriaItemIce;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class MateriaEffectIce implements IMateriaEffect{

	public MateriaItemIce iceMateria;
	
	public MateriaEffectIce(MateriaItemIce iceMateria){
		this.iceMateria = iceMateria;
	}
	@Override
	public void onBlockHarvest(HarvestDropsEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBreakBlock(BreakEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRightClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAttackEntity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProjectileImpact() {
		// TODO Auto-generated method stub
		
	}

}