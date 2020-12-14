package Tavi007.Materia.items;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class IceMateria extends BaseMateria {

	public IceMateria(Properties properties) {
		super(properties);
		maxLevel = 5;
		levelUpAP = new int[maxLevel];
	}

	public IceMateria(Properties properties, int level, int ap) {
		super(properties);
		maxLevel = 5;
		levelUpAP = new int[maxLevel];
		this.level = level;
		this.ap = ap;
	}

	@Override
	public ITextComponent getName() {
		return new StringTextComponent("Ice Materia");
	}
}
