package Tavi007.Materia.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.Materia.Materia;
import Tavi007.Materia.data.pojo.SpellEntityPipelineEntry;
import Tavi007.Materia.entities.SpellProjectileEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Bus.FORGE)
public class SpellEntityPipeline {

    private static Map<ServerLevel, List<SpellEntityPipelineEntry>> pipeline = new HashMap<>();

    public static void addSpellEntityToPipeline(ServerLevel level, SpellProjectileEntity effect, int spawnDelay, String element, Entity spawnLocation) {
        List<SpellEntityPipelineEntry> levelPipeline = pipeline.getOrDefault(level, new ArrayList<>());
        levelPipeline.add(new SpellEntityPipelineEntry(effect, spawnDelay, element, spawnLocation));
        pipeline.put(level, levelPipeline);
    }

    public static void tick(ServerLevel level) {
        List<SpellEntityPipelineEntry> spawnedEntries = new ArrayList<>();
        List<SpellEntityPipelineEntry> levelPipeline = pipeline.getOrDefault(level, new ArrayList<>());
        levelPipeline.forEach(entry -> {
            entry.countDown();
            if (entry.canSpawn()) {
                SpellProjectileEntity entity = entry.getSpellEntity();
                entity.moveTo(entry.getSpawnLocation());
                entity.setDeltaMovement(entry.getShootDirection());
                level.addFreshEntity(entity);
                AttackDataAPI.putLayer(entity, new AttackLayer("magic", entry.getElement()), new ResourceLocation(Materia.MOD_ID, "spell"));
                spawnedEntries.add(entry);
            }
        });
        levelPipeline.removeAll(spawnedEntries);
    }

    @SubscribeEvent
    public static void onLevelTickEvent(LevelTickEvent event) {
        if (event.level instanceof ServerLevel serverLevel &&
            TickEvent.Phase.START.equals(event.phase)) {
            tick(serverLevel);
        }
    }
}
