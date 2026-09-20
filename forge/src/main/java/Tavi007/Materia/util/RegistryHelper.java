package Tavi007.Materia.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHelper {

    public static Item getItem(ResourceLocation resourceLocation) {
        return ForgeRegistries.ITEMS.getValue(resourceLocation);
    }

    public static TagKey<Item> getTagKey(ResourceLocation resourceLocation) {
        return ForgeRegistries.ITEMS.tags().getTagNames().filter(tagKey -> tagKey.location().equals(resourceLocation)).findFirst().orElse(null);
    }

}
