package Tavi007.Materia.client.util;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import Tavi007.Materia.Materia;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;

public class RenderUtil {

    public static void drawMateriaCollectionSlots(PoseStack poseStack, int startX, int startY, List<Integer> collectionSizes) {
        for (Integer collectionSize : collectionSizes) {
            ResourceLocation texture = new ResourceLocation(Materia.MOD_ID, "textures/gui/materia_collection_" + collectionSize + ".png");
            RenderSystem.setShaderTexture(0, texture);
            switch (collectionSize) {
            case 1:
                // gui_start_x, gui_start_y, texture_start_x, texture_start_y, width, height, size x, size y?
                GuiComponent.blit(poseStack, startX, startY, 0, 0, 16, 16, 16, 16);
                startX += 20;
                break;
            case 2:
                GuiComponent.blit(poseStack, startX, startY, 0, 0, 36, 16, 36, 16);
                startX += 40;
                break;
            case 3:
                GuiComponent.blit(poseStack, startX, startY, 0, 0, 56, 16, 56, 16);
                startX += 60;
                break;
            case 4:
                GuiComponent.blit(poseStack, startX, startY, 0, 0, 76, 16, 76, 16);
                break;
            default:
                break;
            }
        }
    }
}
