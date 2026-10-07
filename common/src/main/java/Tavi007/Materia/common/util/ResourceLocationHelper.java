package Tavi007.Materia.common.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ResourceLocationHelper {

    private static Function<Item, ResourceLocation> itemResolver;
    private static Function<EntityType<?>, ResourceLocation> entityResolver;

    public static void init(
            Function<Item, ResourceLocation> itemResolver,
            Function<EntityType<?>, ResourceLocation> entityResolver
    ) {
        ResourceLocationHelper.itemResolver = itemResolver;
        ResourceLocationHelper.entityResolver = entityResolver;
    }

    public static ResourceLocation get(Item item) {
        return itemResolver.apply(item);
    }

    public static ResourceLocation get(EntityType<?> entity) {
        return entityResolver.apply(entity);
    }
}
