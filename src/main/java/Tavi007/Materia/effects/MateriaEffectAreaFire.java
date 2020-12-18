package Tavi007.Materia.effects;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.items.AreaMateria;
import Tavi007.Materia.items.FireMateria;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class MateriaEffectAreaFire extends MateriaEffect implements IMateriaEffectRecipe, IMateriaEffectArea {
	
	public MateriaEffectAreaFire(FireMateria fire, AreaMateria area){
		super("Area Fire");
		this.levelList.add(fire.level);
		this.levelList.add(area.level);
	}
	
	public MateriaEffectAreaFire(AreaMateria area, FireMateria fire){
		this(fire, area);
	}
	
	public MateriaEffectAreaFire(){
		super("Area Fire");
	}

	@Override
	public ArrayList<ItemStack> applyRecipe(ArrayList<ItemStack> itemIn) {
		return itemIn;
	}

	@Override
	public int getAreaLevel() {
		return levelList.get(1);
	}

	@Override
	public void addPickaxeToolTip(List<ITextComponent> tooltip) {
		tooltip.add(new StringTextComponent("Fire Area Pick Effect"));
	};
}
