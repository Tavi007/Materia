package Tavi007.Materia.inventory;

import Tavi007.Materia.inventory.menus.MateriaIncubatorMenu;
import Tavi007.Materia.tileentity.MateriaIncubatorTileentity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class MateriaIncubatorItemHandler extends ItemStackHandler {

    private final MateriaIncubatorTileentity tile;
    private final MateriaIncubatorMenu menu;

    public MateriaIncubatorItemHandler(MateriaIncubatorMenu menu, MateriaIncubatorTileentity tile) {
        super(2);
        this.tile = tile;
        this.menu = menu;
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
        if (slot == 0) {
            return 1;
        }
        return 64;
    }

    @Override
    public boolean isItemValid(int slotId, ItemStack stack) {
        return menu.getSlot(slotId).mayPlace(stack);
    }

    public ItemStack getMateriaStack() {
        return stacks.get(1);
    }
}
