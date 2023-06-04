package Tavi007.Materia.client;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.client.gui.SelectMateriaEffectScreen;
import Tavi007.Materia.client.init.KeyBindingList;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Materia.MOD_ID)
public class PlayerEvents {

    private static final Minecraft MINECRAFT = Minecraft.getInstance();

    @SubscribeEvent
    public static void mouseEvent(final InputEvent.MouseButton.Post event) {
        if (MINECRAFT.player == null || event.getAction() != 1) {
            return;
        }
        if (checkSelectScreen(event.getButton())) {
            return;
        }
    }

    @SubscribeEvent
    public static void keyEvent(final InputEvent.Key event) {
        if (MINECRAFT.player == null || event.getAction() != 1) {
            return;
        }
        if (checkSelectScreen(event.getKey())) {
            return;
        }
    }

    private static boolean checkSelectScreen(int key) {
        if (key == KeyBindingList.SELECT_MATERIA_EFFECT.getKey().getValue()) {
            if (MINECRAFT.screen instanceof SelectMateriaEffectScreen) {
                MINECRAFT.player.closeContainer();
                return true;
            }
            if (MINECRAFT.screen == null) {
                if (checkAndOpenSelectScreen(MINECRAFT.player.getMainHandItem())) {
                    return true;
                }
                if (checkAndOpenSelectScreen(MINECRAFT.player.getOffhandItem())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkAndOpenSelectScreen(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof IMateriaTool) {
            MateriaCollectionHandler collectionHandler = CapabilityHelper.getMateriaCollectionHandler(stack);
            MINECRAFT.setScreen(new SelectMateriaEffectScreen(collectionHandler));
            return true;
        }
        return false;

    }
}
