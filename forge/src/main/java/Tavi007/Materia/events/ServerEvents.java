package Tavi007.Materia.events;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.data.datapack.MobDataManager;
import Tavi007.Materia.common.data.pojo.MobData;
import Tavi007.Materia.common.entities.AbilityPointOrb;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public static void onLivingEntityDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level() instanceof ServerLevel serverLevel) {
            MobData mobData = MobDataManager.get(entity);
            AbilityPointOrb.award(serverLevel, entity.getPosition(0), mobData.getApAmount());
        }
    }

}
