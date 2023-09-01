package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.entities.AbilityPointOrb;
import Tavi007.Materia.entities.SpellProjectileEntity;
import Tavi007.Materia.entities.ThrownAbilityPointBottle;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypeList {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Materia.MOD_ID);

    public static final RegistryObject<EntityType<AbilityPointOrb>> ABILITY_POINT_ORB = ENTITY_TYPES.register("ability_point_orb",
        () -> EntityType.Builder.<AbilityPointOrb> of(AbilityPointOrb::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(6)
            .updateInterval(20)
            .build(new ResourceLocation(Materia.MOD_ID, "ability_point_orb").toString()));

    public static final RegistryObject<EntityType<ThrownAbilityPointBottle>> THROWN_ABILITY_POINT_BOTTLE = ENTITY_TYPES.register("thrown_ability_point_bottle",
        () -> EntityType.Builder.<ThrownAbilityPointBottle> of(ThrownAbilityPointBottle::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(6)
            .updateInterval(20)
            .build(new ResourceLocation(Materia.MOD_ID, "thrown_ability_point_bottle").toString()));

    public static final RegistryObject<EntityType<SpellProjectileEntity>> SPELL_PROJECTILE = ENTITY_TYPES.register("spell_projectile",
        () -> EntityType.Builder.<SpellProjectileEntity> of(SpellProjectileEntity::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(6)
            .updateInterval(20)
            .build(new ResourceLocation(Materia.MOD_ID, "spell_projectile").toString()));
}
