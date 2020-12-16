package Tavi007.Materia.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import Tavi007.Materia.container.EquippingStationContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class EquippingStationScreen extends ContainerScreen<EquippingStationContainer> {

	private static final ResourceLocation TEXTURE = new ResourceLocation("materia", "textures/gui/equipping_station_container.png");
	
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
		super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
		this.font.drawString(matrixStack, this.title.getString(), 8.0f, 8.0f, 4210752);
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
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	    this.minecraft.getTextureManager().bindTexture(TEXTURE);
	}

}
