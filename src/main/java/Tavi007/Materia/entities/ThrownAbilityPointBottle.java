package Tavi007.Materia.entities;

import Tavi007.Materia.ServerConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ThrownAbilityPointBottle extends ThrowableItemProjectile {

    public ThrownAbilityPointBottle(EntityType<? extends ThrownAbilityPointBottle> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownAbilityPointBottle(Level level, LivingEntity livingEntity) {
        super(EntityType.EXPERIENCE_BOTTLE, livingEntity, level);
    }

    public ThrownAbilityPointBottle(Level level, double p_37514_, double p_37515_, double p_37516_) {
        super(EntityType.EXPERIENCE_BOTTLE, p_37514_, p_37515_, p_37516_, level);
    }

    protected Item getDefaultItem() {
        return Items.EXPERIENCE_BOTTLE;
    }

    protected float getGravity() {
        return 0.07F;
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level instanceof ServerLevel) {
            this.level.levelEvent(2002, this.blockPosition(), PotionUtils.getColor(Potions.LUCK));
            int amount = Mth.randomBetweenInclusive(random, ServerConfig.getApBottleMinAmount(), ServerConfig.getApBottleMaxAmount());
            AbilityPointOrb.award((ServerLevel) this.level, this.position(), amount);
            this.discard();
        }
    }
}
