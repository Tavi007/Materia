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
    private Entity entitySpawnLocation;
    private BlockPos blockSpawnLocation;

    public SpellEntityPipelineEntry(SpellProjectileEntity entity, int spawnDelay, String element, Entity entitySpawnLocation) {
        this.entity = entity;
        this.spawnDelay = spawnDelay;
        this.element = element;
        this.entitySpawnLocation = entitySpawnLocation;
        this.blockSpawnLocation = null;
    }

    public SpellEntityPipelineEntry(SpellProjectileEntity entity, int spawnDelay, String element, BlockPos blockSpawnLocation) {
        this.entity = entity;
        this.spawnDelay = spawnDelay;
        this.element = element;
        this.entitySpawnLocation = null;
        this.blockSpawnLocation = blockSpawnLocation;
    }

    public Vec3 getSpawnLocation() {
        if (entitySpawnLocation != null) {
            return entitySpawnLocation.getEyePosition();
        }
        if (blockSpawnLocation != null) {
            return blockSpawnLocation.getCenter();
        }
        return null;
    }

    public void countDown() {
        spawnDelay--;
    }

    public boolean canSpawn() {
        return spawnDelay <= 0;
    }

    public SpellProjectileEntity getSpellEntity() {
        return entity;
    }

    public String getElement() {
        return Optional.ofNullable(element).orElse(BasePropertiesAPI.getDefaultAttackElement());
    }
}
