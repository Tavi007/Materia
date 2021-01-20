package Tavi007.Materia.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import Tavi007.Materia.Materia;
import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.inventory.container.EquippingStationContainer;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class EquippingStationScreen extends ContainerScreen<EquippingStationContainer> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Materia.MOD_ID, "textures/gui/equipping_station_container.png");

	public EquippingStationScreen(EquippingStationContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 175;
		this.ySize = 191;
	}

	@Override
	public void render(MatrixStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
		this.font.func_243248_b(matrixStack, this.title, (float)this.titleX, (float)this.titleY, 4210752);
		this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, (float)this.playerInventoryTitleY+28, 4210752);
	
		ItemStack toolStack = this.container.getMateriaToolStack();
		if (!toolStack.isEmpty() && toolStack.getItem() instanceof IMateriaTool) {
			Item item = toolStack.getItem();

			drawMateriaSlots(matrixStack, 9, 20, ((IMateriaTool) toolStack.getItem()).getTopCollectionSizes());
			drawMateriaSlots(matrixStack, 9, 75, ((IMateriaTool) toolStack.getItem()).getBotCollectionSizes());
			
			//draw effect text
			List<ITextComponent> textList = new ArrayList<ITextComponent>();
			ArrayList<MateriaEffect> effectList = CapabilityHelper.getEffects(toolStack);
			if(item instanceof PickaxeItem) {
				effectList.forEach( effect -> {
					effect.addPickaxeToolTip(textList);
				});
			}
			else if(item instanceof AxeItem) {
				effectList.forEach( effect -> {
					effect.addAxeToolTip(textList);
				});
			}
			else if(item instanceof ShovelItem) {
				effectList.forEach( effect -> {
					effect.addShovelToolTip(textList);
				});
			}
			else if(item instanceof SwordItem) {
				effectList.forEach( effect -> {
					effect.addSwordToolTip(textList);
				});
			}
//			else if(item instanceof WandItem) {
//				MateriaToolUtil.getEffectsTool((IMateriaTool) item).forEach( effect -> {
//					effect.addWandToolTip(text);
//				});
//			}
			
			// draw the text starting  (x,y)
			//not quite happy here. I want colorful text.
			int startX = 103;
			int[] startY = {9};
			textList.forEach( text -> {
				this.minecraft.fontRenderer.func_243246_a(matrixStack, text, (float) startX, (float) startY[0], Color.darkGray.getRGB());
				startY[0] += this.minecraft.fontRenderer.FONT_HEIGHT;
			});
			
		}
	}

	@SuppressWarnings("deprecation")
	private void drawMateriaSlots(MatrixStack matrixStack, int startX, int startY, int[] slots) {	
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		for (int i=0; i<slots.length; i++){
			switch(slots[i]) {
			case 1:
				this.blit(matrixStack, startX, startY, 0, 193, 15, 15);
				startX += 19;
				break;
			case 2:
				this.blit(matrixStack, startX, startY, 0, 209, 36, 15);
				startX += 40;
				break;
			case 3:
				this.blit(matrixStack, startX, startY, 0, 225, 57, 15);
				startX += 61;
				break;
			case 4:
				this.blit(matrixStack, startX, startY, 0, 241, 78, 15);
				break;
			default:
				break;
			}
		}

	}

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
