package Tavi007.Materia.client.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.client.gui.MateriaToolClientTooltipComponent;
import Tavi007.Materia.client.gui.MateriaToolTooltipComponent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StartupClientOnly {

    @SubscribeEvent
    public static void onRegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(MateriaToolTooltipComponent.class, MateriaToolClientTooltipComponent::new);
        Materia.LOGGER.info("Materia tooltip component factories registered.");
    }
}
