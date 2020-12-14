package Tavi007.Materia.items;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class AreaMateria extends BaseMateria {

	public AreaMateria(Properties properties) {
		super(properties);
		maxLevel = 3;
		levelUpAP = new int[maxLevel];
	}
	
	public AreaMateria(Properties properties, int level, int ap) {
		super(properties);
		maxLevel = 3;
		levelUpAP = new int[maxLevel];
		this.level = level;
		this.ap = ap;
	}
	
	@Override
	public ITextComponent getName() {
		return new StringTextComponent("Area Materia");
	}
}
