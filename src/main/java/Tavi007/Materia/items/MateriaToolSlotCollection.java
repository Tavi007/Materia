package Tavi007.Materia.items;

import java.util.ArrayList;
import java.util.Arrays;

import Tavi007.Materia.effects.MateriaEffect;
import net.minecraft.item.ItemStack;

public class MateriaToolSlotCollection {

	public ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();;
	private int noSlots = 1;
	private ItemStack[] materiaStacks = new ItemStack[1]; //also contains empty stacks

	public MateriaToolSlotCollection(int noSlots) {
		if (noSlots>0 && noSlots<4) {
			this.noSlots = noSlots;
			materiaStacks = new ItemStack[noSlots];
			Arrays.fill(materiaStacks, 0, noSlots, ItemStack.EMPTY);
		}
		else {
			//incorrect constructor call.
			
		}
	}
	
	public MateriaToolSlotCollection(ItemStack[] materiaStacks) {
		noSlots = materiaStacks.length;
		this.materiaStacks = materiaStacks;
		computeEffectList();
	}
	
	public int getNoSlots() {
		return noSlots;
	}
	
	public void addAP(int amount) {
		//add AP to the ItemStacks
		for(int i=0; i<materiaStacks.length; i++) {
			if(!materiaStacks[i].isEmpty()) {
				((BaseMateria) materiaStacks[i].getItem()).addAP(amount);;
			}
		}
		//recompute EffectList
		//maybe write an updateEffectList instead, to skip some redundant effect search
		//only the levels need to be updated.
		computeEffectList();
	}
	
	public ArrayList<BaseMateria> getMateriaList() {
		ArrayList<BaseMateria> materiaList = new ArrayList<BaseMateria>();
		for(int i=0; i<materiaStacks.length; i++) {
			materiaList.add((BaseMateria) materiaStacks[i].getItem());
		}
		return materiaList;
	}
	
	public ArrayList<ItemStack> getMateriaStackList() {
		ArrayList<ItemStack> materiaStackList = new ArrayList<ItemStack>();
		for(int i=0; i<materiaStacks.length; i++) {
			materiaStackList.add(materiaStacks[i]);
		}
		return materiaStackList;
	}
	
	
	private void computeEffectList() {
		
	}
	
}
