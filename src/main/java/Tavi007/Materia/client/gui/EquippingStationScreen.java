package Tavi007.Materia.client.gui;

import java.awt.Color;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import Tavi007.Materia.Materia;
import Tavi007.Materia.inventory.container.EquippingStationContainer;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.items.MateriaToolSlot;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class EquippingStationScreen extends ContainerScreen<EquippingStationContainer> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Materia.MOD_ID, "textures/gui/equipping_station_container.png");

	public EquippingStationScreen(EquippingStationContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 176;
		this.ySize = 166;
	}

	/*
	 * This method is called every tick and does the basic rendering of the GUI,
	 * rendering the background, and rendering any hovering tool tips, if the user
	 * does happen to have their mouse over it.
	 */
	@Override
	public void render(MatrixStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	/*
	 * This method is called every tick and is for drawing everything in front of
	 * the background, that being slots, tooltips, and anything that is infront. In
	 * here we draw the 2 strings for the name of this screen and the name of the
	 * player inventory.
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
		this.font.func_243248_b(matrixStack, this.title, (float)this.titleX, (float)this.titleY, 4210752);
		this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, (float)this.playerInventoryTitleY, 4210752);
		if (!this.container.hasEmptyMateriaToolSlot()) {
			IMateriaTool materiaTool = (IMateriaTool) this.container.getMateriaToolStack().getItem();
			drawMateriaSlots(matrixStack, 35, 26, materiaTool.getTopSlots());
			drawMateriaSlots(matrixStack, 35, 49, materiaTool.getBotSlots());
		}
	}

	private void drawMateriaSlots(MatrixStack matrixStack, int startX, int startY, MateriaToolSlot[] slots) {
		int[] x = {startX};
		int[] y = {startY};
		for (int i=0; i<slots.length; i++){
			switch(slots[i].getNoSlots()) {
			case 1:
				this.minecraft.fontRenderer.drawString(matrixStack, " 0 ", (float) x[0], (float) y[0], Color.darkGray.getRGB());
				x[0] += 17;
				break;
			case 2:
				this.minecraft.fontRenderer.drawString(matrixStack, " 0= =0 ", (float) x[0], (float) y[0], Color.darkGray.getRGB());
				x[0] += 34;
				break;
			case 3:
				this.minecraft.fontRenderer.drawString(matrixStack, " 0= =0= =0 ", (float) x[0], (float) y[0], Color.darkGray.getRGB());
				x[0] += 51;
				break;
			default:
				break;
			}
		}

	}

	/*
	 * This method is called every tick and is for drawing everything in the
	 * background(behind the slots and any popups). In here we first use
	 * RenderSystem.color4f, which makes it do any following rendering operations
	 * with full red, green, blue and alpha. Then we bind the background texture to
	 * this screen. Then we get the X and Y by taking the xSize and ySize from the
	 * width and height, and dividing those values by 2. This allows us to get the
	 * position of where we should start drawing the texture. Then we call the blit
	 * method which takes in the X and Y positions that we want to start drawing at.
	 * Then the location in the texture that we want to start drawing from, in our
	 * case the top left(0, 0). Then the width and height that we are drawing(which
	 * is our X and Y size).
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);

		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
	}

}
