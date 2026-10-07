package Tavi007.Materia.common.init.registrar;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public interface MenuRegistrar {
    <T extends AbstractContainerMenu> MenuType<T> register(String id, MenuFactory<T> supplier);
}
