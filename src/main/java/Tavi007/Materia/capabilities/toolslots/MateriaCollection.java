package Tavi007.Materia.capabilities.toolslots;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import org.jline.utils.Log;

import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.items.MateriaItem;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class MateriaCollection extends ItemStackHandler {

	//should be saved in nbt too or rather be recalculated everytime?
	private ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();
	protected int counter = 0;

	private final static int maxItemStackSlots = 8;
	
	public MateriaCollection() {
		super(maxItemStackSlots);
	}

	public ArrayList<MateriaEffect> getEffects() {
		return effectList;
	}

	public void setEffects(ArrayList<MateriaEffect> effectList) {
		this.effectList = effectList;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public MateriaEffect getCurrentEffect() {
		if(counter >= effectList.size()) {
			counter = 0;
		}
		return effectList.get(counter);
	}
	
	public void incrementCounter() {
		counter++;
		if(counter >= effectList.size()) {
			counter = 0;
		}
	}
	
	public void addAp(int ap) {
		for(ItemStack stack : stacks) {
			if(!stack.isEmpty()) {
				int[] apToNextLevel = ((MateriaItem) stack.getItem()).getApToNextLevel();
				CapabilityHelper.getLevelData(stack).addAP(ap,apToNextLevel);
			}
		}
	}

	@Override
	public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
		if (slot < 0 || slot >= maxItemStackSlots) {
			throw new IllegalArgumentException("Invalid slot number:"+slot);
		}
		if (stack.isEmpty()) return false;
		Item item = stack.getItem();
		return item instanceof MateriaItem;
	}
}
