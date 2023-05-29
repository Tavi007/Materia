package Tavi007.Materia.client.util;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import Tavi007.Materia.Materia;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;

public class RenderUtil {

    public static void drawMateriaCollectionSlots(PoseStack poseStack, int startX, int startY, List<Integer> collectionSizes) {
        int xOffSet = 0;
        for (Integer collectionSize : collectionSizes) {
            ResourceLocation texture = new ResourceLocation(Materia.MOD_ID, "textures/gui/materia_collection_" + collectionSize + ".png");
            RenderSystem.setShaderTexture(0, texture);
            int width = 16 + (collectionSize - 1) * 20;
            GuiComponent.blit(poseStack, startX + xOffSet, startY, 0, 0, width, 16, width, 16);
            xOffSet += 20 * collectionSize;
        }
    }
}
