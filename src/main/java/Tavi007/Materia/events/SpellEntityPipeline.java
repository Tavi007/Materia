package Tavi007.Materia.events;

import java.util.ArrayList;
import java.util.List;

import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.Materia.Materia;
import Tavi007.Materia.data.pojo.SpellEntityPipelineEntry;
import Tavi007.Materia.entities.SpellProjectileEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Bus.FORGE)
public class SpellEntityPipeline {

    private static List<SpellEntityPipelineEntry> pipeline = new ArrayList<>();

    public static void addSpellEntityToPipeline(SpellProjectileEntity effect, int spawnDelay, String element, Entity spawnLocation) {
        pipeline.add(new SpellEntityPipelineEntry(effect, spawnDelay, element, spawnLocation));
    }

    public static void tick(ServerLevel level) {
        List<SpellEntityPipelineEntry> spawnedEntries = new ArrayList<>();
        pipeline.forEach(entry -> {
            entry.countDown();
            if (entry.canSpawn()) {
                SpellProjectileEntity entity = entry.getSpellEntity();
                level.addFreshEntity(entity);
                AttackDataAPI.putLayer(entity, new AttackLayer("magic", entry.getElement()), new ResourceLocation(Materia.MOD_ID, "spell"));
                spawnedEntries.add(entry);
            }
        });
        pipeline.removeAll(spawnedEntries);
    }

    @SubscribeEvent
    public static void onLevelTickEvent(LevelTickEvent event) {
        if (event.level instanceof ServerLevel serverLevel) {
            tick(serverLevel);
        }
    }
}
