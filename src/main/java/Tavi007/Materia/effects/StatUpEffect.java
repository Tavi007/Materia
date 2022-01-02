package Tavi007.Materia.effects;

import Tavi007.Materia.Materia;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class StatUpEffect extends MateriaEffect {

	private Stat stat;

	public StatUpEffect(Stat stat) {
		super(new ResourceLocation(Materia.MOD_ID, stat.toString().toLowerCase()));
		this.stat = stat;
	}
	
	public boolean isMiningSpeed() {
		return stat.equals(Stat.MINING_SPEED);
	}
	
	public int getValue(ItemStack stack) {
		return 0;
	}
	
	@Override
	public String getDefaultTooltip() {
		return stat.getTooltip();
	}

}
