package Tavi007.Materia.entities;

import Tavi007.Materia.init.EntityTypeList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class SpellProjectileEntity extends Projectile {

    private float damage;
    private String msgId;
    private String texture;

    public SpellProjectileEntity(Level level, double posX, double posY, double posZ, float damage, String msgId, String texture) {
        this(EntityTypeList.SPELL_PROJECTILE.get(), level);
        this.setPos(posX, posY, posZ);
        this.setYRot((float) (this.random.nextDouble() * 360.0D));
        this.setDeltaMovement((this.random.nextDouble() * (double) 0.2F - (double) 0.1F) * 2.0D,
            this.random.nextDouble() * 0.2D * 2.0D,
            (this.random.nextDouble() * (double) 0.2F - (double) 0.1F) * 2.0D);
        this.damage = damage;
        this.msgId = msgId;
        this.texture = texture;

    }

    public SpellProjectileEntity(EntityType<? extends SpellProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    protected void defineSynchedData() {
    }

    public void tick() {
        super.tick();
        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();

    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putShort("damage", (short) damage);
        compoundTag.putString("msg_id", msgId);
        compoundTag.putString("texture", texture);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        this.damage = compoundTag.getShort("damage");
        this.msgId = compoundTag.getString("msg_id");
        this.texture = compoundTag.getString("texture");
    }

    public void hitLivingEntity(LivingEntity entity) {
        if (!this.level.isClientSide) {
            entity.hurt(new EntityDamageSource(msgId, this), damage);
        }
    }

    public SoundSource getSoundSource() {
        return SoundSource.AMBIENT;
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(texture);
    }
}
