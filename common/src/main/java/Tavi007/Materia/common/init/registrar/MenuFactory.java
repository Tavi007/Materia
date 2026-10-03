package Tavi007.Materia.common.init.registrar;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

@FunctionalInterface
public interface MenuFactory<T extends AbstractContainerMenu> {
    T create(int containerId, Inventory inventory);
}