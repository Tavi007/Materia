package Tavi007.Materia.init;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.entities.AbilityPointOrb;
import Tavi007.Materia.common.entities.SpellProjectileEntity;
import Tavi007.Materia.common.entities.ThrownAbilityPointBottle;
import Tavi007.Materia.common.init.ModEntities;
import Tavi007.Materia.common.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypeList {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Constants.MOD_ID);

    static {
        ModEntities.ENTITY_MAP.forEach(ENTITY_TYPES::register);
    }
}
