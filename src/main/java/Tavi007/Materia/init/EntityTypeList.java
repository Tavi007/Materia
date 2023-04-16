package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.entity.AbilityPointOrb;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeList {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Materia.MOD_ID);

    public static final EntityType<AbilityPointOrb> ABILITY_POINT_ORB = ENTITY_TYPES.register("ability_point_orb",
        () -> EntityType.Builder.<AbilityPointOrb> of(AbilityPointOrb::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(6)
            .updateInterval(20)
            .build(""))
        .get();

}
