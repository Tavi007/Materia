package Tavi007.Materia.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import Tavi007.Materia.client.util.RenderUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;

public class MateriaToolClientTooltipComponent implements ClientTooltipComponent {

    private MateriaToolTooltipComponent componentData;

    public MateriaToolClientTooltipComponent(MateriaToolTooltipComponent componentData) {
        this.componentData = componentData;
    }

    @Override
    public int getHeight() {
        return componentData.getHeight();
    }

    @Override
    public int getWidth(Font font) {
        return componentData.getWidth();
    }

    @Override
    public void renderImage(Font font, int posX, int posY, PoseStack poseStack, ItemRenderer itemRenderer, int p_169963_) {
        if (componentData.hasTopCollection()) {
            RenderUtil.drawMateriaCollectionSlots(poseStack, posX, posY, componentData.getTopCollectionSizes());
            posY += 18;
        }
        if (componentData.hasBotCollection()) {
            RenderUtil.drawMateriaCollectionSlots(poseStack, posX, posY, componentData.getBotCollectionSizes());
        }
    }
}
