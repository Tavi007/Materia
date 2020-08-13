package Tavi007.Materia.effects;

import net.minecraftforge.event.world.BlockEvent;

public interface IMateriaEffect {

	public void onBlockHarvest(BlockEvent.HarvestDropsEvent event);
	public void onBreakBlock(BlockEvent.BreakEvent event);
	public void onRightClick();
	public void onAttackEntity();
	public void onProjectileImpact();
}
