package Tavi007.Materia.inventory;

import Tavi007.Materia.capabilities.toolslots.MateriaCollection;
import Tavi007.Materia.inventory.container.MateriaIncubatorContainer;
import Tavi007.Materia.tileentity.MateriaIncubatorTileentity;
import Tavi007.Materia.util.CapabilityHelper;
import Tavi007.Materia.util.MateriaEffectHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class MateriaIncubatorItemHandler extends ItemStackHandler {
	
	private final MateriaIncubatorTileentity tile;
	private final MateriaIncubatorContainer container;

	public MateriaIncubatorItemHandler(MateriaIncubatorContainer container, MateriaIncubatorTileentity tile) {
		super(2);
		this.tile = tile;
		this.container = container;
	}
	
	@Override
	public void onContentsChanged(int slot) {
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
