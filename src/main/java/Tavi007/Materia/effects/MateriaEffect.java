package Tavi007.Materia.effects;

import net.minecraftforge.event.world.BlockEvent;

public class MateriaEffect 
{
	protected final int materiaLevel;
	
	// when combined with other Materia
	protected final int areaLevel;
	protected final boolean bonusEffect;
	protected final boolean negateEffect;
	
	public MateriaEffect() {
		this.materiaLevel = 1;
		this.areaLevel = 0;
		this.bonusEffect = false;
		this.negateEffect = false;
	}

	public MateriaEffect(int materiaLevel, int areaLevel, boolean bonusEffect, boolean negateEffect) {
		this.materiaLevel = Math.max(materiaLevel, 1);
		this.areaLevel = Math.max(areaLevel, 0);
		this.bonusEffect = bonusEffect;
		this.negateEffect = negateEffect;
	}

	public int getAreaLevel() {return this.areaLevel;}
	public boolean hasBonusEffect() {return this.bonusEffect;}
	public boolean hasNegativEffect() {return this.negateEffect;}
	
	public void onBlockHarvest(BlockEvent.HarvestDropsEvent event) {}
	public void onBreakBlock(BlockEvent.BreakEvent event) {}
	public void onRightClick() {}
	public void onAttackEntity() {}
	public void onProjectileImpact() {}
}
