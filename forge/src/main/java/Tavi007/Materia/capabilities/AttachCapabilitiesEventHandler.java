package Tavi007.Materia.capabilities;

import Tavi007.Materia.Materia;
import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.data.capabilities.MateriaLevelData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class AttachCapabilitiesEventHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
        if (!event.getCapabilities().containsKey(Constants.MATERIA_LEVEL_DATA_CAPABILITY)) {

            final MateriaLevelData data = new MateriaLevelData();  // TODO: is this correct?
            event.addCapability(Constants.MATERIA_LEVEL_DATA_CAPABILITY,
                    MateriaLevelDataCapability.createProvider(data));
        }
    }
}
