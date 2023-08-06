package Tavi007.Materia.events;

import Tavi007.Materia.Materia;
import Tavi007.Materia.entities.AbilityPointOrb;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public static void onLivingEntityDeath(LivingDeathEvent event) {
        Materia.LOGGER.info("entity died");

        LivingEntity entity = event.getEntity();
        if (entity.getLevel()instanceof ServerLevel serverLevel) {
            int amount = 10; // TODO: use data from reload listener
            AbilityPointOrb.award(serverLevel, entity.getPosition(0), amount);
        }
    }

}
