package Tavi007.Materia.registry;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.registry.ModMenus;
import Tavi007.Materia.registry.registrar.ForgeMenuRegistrar;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MenuList {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Constants.MOD_ID);

    static {
        ForgeMenuRegistrar registrar = new ForgeMenuRegistrar();
        ModMenus.register(registrar);
    }

}
