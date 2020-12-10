package Tavi007.Materia.capabilities.effects;

import java.util.ArrayList;

import Tavi007.Materia.Materia;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class EffectList {
	public final ArrayList<MateriaEffect> list;
	
	public EffectList() {
		this.list = new ArrayList<MateriaEffect>();
	}
	
	public int getMaxAreaLevel() {
		int[] maxAreaLevel = {1};
		this.list.forEach(effect -> {
			if(effect instanceof IMateriaEffectArea) {
				maxAreaLevel[0] = Math.max(maxAreaLevel[0], ((IMateriaEffectArea) effect).getAreaLevel());
			}
		});
		return maxAreaLevel[0];
	}
	
	
	public void add(MateriaEffect effect) {
		this.list.add(effect);
	}
	
	public boolean remove(int index) {
		try {
			this.list.remove(index);
		}
		catch(Exception e) {
			Materia.LOGGER.catching(e);
			return false;
		}
		return true;
	}
	
	//saving
	public CompoundNBT writeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		
		ListNBT list = new ListNBT();
		this.list.forEach(effect -> {
			CompoundNBT effectNBT = new CompoundNBT();
			effectNBT.putString("effect_name", effect.getName());
			effectNBT.putIntArray("effect_max_level", effect.getMaxLevel());
			effectNBT.putIntArray("effect_ap", effect.getAP());
			effectNBT.putIntArray("effect_level", effect.getLevel());
			effectNBT.putIntArray("effect_next_level_ap", effect.getNextLevelAP());
		});
		nbt.put("effect_list", list);
		return nbt;
	}
	
	public void readNBT(CompoundNBT nbt) {
		ListNBT list = (ListNBT) nbt.get("effect_list");
		ArrayList<MateriaEffect> effectArray = new ArrayList<MateriaEffect>();
		list.forEach(effectNBT -> {
			CompoundNBT effectNBT_ = (CompoundNBT) effectNBT;
			String name = effectNBT_.getString("effect_name");
			int[] maxLevel = effectNBT_.getIntArray("effect_max_level");
			int[] abilityPower = effectNBT_.getIntArray("effect_ap");
			int[] level = effectNBT_.getIntArray("effect_level");
			int[] nextLevelAP = effectNBT_.getIntArray("effect_next_level_ap");
			effectArray.add(getMateriaEffect(name, maxLevel, abilityPower, level, nextLevelAP));
		});
	}
	
	private MateriaEffect getMateriaEffect(String name, int[] maxLevel, int[] abilityPower, int[] level, int[] nextLevelAP) {
		switch (name) {
			case "Fire":
				return new MateriaEffectFire(maxLevel, abilityPower, level, nextLevelAP);
			case "Fire Area":
				return new MateriaEffectAreaFire(maxLevel, abilityPower, level, nextLevelAP);
			case "Ice":
				return new MateriaEffectIce(maxLevel, abilityPower, level, nextLevelAP);
			default:
				return new MateriaEffect();
		
		}
	}
}
