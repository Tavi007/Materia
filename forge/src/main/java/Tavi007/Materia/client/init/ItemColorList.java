package Tavi007.Materia.client.init;

import Tavi007.Materia.client.registry.ModColors;
import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.init.ModItems;
import Tavi007.Materia.common.items.Magicite;
import net.minecraft.world.level.ItemLike;
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
        ModColors.ITEM_COLORS.forEach((color, items) -> {
            event.register(color, items.toArray(ItemLike[]::new));
        });
    }
}