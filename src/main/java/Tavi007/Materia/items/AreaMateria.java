package Tavi007.Materia.items;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class AreaMateria extends BaseMateria {

	public AreaMateria(Properties properties) {
		super(properties);
	}

	@Override
	public ITextComponent getName() {
		return new StringTextComponent("Area Materia");
	}
}
