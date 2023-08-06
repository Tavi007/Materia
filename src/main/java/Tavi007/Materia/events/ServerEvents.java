package Tavi007.Materia.events;

import Tavi007.Materia.Materia;
import Tavi007.Materia.data.pojo.MobData;
import Tavi007.Materia.entities.AbilityPointOrb;
import Tavi007.Materia.init.ReloadListenerList;
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
        LivingEntity entity = event.getEntity();
        if (entity.getLevel() instanceof ServerLevel serverLevel) {
            MobData mobData = ReloadListenerList.MOB_DATA_MANAGER.getMobData(entity);
            AbilityPointOrb.award(serverLevel, entity.getPosition(0), mobData.getApAmount());
        }
    }

}
