package Tavi007.Materia.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import Tavi007.Materia.data.pojo.effects.SpellEntityEffect;
import Tavi007.Materia.particles.SpellEntityTrailParticleOption;
import Tavi007.Materia.util.DefaultResourceLocation;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class SpellProjectileEntity extends Projectile implements IEntityAdditionalSpawnData {

    private static final String DEFAULT_MESSAGE_ID = "blab.bla";

    private SpellEntityEffect effectData;
    private String messageId;

    public SpellProjectileEntity(EntityType<? extends SpellProjectileEntity> entityType, Level level, Entity sourceEntity, Vec3 shootDirection,
            SpellEntityEffect effectData, String messageId) {
        super(entityType, level);
        this.moveTo(sourceEntity.getX(), sourceEntity.getEyeY(), sourceEntity.getZ(), this.getYRot(), this.getXRot());
        this.effectData = effectData;
        this.messageId = messageId;
        this.setDeltaMovement(shootDirection); // .scale(getSpeed())
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
        effectData = new SpellEntityEffect(tag.getCompound("effect_data"));
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
        if (!this.getLevel().isClientSide()) {
            float damage = getDamage();
            if (Math.abs((double) damage) < 0.0001) {
                hitResult.getEntity().hurt(new EntityDamageSource(getMessage(), this), getDamage());
            }
            CommandSourceStack commandStack = getCommandSourceStack();
            performCommands(commandStack, getOnLivingEntityHitCommands());
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (!this.getLevel().isClientSide()) {
            CommandSourceStack commandStack = getCommandSourceStack();
            performCommands(commandStack, getOnBlockHitCommands());
        }
        this.discard();
    }

    private CommandSourceStack getCommandSourceStack() {
        return new CommandSourceStack(
            CommandSource.NULL,
            this.position(),
            this.getRotationVector(),
            (ServerLevel) this.getLevel(),
            4,
            "",
            CommonComponents.EMPTY,
            this.getServer(),
            this);
    }

    private void performCommands(CommandSourceStack commandStack, List<String> commands) {
        MinecraftServer minecraftserver = this.getLevel().getServer();
        commands.forEach(command -> {
            minecraftserver.getCommands().performPrefixedCommand(commandStack, command);
        });
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.AMBIENT;
    }

    protected ParticleOptions getTrailParticle() {
        return new SpellEntityTrailParticleOption(getTrailTexture());
    }

    protected String getMessage() {
        return Optional.ofNullable(messageId).orElse(DEFAULT_MESSAGE_ID);
    }

    // TODO get defaul values from server config
    protected float getDamage() {
        return Optional.ofNullable(effectData).map(SpellEntityEffect::getDamage).orElse(1.0f);
    }

    protected float getSpeed() {
        return Optional.ofNullable(effectData).map(SpellEntityEffect::getSpeed).orElse(1.0f);
    }

    public ResourceLocation getTexture() {
        return Optional.ofNullable(effectData)
            .map(SpellEntityEffect::getTexture)
            .map(ResourceLocation::new)
            .orElse(DefaultResourceLocation.SPELL_TEXTURE);
    }

    public ResourceLocation getTrailTexture() {
        return Optional.ofNullable(effectData)
            .map(SpellEntityEffect::getTrailTexture)
            .map(ResourceLocation::new)
            .orElse(DefaultResourceLocation.SPELL_TRAIL_TEXTURE);
    }

    private List<String> getOnHitCommands() {
        return Optional.ofNullable(effectData)
            .map(SpellEntityEffect::getOnHitCommands)
            .orElse(Collections.emptyList());
    }

    private List<String> getOnLivingEntityHitCommands() {
        List<String> commands = new ArrayList<>();
        commands.addAll(getOnHitCommands());
        commands.addAll(Optional.ofNullable(effectData)
            .map(SpellEntityEffect::getOnLivingEntityHitCommands)
            .orElse(Collections.emptyList()));
        return commands;
    }

    private List<String> getOnBlockHitCommands() {
        List<String> commands = new ArrayList<>();
        commands.addAll(getOnHitCommands());
        commands.addAll(Optional.ofNullable(effectData)
            .map(SpellEntityEffect::getOnBlockHitCommands)
            .orElse(Collections.emptyList()));
        return commands;
    }

    @Override
    protected void defineSynchedData() {
        // TODO Auto-generated method stub

    }

    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (this.level.isClientSide || (entity == null || !entity.isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
            super.tick();

            HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double newX = this.getX() + vec3.x;
            double newY = this.getY() + vec3.y;
            double newZ = this.getZ() + vec3.z;
            ProjectileUtil.rotateTowardsMovement(this, 0.2F);

            this.level.addParticle(this.getTrailParticle(), newX, newY + 0.1, newZ, 0.0D, 0.0D, 0.0D);
            this.setPos(newX, newY, newZ);
        } else {
            this.discard();
        }
    }
}
