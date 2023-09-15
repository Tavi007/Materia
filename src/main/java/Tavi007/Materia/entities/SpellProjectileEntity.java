package Tavi007.Materia.entities;

import java.util.Optional;

import Tavi007.Materia.Materia;
import Tavi007.Materia.data.pojo.effects.SpellEntityEffect;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class SpellProjectileEntity extends AbstractHurtingProjectile implements IEntityAdditionalSpawnData {

    private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(Materia.MOD_ID, "textures/entity/default_spell_projectile.png");
    private static final String DEFAULT_MESSAGE_ID = "blab.bla";

    private SpellEntityEffect effectData;
    private String messageId;

    public SpellProjectileEntity(EntityType<? extends SpellProjectileEntity> entityType, Level level, Entity sourceEntity, Vec3 shootDirection,
            SpellEntityEffect effectData, String messageId) {
        super(entityType, sourceEntity.getX(), sourceEntity.getEyeY(), sourceEntity.getZ(), shootDirection.x, shootDirection.y, shootDirection.z, level);
        this.effectData = effectData;
        this.messageId = messageId;
        this.setDeltaMovement(shootDirection.scale(getSpeed()));
    }

    public SpellProjectileEntity(EntityType<? extends SpellProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        CompoundTag compoundTag = effectData.serializeNBT();
        tag.put("effect_data", compoundTag);
        tag.putString("message_id", messageId);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        effectData = new SpellEntityEffect("", "", 0, 0);
        effectData.deserializeNBT(tag.getCompound("effect_data"));
        messageId = tag.getString("message_id");
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buf) {
        effectData.encode(buf);
        buf.writeUtf(messageId);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buf) {
        effectData = new SpellEntityEffect(buf);
        messageId = buf.readUtf();
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        hitResult.getEntity().hurt(new EntityDamageSource(getMessage(), this), getDamage());

        // TODO: add command handling here
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);

        // TODO: add command handling here
        this.discard();
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.AMBIENT;
    }

    // TODO define through effect data
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SMOKE;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    protected String getMessage() {
        return Optional.ofNullable(messageId).orElse(DEFAULT_MESSAGE_ID);
    }

    protected float getDamage() {
        return Optional.ofNullable(effectData).map(SpellEntityEffect::getDamage).orElse(1.0f);
    }

    protected float getSpeed() {
        return Optional.ofNullable(effectData).map(SpellEntityEffect::getSpeed).orElse(1.0f);
    }

    public ResourceLocation getTexture() {
        return DEFAULT_TEXTURE;
        // return Optional.ofNullable(effectData)
        // .map(SpellEntityEffect::getTexture)
        // .map(ResourceLocation::new)
        // .orElse(DEFAULT_TEXTURE);
    }
}
