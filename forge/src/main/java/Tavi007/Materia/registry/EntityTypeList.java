package Tavi007.Materia.registry;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.registry.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeList {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Constants.MOD_ID);

    static {
        ModEntities.ENTITY_MAP.forEach(ENTITY_TYPES::register);
    }
}
