package Tavi007.Materia.capabilities.effects;

import java.util.ArrayList;

import Tavi007.Materia.Materia;
import net.minecraft.nbt.CompoundNBT;

public class EffectList {
	public final ArrayList<IMateriaEffect> list;
	
	public EffectList() {
		this.list = new ArrayList<IMateriaEffect>();
	}
	
	public void addEffect(IMateriaEffect effect) {
		this.list.add(effect);
	}
	
	public boolean removeEffect(int index) {
		try {
			this.list.remove(index);
		}
		catch(Exception e) {
			Materia.LOGGER.catching(e);
			return false;
		}
		return true;
	}
	
	//networking
	public CompoundNBT writeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		int size = list.size();
		nbt.putInt("list_size", size);
		this.list.forEach(effect ->{
		});
		
		return nbt;
	}
	
	public void readNBT(CompoundNBT nbt) {
		int size = nbt.getInt("list_size");
		for(int i=0; i<size; i++) {
			
		}
	}
}
