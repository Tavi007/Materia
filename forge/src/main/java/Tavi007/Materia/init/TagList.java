package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class TagList {

    public static final TagKey<Item> AREA_CHANGING_MATERIA = register("area_changing_materia");

    private static TagKey<Item> register(String name) {
        return ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation(Materia.MOD_ID, name));
    }
}
