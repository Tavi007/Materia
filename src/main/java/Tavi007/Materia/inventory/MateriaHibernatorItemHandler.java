package Tavi007.Materia.inventory;

import Tavi007.Materia.inventory.container.EquippingStationContainer;
import Tavi007.Materia.inventory.container.MateriaHibernatorContainer;
import Tavi007.Materia.tileentity.MateriaHibernatorTileentity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class MateriaHibernatorItemHandler extends ItemStackHandler {
	
	private final MateriaHibernatorTileentity tile;
	private final MateriaHibernatorContainer container;

	public MateriaHibernatorItemHandler(MateriaHibernatorContainer container, MateriaHibernatorTileentity tile) {
		super(2);
		this.tile = tile;
		this.container = container;
	}
	
	@Override
    public int getSlots() {
        return 2;
    }

	@Override
	public int getSlotLimit(int slot) {
		if(slot == 1) {
			return 1;
		}
		return 64;
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return container.inventorySlots.get(slot).isItemValid(stack);
	}
	
	public ItemStack getMateriaStack() {
		return stacks.get(1);
	}
}
