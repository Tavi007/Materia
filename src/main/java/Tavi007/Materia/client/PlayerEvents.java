package Tavi007.Materia.client;

import Tavi007.Materia.Materia;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Materia.MOD_ID)
public class PlayerEvents {

    private static final Minecraft MINECRAFT = Minecraft.getInstance();

    @SubscribeEvent
    public static void mouseEvent(final InputEvent.MouseButton.Post event) {
        if (MINECRAFT.player == null) {
            return;
        }

        if (MINECRAFT.screen == null) {

        }
    }

}
