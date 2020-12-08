package Tavi007.Materia.capabilities.effects;

import net.minecraft.nbt.INBT;

public interface IMateriaEffect {

	public void addAP(int amount);
	
	//networking
	public INBT writeNBT();
	public void readNBT(final INBT nbt);
	
}
