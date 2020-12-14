package Tavi007.Materia.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class BaseMateria extends Item {

	public int level = 0;
	public int maxLevel = 0;
	public int ap = 0;
	public int[] levelUpAP = new int[maxLevel];
	
	public BaseMateria(Properties properties) {
		super(properties);
	}


	public BaseMateria(Properties properties, int level, int maxLevel, int ap, int[] levelUpAP) {
		super(properties);
		this.level = level;
		this.maxLevel = maxLevel;
		this.ap = ap;
		this.levelUpAP = levelUpAP;
	}
	
	public void addAP(int amount) {
		if (level >= maxLevel) {
			return;
		}
		ap += amount;
		int nextLevelAP = levelUpAP[level];
		if (ap > nextLevelAP) {
			//level up
			ap -= nextLevelAP;
			level += 1; 
		}
	}
	
	@Override
	public ITextComponent getName() {
		return new StringTextComponent("Base Materia");
	}
	
	//add enchanted glint, if maxLevel is reached
	@Override
	public boolean hasEffect(ItemStack stack) {
      return level >= maxLevel;
   }
}
