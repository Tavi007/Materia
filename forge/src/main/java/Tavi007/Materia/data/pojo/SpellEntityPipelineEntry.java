package Tavi007.Materia.data.pojo;

import java.util.Optional;

import Tavi007.ElementalCombat.api.BasePropertiesAPI;
import Tavi007.Materia.entities.SpellProjectileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class SpellEntityPipelineEntry {

    private SpellProjectileEntity entity;
    private int spawnDelay;
    private String element;
    private Entity spawnSourceEntity;
    private BlockPos blockSpawnLocation;
    private Vec3 blockShootDirection;

    public SpellEntityPipelineEntry(SpellProjectileEntity entity, int spawnDelay, String element, Entity spawnSourceEntity) {
        this.entity = entity;
        this.spawnDelay = spawnDelay;
        this.element = element;
        this.spawnSourceEntity = spawnSourceEntity;
        this.blockSpawnLocation = null;
        this.blockShootDirection = null;
    }

    public SpellEntityPipelineEntry(SpellProjectileEntity entity, int spawnDelay, String element, BlockPos blockSpawnLocation, Vec3 blockShootDirection) {
        this.entity = entity;
        this.spawnDelay = spawnDelay;
        this.element = element;
        this.spawnSourceEntity = null;
        this.blockSpawnLocation = blockSpawnLocation;
        this.blockShootDirection = blockShootDirection;
    }

    public Vec3 getSpawnLocation() {
        if (spawnSourceEntity != null) {
            return spawnSourceEntity.getEyePosition();
        }
        if (blockSpawnLocation != null) {
            return blockSpawnLocation.getCenter();
        }
        return null;
    }

    public Vec3 getShootDirection() {
        if (spawnSourceEntity != null) {
            return spawnSourceEntity.getLookAngle();
        }
        if (blockShootDirection != null) {
            return blockShootDirection;
        }
        return null;
    }

    public void countDown() {
        spawnDelay--;
    }

    public boolean canSpawn() {
        return spawnDelay <= 0 && (canSpawnFromEntity() || canSpawnFromBlock());
    }

    private boolean canSpawnFromEntity() {
        return spawnSourceEntity != null;
    }

    private boolean canSpawnFromBlock() {
        return blockSpawnLocation != null && blockShootDirection != null;
    }

    public SpellProjectileEntity getSpellEntity() {
        return entity;
    }

    public String getElement() {
        return Optional.ofNullable(element).orElse(BasePropertiesAPI.getDefaultAttackElement());
    }
}
