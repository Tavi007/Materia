package Tavi007.Materia.client.gui.screens;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.inventory.menus.ParaMagicExtractorMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ParaMagicExtractorScreen extends AbstractContainerScreen<ParaMagicExtractorMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MOD_ID, "textures/gui/para_magic_extractor.png");

    // if I change the texture, change these values too to fix GUI
    public static final int IMAGE_WIDTH = 176;
    public static final int IMAGE_HEIGHT = 192;
    public static final int INPUT_SLOT_X = 62;
    public static final int INPUT_SLOT_Y = 21;
    public static final int BOTTLE_SLOT_X = 62;
    public static final int BOTTLE_SLOT_Y = 71;
    public static final int OUTPUT_SLOT_X = 98;
    public static final int OUTPUT_SLOT_Y = 37;

    public ParaMagicExtractorScreen(ParaMagicExtractorMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = IMAGE_WIDTH;
        this.imageHeight = IMAGE_HEIGHT;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
