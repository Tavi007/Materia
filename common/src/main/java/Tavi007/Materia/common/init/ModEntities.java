package Tavi007.Materia.common.init;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.blocks.EquippingStationBlock;
import Tavi007.Materia.common.blocks.MateriaIncubatorBlock;
import Tavi007.Materia.common.entities.AbilityPointOrb;
import Tavi007.Materia.common.entities.SpellProjectileEntity;
import Tavi007.Materia.common.entities.ThrownAbilityPointBottle;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;

public class ModEntities {
    public static final HashMap<String, Supplier<? extends EntityType<?>>> ENTITY_MAP = new HashMap<>();

    public static final Supplier<EntityType<AbilityPointOrb>> ABILITY_POINT_ORB = Suppliers.memoize(
            () -> EntityType.Builder.<AbilityPointOrb> of(AbilityPointOrb::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(6)
                    .updateInterval(20)
                    .build(new ResourceLocation(Constants.MOD_ID, Constants.ABILITY_POINT_ORB).toString()));

    public static final Supplier<EntityType<ThrownAbilityPointBottle>> THROWN_ABILITY_POINT_BOTTLE = Suppliers.memoize(
            () -> EntityType.Builder.<ThrownAbilityPointBottle> of(ThrownAbilityPointBottle::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(6)
                    .updateInterval(20)
                    .build(new ResourceLocation(Constants.MOD_ID, "thrown_ability_point_bottle").toString()));

    public static final Supplier<EntityType<SpellProjectileEntity>> SPELL_PROJECTILE = Suppliers.memoize(
            () -> EntityType.Builder.<SpellProjectileEntity> of(SpellProjectileEntity::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build(new ResourceLocation(Constants.MOD_ID, "spell_projectile").toString()));

    static {
        ENTITY_MAP.put(Constants.ABILITY_POINT_ORB, ABILITY_POINT_ORB);
        ENTITY_MAP.put(Constants.THROWN_ABILITY_POINT_BOTTLE, THROWN_ABILITY_POINT_BOTTLE);
        ENTITY_MAP.put(Constants.SPELL_PROJECTILE, SPELL_PROJECTILE);
    }

}
