package Tavi007.Materia.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import Tavi007.Materia.Materia;
import Tavi007.Materia.client.util.RenderUtil;
import Tavi007.Materia.inventory.menus.EquippingStationMenu;
import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class EquippingStationScreen extends AbstractContainerScreen<EquippingStationMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Materia.MOD_ID, "textures/gui/equipping_station_menu.png");

    public EquippingStationScreen(EquippingStationMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 175;
        this.imageHeight = 191;
        this.inventoryLabelY = this.imageHeight - 94;
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
            RenderUtil.drawMateriaCollectionSlots(poseStack, x + 50, y + 20, item.getTopCollectionSizes());
            RenderUtil.drawMateriaCollectionSlots(poseStack, x + 50, y + 75, item.getBotCollectionSizes());
        }
    }
}
