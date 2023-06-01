package Tavi007.Materia.client.init;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import Tavi007.Materia.Materia;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyBindingList {

    private static final String keyMappingCategory = "Materia";

    public static final KeyMapping SELECT_MATERIA_EFFECT = new KeyMapping("key.materia.select.effect",
        KeyConflictContext.UNIVERSAL,
        InputConstants.Type.MOUSE,
        GLFW.GLFW_MOUSE_BUTTON_MIDDLE,
        keyMappingCategory);

    @SubscribeEvent
    public static void onRegisterKeyMappingsEvent(RegisterKeyMappingsEvent event) {
        event.register(SELECT_MATERIA_EFFECT);
        Materia.LOGGER.info("Materia key mappings registered.");
    }

}
