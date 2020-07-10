package Tavi007.Materia.effects;

import net.minecraftforge.event.world.BlockEvent;

public class MateriaEffectIce extends MateriaEffect{

	public MateriaEffectIce(){
		super();
	}
	
	public MateriaEffectIce(int materiaLevel, int areaLevel, boolean bonusEffect, boolean negateEffect){
		super(materiaLevel, areaLevel, bonusEffect, negateEffect);
	}
	
	@Override
	public void onBreakBlock(BlockEvent.BreakEvent event) {
		System.out.println("Ice Event");
	}
	
}