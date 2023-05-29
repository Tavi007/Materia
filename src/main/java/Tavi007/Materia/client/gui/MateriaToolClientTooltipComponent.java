package Tavi007.Materia.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import Tavi007.Materia.client.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

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
        RenderUtil.drawMateriaCollectionSlots(poseStack, posX, posY, componentData.getTopCollectionSizes());
        RenderUtil.drawMateriaCollectionSlots(poseStack, posX, posY + 18, componentData.getBotCollectionSizes());
        renderItems(font, 0, 4, posX, posY);
        renderItems(font, 4, 8, posX, posY + 18);
    }

    private void renderItems(Font font, int start, int end, int posX, int posY) {
        int xOffSet = -1;
        for (int i = start; i < end; i++) {
            ItemStack stack = componentData.getStack(i);
            if (stack != null && !stack.isEmpty()) {
                ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                itemRenderer.renderGuiItem(stack, posX + xOffSet, posY);
                itemRenderer.renderGuiItemDecorations(font, stack, posX + xOffSet, posY);
            }
            xOffSet += 20;
        }
    }
}
