package Tavi007.Materia.effects;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.items.BaseMateria;
import net.minecraft.util.text.ITextComponent;

public class MateriaEffect {
	private String name = "Base";
	protected ArrayList<BaseMateria> materiaList = new ArrayList<BaseMateria>();
	
	public MateriaEffect(String name) {
		this.name = name;	
	}

	public MateriaEffect() {
	}

	public String getName() {
		return name;
	};

	public void addAP(int amount) {
		materiaList.forEach(materia ->{
			materia.addAP(amount);
		});
	}

	public void addPickaxeToolTip(List<ITextComponent> tooltip) {
		return;
	};
}
