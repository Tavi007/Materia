package Tavi007.Materia.client.init;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.init.ModItems;
import Tavi007.Materia.common.items.Magicite;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = Constants.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = net.minecraftforge.api.distmarker.Dist.CLIENT
)
public class ItemColorList {

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
                    if (tintIndex == 1 && stack.getItem() instanceof Magicite item) {
                        return item.getColor();
                    }
                    return 0xFFFFFFFF;
                },
                ModItems.FIRE_MAGICITE.get(),
                ModItems.ICE_MAGICITE.get()
        );
    }
}