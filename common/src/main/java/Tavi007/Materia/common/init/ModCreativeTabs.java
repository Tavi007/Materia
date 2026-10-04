package Tavi007.Materia.common.init;

import Tavi007.Materia.common.Constants;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import java.util.HashMap;

public class ModCreativeTabs {
    public static final HashMap<String, Supplier<CreativeModeTab>> TAB_MAP = new HashMap<>();

    public static final Supplier<CreativeModeTab> MATERIAS = register(
            Constants.MATERIA_TAB,
        () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .title(Component.translatable("itemGroup.materia"))
            .icon(ModItems.BASE_MATERIA.get()::getDefaultInstance)
            .displayItems((displayParams, output) ->
                    ModItems.MATERIAS.forEach((supplier) ->
                            output.accept(supplier.get())))
            .build());


    public static final Supplier<CreativeModeTab> MATERIA_TOOLS = register(
            Constants.MATERIA_TOOL_TAB,
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .title(Component.translatable("itemGroup.materia_tool"))
                    .icon(ModItems.MATERIA_DIAMOND_SWORD.get()::getDefaultInstance)
                    .displayItems((displayParams, output) ->
                            ModItems.MATERIA_TOOLS.forEach((supplier) ->
                                    output.accept(supplier.get())))
                    .build());

    public static final Supplier<CreativeModeTab> MISC = register(
            Constants.MISC_TAB,
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .title(Component.translatable("itemGroup.misc"))
                    .icon(ModItems.ABILITY_POINT_BOTTLE.get()::getDefaultInstance)
                    .displayItems((displayParams, output) ->
                            ModItems.MISC.forEach((supplier) ->
                                    output.accept(supplier.get())))
                    .build());

    private static Supplier<CreativeModeTab> register(String name, Supplier<CreativeModeTab> supplier) {
        Supplier<CreativeModeTab> memoized = Suppliers.memoize(supplier);
        TAB_MAP.put(name, memoized);
        return memoized;
    }
}
