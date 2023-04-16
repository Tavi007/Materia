package Tavi007.Materia.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import Tavi007.Materia.Materia;
import Tavi007.Materia.inventory.container.EquippingStationContainer;
import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.item.ItemStack;

public class EquippingStationScreen extends ContainerScreen<EquippingStationContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Materia.MOD_ID, "textures/gui/equipping_station_container.png");

    public EquippingStationScreen(EquippingStationContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 175;
        this.ySize = 191;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.font.func_243248_b(matrixStack, this.title, (float) this.titleX, (float) this.titleY, 4210752);
        this.font.func_243248_b(matrixStack,
            this.playerInventory.getDisplayName(),
            (float) this.playerInventoryTitleX,
            (float) this.playerInventoryTitleY + 28,
            4210752);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);

        ItemStack toolStack = this.container.getMateriaToolStack();
        if (!toolStack.isEmpty() && toolStack.getItem() instanceof IMateriaTool) {
            IMateriaTool item = (IMateriaTool) toolStack.getItem();
            drawMateriaSlots(matrixStack, x + 50, y + 21, item.getTopCollectionSizes());
            drawMateriaSlots(matrixStack, x + 50, y + 76, item.getBotCollectionSizes());
        }
    }

    @SuppressWarnings("deprecation")
    private void drawMateriaSlots(MatrixStack matrixStack, int startX, int startY, int[] slots) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        for (int i = 0; i < slots.length; i++) {
            switch (slots[i]) {
            case 1:
                this.blit(matrixStack, startX, startY, 0, 193, 15, 15);
                startX += 20;
                break;
            case 2:
                this.blit(matrixStack, startX, startY, 0, 209, 36, 15);
                startX += 40;
                break;
            case 3:
                this.blit(matrixStack, startX, startY, 0, 225, 57, 15);
                startX += 60;
                break;
            case 4:
                this.blit(matrixStack, startX, startY, 0, 241, 78, 15);
                break;
            default:
                break;
            }
        }
    }
}
