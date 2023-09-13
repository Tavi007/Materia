package Tavi007.Materia.entities;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.init.EntityTypeList;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;

public class AbilityPointOrb extends Entity {

    private static final int LIFETIME = 6000;
    private static final int ENTITY_SCAN_PERIOD = 20;
    private static final int MAX_FOLLOW_DIST = 8;
    private static final int ORB_GROUPS_PER_AREA = 40;
    private static final double ORB_MERGE_DISTANCE = 0.5D;
    private int age;
    private int health = 5;
    public int value;
    private int count = 1;
    private Player followingPlayer;

    public AbilityPointOrb(Level level, double posX, double posY, double posZ, int value) {
        this(EntityTypeList.ABILITY_POINT_ORB.get(), level);
        this.setPos(posX, posY, posZ);
        this.setYRot((float) (this.random.nextDouble() * 360.0D));
        this.setDeltaMovement((this.random.nextDouble() * (double) 0.2F - (double) 0.1F) * 2.0D,
            this.random.nextDouble() * 0.2D * 2.0D,
            (this.random.nextDouble() * (double) 0.2F - (double) 0.1F) * 2.0D);
        this.value = value;
    }

    public AbilityPointOrb(EntityType<? extends AbilityPointOrb> entityType, Level level) {
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
        if (this.isEyeInFluid(FluidTags.WATER)) {
            this.setUnderwaterMovement();
        } else if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.03D, 0.0D));
        }

        if (this.level.getFluidState(this.blockPosition()).is(FluidTags.LAVA)) {
            this.setDeltaMovement((double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F),
                (double) 0.2F,
                (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F));
        }

        if (!this.level.noCollision(this.getBoundingBox())) {
            this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0D, this.getZ());
        }

        if (this.tickCount % 20 == 1) {
            this.scanForEntities();
        }

        if (this.followingPlayer != null && (this.followingPlayer.isSpectator() || this.followingPlayer.isDeadOrDying())) {
            this.followingPlayer = null;
        }

        if (this.followingPlayer != null) {
            Vec3 vec3 = new Vec3(this.followingPlayer.getX() - this.getX(),
                this.followingPlayer.getY() + (double) this.followingPlayer.getEyeHeight() / 2.0D - this.getY(),
                this.followingPlayer.getZ() - this.getZ());
            double d0 = vec3.lengthSqr();
            if (d0 < 64.0D) {
                double d1 = 1.0D - Math.sqrt(d0) / 8.0D;
                this.setDeltaMovement(this.getDeltaMovement().add(vec3.normalize().scale(d1 * d1 * 0.1D)));
            }
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        float f = 0.98F;
        if (this.onGround) {
            BlockPos pos = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
            f = this.level.getBlockState(pos).getFriction(this.level, pos, this) * 0.98F;
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply((double) f, 0.98D, (double) f));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, -0.9D, 1.0D));
        }

        ++this.age;
        if (this.age >= 6000) {
            this.discard();
        }

    }

    private void scanForEntities() {
        if (this.followingPlayer == null || this.followingPlayer.distanceToSqr(this) > 64.0D) {
            this.followingPlayer = this.level.getNearestPlayer(this, 8.0D);
        }

        if (this.level instanceof ServerLevel) {
            for (AbilityPointOrb AbilityPointOrb : this.level
                .getEntities(EntityTypeTest.forClass(AbilityPointOrb.class), this.getBoundingBox().inflate(0.5D), this::canMerge)) {
                this.merge(AbilityPointOrb);
            }
        }

    }

    public static void award(ServerLevel serverLevel, Vec3 position, int value) {
        while (value > 0) {
            int i = getExperienceValue(value);
            value -= i;
            if (!tryMergeToExisting(serverLevel, position, i)) {
                serverLevel.addFreshEntity(new AbilityPointOrb(serverLevel, position.x(), position.y(), position.z(), i));
            }
        }

    }

    private static boolean tryMergeToExisting(ServerLevel serverLevel, Vec3 position, int value) {
        AABB aabb = AABB.ofSize(position, 1.0D, 1.0D, 1.0D);
        int i = serverLevel.getRandom().nextInt(40);
        List<AbilityPointOrb> list = serverLevel.getEntities(EntityTypeTest.forClass(AbilityPointOrb.class), aabb, (p_147081_) -> {
            return canMerge(p_147081_, i, value);
        });
        if (!list.isEmpty()) {
            AbilityPointOrb AbilityPointOrb = list.get(0);
            ++AbilityPointOrb.count;
            AbilityPointOrb.age = 0;
            return true;
        } else {
            return false;
        }
    }

    private boolean canMerge(AbilityPointOrb orb) {
        return orb != this && canMerge(orb, this.getId(), this.value);
    }

    private static boolean canMerge(AbilityPointOrb orb, int id, int value) {
        return !orb.isRemoved() && (orb.getId() - id) % 40 == 0 && orb.value == value;
    }

    private void merge(AbilityPointOrb orb) {
        this.count += orb.count;
        this.age = Math.min(this.age, orb.age);
        orb.discard();
    }

    private void setUnderwaterMovement() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x * (double) 0.99F, Math.min(vec3.y + (double) 5.0E-4F, (double) 0.06F), vec3.z * (double) 0.99F);
    }

    protected void doWaterSplashEffect() {
    }

    public boolean hurt(DamageSource damageSource, float health) {
        if (this.level.isClientSide || this.isRemoved())
            return false; // Forge: Fixes MC-53850
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else if (this.level.isClientSide) {
            return true;
        } else {
            this.markHurt();
            this.health = (int) ((float) this.health - health);
            if (this.health <= 0) {
                this.discard();
            }

            return true;
        }
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putShort("Health", (short) this.health);
        compoundTag.putShort("Age", (short) this.age);
        compoundTag.putShort("Value", (short) this.value);
        compoundTag.putInt("Count", this.count);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        this.health = compoundTag.getShort("Health");
        this.age = compoundTag.getShort("Age");
        this.value = compoundTag.getShort("Value");
        this.count = Math.max(compoundTag.getInt("Count"), 1);
    }

    public void playerTouch(Player player) {
        if (!this.level.isClientSide) {
            if (player.takeXpDelay == 0) {
                player.takeXpDelay = 2;
                player.take(this, 1);

                List<ItemStack> stacks = getRelevantStacks(player);
                stacks.forEach(stack -> {
                    if (stack.getItem() instanceof IMateriaTool) {
                        MateriaCollectionHandler collection = CapabilityHelper.getMateriaCollectionHandler(stack);
                        collection.addAp(stack, this.value);
                    }
                });

                --this.count;
                if (this.count == 0) {
                    this.discard();
                }
            }
        }
    }

    private List<ItemStack> getRelevantStacks(Player player) {
        List<ItemStack> stacks = new ArrayList<>();
        stacks.add(player.getMainHandItem());
        player.getArmorSlots().forEach(stack -> stacks.add(stack));
        CuriosApi.getCuriosHelper().getEquippedCurios(player).ifPresent(curioConsumer -> {
            for (int i = 0; i < curioConsumer.getSlots(); i++) {
                stacks.add(curioConsumer.getStackInSlot(i));
            }
        });
        return stacks;
    }

    public int getValue() {
        return this.value;
    }

    public int getIcon() {
        if (this.value >= 2477) {
            return 10;
        } else if (this.value >= 1237) {
            return 9;
        } else if (this.value >= 617) {
            return 8;
        } else if (this.value >= 307) {
            return 7;
        } else if (this.value >= 149) {
            return 6;
        } else if (this.value >= 73) {
            return 5;
        } else if (this.value >= 37) {
            return 4;
        } else if (this.value >= 17) {
            return 3;
        } else if (this.value >= 7) {
            return 2;
        } else {
            return this.value >= 3 ? 1 : 0;
        }
    }

    public static int getExperienceValue(int value) {
        if (value >= 2477) {
            return 2477;
        } else if (value >= 1237) {
            return 1237;
        } else if (value >= 617) {
            return 617;
        } else if (value >= 307) {
            return 307;
        } else if (value >= 149) {
            return 149;
        } else if (value >= 73) {
            return 73;
        } else if (value >= 37) {
            return 37;
        } else if (value >= 17) {
            return 17;
        } else if (value >= 7) {
            return 7;
        } else {
            return value >= 3 ? 3 : 1;
        }
    }

    public boolean isAttackable() {
        return false;
    }

    public SoundSource getSoundSource() {
        return SoundSource.AMBIENT;
    }
}
