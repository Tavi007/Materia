package Tavi007.Materia.client.gui;

import Tavi007.Materia.common.Constants;
import net.minecraft.client.gui.GuiGraphics;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Matrix4f;
import Tavi007.Materia.client.util.RenderUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

public class MateriaToolClientComponent implements ClientTooltipComponent {

    private MateriaToolComponent componentData;

    public MateriaToolClientComponent(MateriaToolComponent componentData) {
        this.componentData = componentData;
    }

    @Override
    public int getHeight() {
        return componentData.getCollectionHeight() + componentData.getSelectedDescriptionHeight();
    }

    @Override
    public int getWidth(Font font) {
        return Math.max(componentData.getCollectionWidth(), componentData.getSelectedDescriptionWidth(font));
    }

    @Override
    public void renderText(Font font, int posX, int posY, Matrix4f matrix4f, MultiBufferSource.BufferSource bufferSource) {
        int yOffSet = 36;
        for (Pair<FormattedCharSequence, Integer> pair : componentData.getSelectedEffectDescriptions()) {
            //font.drawInBatch(pair.getLeft(), posX, posY + yOffSet, pair.getRight(), false, matrix4f, bufferSource, false, 0, 15728880);
            yOffSet += font.lineHeight;
        }
    }

    @Override
    public void renderImage(Font font, int posX, int posY, GuiGraphics guiGraphics) {
        RenderUtil.drawMateriaCollectionSlots(guiGraphics, posX, posY, componentData.getTopCollectionSizes());
        RenderUtil.drawMateriaCollectionSlots(guiGraphics, posX, posY + 18, componentData.getBotCollectionSizes());
        renderItems(font, 0, 4, posX, posY);
        renderItems(font, 4, 8, posX, posY + 18);
    }

    private void renderItems(Font font, int start, int end, int posX, int posY) {
        int xOffSet = -1;
        for (int i = start; i < end; i++) {
            ItemStack stack = componentData.getStack(i);
            if (stack != null && !stack.isEmpty()) {
                ItemRenderer itemRenderer = Constants.MINECRAFT.getItemRenderer();
//                itemRenderer.renderGuiItem(stack, posX + xOffSet, posY);
//                itemRenderer.renderGuiItemDecorations(font, stack, posX + xOffSet, posY);
            }
            xOffSet += 20;
        }
    }
}
