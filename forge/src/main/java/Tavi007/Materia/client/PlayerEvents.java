package Tavi007.Materia.client;

import com.mojang.blaze3d.platform.InputConstants;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.client.gui.MateriaToolComponent;
import Tavi007.Materia.client.gui.SelectMateriaEffectScreen;
import Tavi007.Materia.client.init.KeyBindingList;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.Input;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Materia.MOD_ID)
public class PlayerEvents {

    private static final Minecraft MINECRAFT = Materia.MINECRAFT;

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
        if (MINECRAFT.screen instanceof SelectMateriaEffectScreen) {
            MINECRAFT.screen.onClose();
            MINECRAFT.player.closeContainer();
            return true;
        }
        if (key == KeyBindingList.SELECT_MATERIA_EFFECT.getKey().getValue()) {
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
        if (stack != null && stack.getItem() instanceof IMateriaTool materiaTool) {
            MateriaCollectionHandler collectionHandler = CapabilityHelper.getMateriaCollectionHandler(stack);
            MateriaToolComponent component = new MateriaToolComponent(materiaTool, collectionHandler);
            MINECRAFT.setScreen(new SelectMateriaEffectScreen(component));
            return true;
        }
        return false;
    }

    @SubscribeEvent
    public static void updateInputEvent(MovementInputUpdateEvent event) {
        if (MINECRAFT.screen instanceof SelectMateriaEffectScreen) {
            Options settings = MINECRAFT.options;
            Input eInput = event.getInput();
            eInput.up = InputConstants.isKeyDown(MINECRAFT.getWindow().getWindow(), settings.keyUp.getKey().getValue());
            eInput.down = InputConstants.isKeyDown(MINECRAFT.getWindow().getWindow(), settings.keyDown.getKey().getValue());
            eInput.left = InputConstants.isKeyDown(MINECRAFT.getWindow().getWindow(), settings.keyLeft.getKey().getValue());
            eInput.right = InputConstants.isKeyDown(MINECRAFT.getWindow().getWindow(), settings.keyRight.getKey().getValue());

            eInput.forwardImpulse = eInput.up == eInput.down ? 0.0F : (eInput.up ? 1.0F : -1.0F);
            eInput.leftImpulse = eInput.left == eInput.right ? 0.0F : (eInput.left ? 1.0F : -1.0F);
            eInput.jumping = InputConstants.isKeyDown(MINECRAFT.getWindow().getWindow(), settings.keyJump.getKey().getValue());
            eInput.shiftKeyDown = InputConstants.isKeyDown(MINECRAFT.getWindow().getWindow(), settings.keyShift.getKey().getValue());
            if (MINECRAFT.player.isMovingSlowly()) {
                eInput.leftImpulse = (float) ((double) eInput.leftImpulse * 0.3D);
                eInput.forwardImpulse = (float) ((double) eInput.forwardImpulse * 0.3D);
            }
        }
    }
}
