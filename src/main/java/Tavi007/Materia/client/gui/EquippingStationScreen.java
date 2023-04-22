package Tavi007.Materia.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import Tavi007.Materia.Materia;
import Tavi007.Materia.inventory.container.EquippingStationContainer;
import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class EquippingStationScreen extends AbstractContainerScreen<EquippingStationContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Materia.MOD_ID, "textures/gui/equipping_station_container.png");

    public EquippingStationScreen(EquippingStationContainer menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 175;
        this.imageHeight = 191;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);
        renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);

        ItemStack toolStack = getMenu().getMateriaToolStack();
        if (!toolStack.isEmpty() && toolStack.getItem() instanceof IMateriaTool) {
            IMateriaTool item = (IMateriaTool) toolStack.getItem();
            drawMateriaSlots(poseStack, x + 50, y + 21, item.getTopCollectionSizes());
            drawMateriaSlots(poseStack, x + 50, y + 76, item.getBotCollectionSizes());
        }
    }

    private void drawMateriaSlots(PoseStack poseStack, int startX, int startY, int[] slots) {
        for (int i = 0; i < slots.length; i++) {
            switch (slots[i]) {
            case 1:
                blit(poseStack, startX, startY, 0, 193, 15, 15);
                startX += 20;
                break;
            case 2:
                blit(poseStack, startX, startY, 0, 209, 36, 15);
                startX += 40;
                break;
            case 3:
                blit(poseStack, startX, startY, 0, 225, 57, 15);
                startX += 60;
                break;
            case 4:
                blit(poseStack, startX, startY, 0, 241, 78, 15);
                break;
            default:
                break;
            }
        }
    }
}
