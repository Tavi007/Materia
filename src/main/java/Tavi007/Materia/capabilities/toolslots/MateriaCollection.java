package Tavi007.Materia.capabilities.toolslots;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.items.BaseMateria;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class MateriaCollection extends ItemStackHandler {

	//should be saved in nbt too or rather be recalculated everytime?
	public ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();

	private final static int maxItemStackSlots = 8;
	
	public MateriaCollection() {
		super(maxItemStackSlots);
	}

	public ArrayList<MateriaEffect> getEffects() {
		return effectList;
	}
	

	@Override
    protected void onContentsChanged(int slot) {
		//recompute effects here?
		String test = "hi!"; 
    }

	@Override
	public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
		if (slot < 0 || slot >= maxItemStackSlots) {
			throw new IllegalArgumentException("Invalid slot number:"+slot);
		}
		if (stack.isEmpty()) return false;
		Item item = stack.getItem();
		if (item instanceof BaseMateria) return true;
		return false;
	}
}
