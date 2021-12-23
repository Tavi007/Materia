package Tavi007.Materia.capabilities.toolslots;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.items.MateriaItem;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class MateriaCollection extends ItemStackHandler {

	//should be saved in nbt too or rather be recalculated everytime?
	private ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();
	private Integer effectIndex = 0;
	private Boolean dirty = false;

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

	public int getEffectIndex() {
		return effectIndex;
	}

	public void setEffectIndex(int index) {
		this.effectIndex = index;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void markCleaned() {
		dirty = false;
	}

	public void markDirty() {
		dirty = true;
	}
	
	public MateriaEffect getCurrentEffect() {
		if(effectIndex >= effectList.size()) {
			effectIndex = 0;
		}
		return effectList.get(effectIndex);
	}

	public void incrementEffectIndex() {
		effectIndex++;
		if(effectIndex >= effectList.size()) {
			effectIndex = 0;
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
		return stack.getItem() instanceof MateriaItem;
	}

	@Override
	protected void onLoad() {
		dirty = true;
	}

	@Override
	protected void onContentsChanged(int slot) {
		onLoad(); //highly inefficient. TODO: make this better
	}
}
