package Tavi007.Materia.client.gui;

import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.recipes.effects.configuration.AbstractMateriaEffectConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class SelectMateriaEffectScreen extends Screen {

    // open/close animation
    private final float ANIMATION_LENGTH = 0.40f;
    private static final float PRECISION = 5.0f;
    private float totalTime;
    private float prevTick;
    private float extraTick;
    private boolean opening;

    private MateriaCollectionHandler materiaCollection;
    private int newSelectedConfiguration;

    public SelectMateriaEffectScreen(MateriaCollectionHandler materiaCollection) {
        super(Component.literal(""));
        this.minecraft = Minecraft.getInstance();
        this.materiaCollection = materiaCollection;
        this.opening = true;

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void tick() {
        if (totalTime != ANIMATION_LENGTH) {
            extraTick++;
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);
        // new midpoint of coordinate system is center of screen
        int centerOfScreenX = width / 2;
        int centerOfScreenY = height / 2;
        double posX = mouseX - centerOfScreenX;
        double posY = mouseY - centerOfScreenY;

        // polar coordinate with (r=1, phi=0) <=> (x=0, y=1)
        double rSquared = posX * posX + posY * posY;
        double phi = Math.atan2(posY, posX);

        // radius definitions
        float radiusIn = Math.max(0.1f, 45);
        float radiusOut = radiusIn * 2;
        float itemRadius = (radiusIn + radiusOut) * 0.5f;

        // animation progress
        float openAnimation = opening ? totalTime / ANIMATION_LENGTH : 1.0f - totalTime / ANIMATION_LENGTH;
        float currTick = minecraft.getFrameTime();
        totalTime += (currTick + extraTick - prevTick) / 20f;
        extraTick = 0;
        prevTick = currTick;
        float animationProgress = Mth.clamp(openAnimation, 0, 1);
        animationProgress = (float) (1 - Math.pow(1 - animationProgress, 3));

        // divide selection ring into slices
        Map<List<ItemStack>, List<AbstractMateriaEffectConfiguration>> stacksToConfigurationMapping = materiaCollection
            .getItemstacksToEffectConfigurationMapping();
        int numberOfOptions = Math.min(MateriaCollectionHandler.MAX_ITEM_SLOTS, stacksToConfigurationMapping.size());

        // compute currently selected Index
        if (opening) {
            newSelectedConfiguration = materiaCollection.getSelectedConfigurationsIndex();
            for (int i = 0; i < numberOfOptions; i++) {
                float sliceBorderLeft = ((i - 0.5f) / (float) numberOfOptions) + 0.25f;
                float sliceBorderRight = ((i + 0.5f) / (float) numberOfOptions) + 0.25f;
                if (phi >= sliceBorderLeft &&
                    phi < sliceBorderRight &&
                    rSquared >= radiusIn * radiusIn &&
                    rSquared < radiusOut * radiusOut) {
                    newSelectedConfiguration = i;
                    break;
                }
            }
        }

        // draw select ring
        drawSlices(poseStack, numberOfOptions, centerOfScreenX, centerOfScreenY, radiusIn, radiusOut);

        // draw description in the middle
        drawCenteredString(poseStack, font, width / 2, (height - font.lineHeight) / 2, materiaCollection.getEffectConfigurations(newSelectedConfiguration));
        poseStack.popPose();

    }

    private void drawSlices(PoseStack poseStack, int numberOfOptions, int posX, int posY, float radiusIn, float radiusOut) {
        poseStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        for (int i = 0; i < numberOfOptions; i++) {
            float leftBorder = ((i - 0.5f) / (float) numberOfOptions) + 0.25f;
            float rightBorder = ((i + 0.5f) / (float) numberOfOptions) + 0.25f;
            if (newSelectedConfiguration == i) {
                drawSlice(buffer, posX, posY, 10, radiusIn, radiusOut * 1.25f, leftBorder * 1.25f, rightBorder * 1.25f, 63, 161, 191, 60);
            } else
                drawSlice(buffer, posX, posY, 10, radiusIn, radiusOut, leftBorder, rightBorder, 0, 0, 0, 64);
        }
        tessellator.end();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();

    }

    private void drawSlice(BufferBuilder buffer, float x, float y, float z, float radiusIn, float radiusOut, float startAngle, float endAngle, int r, int g,
            int b, int a) {
        float angle = endAngle - startAngle;
        int sections = Math.max(1, Mth.ceil(angle / PRECISION));

        angle = endAngle - startAngle;

        for (int i = 0; i < sections; i++) {
            float angle1 = startAngle + (i / (float) sections) * angle;
            float angle2 = startAngle + ((i + 1) / (float) sections) * angle;

            float pos1InX = x + radiusIn * (float) Math.cos(angle1);
            float pos1InY = y + radiusIn * (float) Math.sin(angle1);
            float pos1OutX = x + radiusOut * (float) Math.cos(angle1);
            float pos1OutY = y + radiusOut * (float) Math.sin(angle1);
            float pos2OutX = x + radiusOut * (float) Math.cos(angle2);
            float pos2OutY = y + radiusOut * (float) Math.sin(angle2);
            float pos2InX = x + radiusIn * (float) Math.cos(angle2);
            float pos2InY = y + radiusIn * (float) Math.sin(angle2);

            buffer.vertex(pos1OutX, pos1OutY, z).color(r, g, b, a).endVertex();
            buffer.vertex(pos1InX, pos1InY, z).color(r, g, b, a).endVertex();
            buffer.vertex(pos2InX, pos2InY, z).color(r, g, b, a).endVertex();
            buffer.vertex(pos2OutX, pos2OutY, z).color(r, g, b, a).endVertex();
        }
    }

    private void drawCenteredString(PoseStack poseStack, Font font, int posX, int posY, List<AbstractMateriaEffectConfiguration> effectConfigurations) {
        for (AbstractMateriaEffectConfiguration configuration : effectConfigurations) {
            Component localizedDescription = Component.translatable(configuration.getDescriptionId(""));
            if (localizedDescription != null) {
                // font.drawInBatch(localizedDescription, posX, posY, 16777215, false, poseStack, buffer., false, 0, 15728880);
            }
        }
    }

    // private void renderItems(Font font, int start, int end, int posX, int posY) {
    // int xOffSet = -1;
    // for (int i = start; i < end; i++) {
    // ItemStack stack = componentData.getStack(i);
    // if (stack != null && !stack.isEmpty()) {
    // ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
    // itemRenderer.renderGuiItem(stack, posX + xOffSet, posY);
    // itemRenderer.renderGuiItemDecorations(font, stack, posX + xOffSet, posY);
    // }
    // xOffSet += 20;
    // }
    // }

    @Override
    public void onClose() {
        super.onClose();
        if (newSelectedConfiguration != materiaCollection.getSelectedConfigurationsIndex()) {
            materiaCollection.setSelectedConfigurationsIndex(newSelectedConfiguration);
            // update server
        }
    }

}
