package Tavi007.Materia.objects.items;

import java.util.ArrayList;

import net.minecraft.item.Item;

public class MateriaItem extends Item implements IMateriaItem {

	private int level;
	private int ap;
	private final ArrayList<Integer> levelUpList;
	
	public MateriaItem(Properties properties) {
		super(properties);
		
		//to do: get list from loaded json file.
		this.levelUpList = new ArrayList<Integer>();
	}

	@Override
	public void setLevel(int level) {this.level = level;}

	@Override
	public int getLevel() {return this.level;}

	@Override
	public void setAP(int ap) {this.ap = ap;}

	@Override
	public int getAP() {return this.ap;}

	@Override
	public void addAP(int amount) {this.ap += amount;}

	@Override
	public ArrayList<Integer> getLevelUpAP() {return this.levelUpList;}

}
