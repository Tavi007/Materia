package Tavi007.Materia.init.registrar;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.init.registrar.MenuFactory;
import Tavi007.Materia.common.init.registrar.MenuRegistrar;
import Tavi007.Materia.common.inventory.menus.MateriaIncubatorMenu;
import Tavi007.Materia.init.MenuList;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;

public class ForgeMenuRegistrar implements MenuRegistrar {

    @Override
    public <T extends AbstractContainerMenu> Supplier<MenuType<T>> register(String id, MenuFactory<T> supplier) {
        RegistryObject<MenuType<T>> object = MenuList.MENU_TYPES.register(id, () -> IForgeMenuType.create(supplier::create));
        return Suppliers.memoize(object::get);
    }
}
