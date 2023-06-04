package Tavi007.Materia.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.recipes.effects.configuration.AbstractMateriaEffectConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class SelectMateriaEffectScreen extends Screen {

    // open/close animation
    private final float ANIMATION_LENGTH = 0.40f;
    private static final float PRECISION = 0.1f;
    private float totalTime;
    private float prevTick;
    private float extraTick;
    private boolean opening;

    private MateriaToolTooltipComponent materiaToolComponent;

    private int newSelectedConfiguration;

    public SelectMateriaEffectScreen(MateriaToolTooltipComponent materiaToolComponent) {
        super(Component.literal(""));
        this.minecraft = Minecraft.getInstance();
        this.materiaToolComponent = materiaToolComponent;
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
        tickForLogging++;
    }

    int tickForLogging = 0;

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);
        // new midpoint of coordinate system is center of screen
        int centerOfScreenX = width / 2;
        int centerOfScreenY = height / 2;
        double posX = mouseX - centerOfScreenX;
        double posY = mouseY - centerOfScreenY;

        // polar coordinate with (r=1, phi=0) <=> (x=0, y= -1) and phi running clockwise
        double rSquared = posX * posX + posY * posY;
        double phi = Math.atan2(-posX, posY) + Math.PI;

        if (tickForLogging % 25 == 0) {
            Materia.LOGGER.info("phi=" + phi);
            tickForLogging = 1;
        }

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
        Map<List<ItemStack>, List<AbstractMateriaEffectConfiguration>> stacksToConfigurationMapping = materiaToolComponent
            .getMateriaCollection()
            .getItemstacksToEffectConfigurationMapping();
        int numberOfOptions = Math.min(MateriaCollectionHandler.MAX_ITEM_SLOTS, stacksToConfigurationMapping.size());

        // compute slices
        List<SelectSlice> slices = new ArrayList<>();
        if (opening) {
            newSelectedConfiguration = -1;

            for (int i = 0; i < numberOfOptions; i++) {
                float sliceBorderLeft = getBorder(i, numberOfOptions, true);
                float sliceBorderRight = getBorder(i, numberOfOptions, false);
                boolean isSelected = false;
                if (rSquared >= radiusIn * radiusIn && rSquared < radiusOut * radiusOut) {
                    if (i == 0 && (phi < sliceBorderRight || sliceBorderLeft + (Math.PI * 2) <= phi)) {
                        newSelectedConfiguration = i;
                        isSelected = true;
                    } else if (sliceBorderLeft <= phi && phi < sliceBorderRight) {
                        newSelectedConfiguration = i;
                        isSelected = true;
                    }
                }
                slices.add(new SelectSlice(sliceBorderLeft, sliceBorderRight, radiusIn, radiusOut, isSelected));
            }
        }

        // draw select ring
        drawSlices(poseStack, slices, centerOfScreenX, centerOfScreenY);

        poseStack.popPose();
    }

    private float getBorder(int index, int numberOfOptions, boolean isLeft) {
        float indexHalf = isLeft ? (float) (index - 0.5f) : (float) (index + 0.5f);
        return (float) (indexHalf * Math.PI * 2 / numberOfOptions);
    }

    private void drawSlices(PoseStack poseStack, List<SelectSlice> slices, int posX, int posY) {
        poseStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        for (SelectSlice slice : slices) {
            slice.renderBackground(buffer, posX, posY);
        }
        tessellator.end();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();

    }

    @Override
    public void onClose() {
        super.onClose();
        if (newSelectedConfiguration != materiaToolComponent.getMateriaCollection().getSelectedConfigurationsIndex()) {
            materiaToolComponent.getMateriaCollection().setSelectedConfigurationsIndex(newSelectedConfiguration);
            // update server
        }
    }

    private class SelectSlice {

        private float startAngle;
        private float endAngle;
        private float radiusIn;
        private float radiusOut;
        private boolean isSelected;

        private SelectSlice(float startAngle, float endAngle, float radiusIn, float radiusOut, boolean isSelected) {
            this.startAngle = startAngle;
            this.endAngle = endAngle;
            this.radiusIn = radiusIn;
            this.radiusOut = radiusOut;
            this.isSelected = isSelected;
        }

        protected void renderBackground(BufferBuilder buffer, int x, int y) {
            float angle = endAngle - startAngle;
            int sections = Math.max(1, Mth.ceil(angle / PRECISION));

            int r = isSelected ? 63 : 0;
            int g = isSelected ? 161 : 0;
            int b = isSelected ? 191 : 0;
            int a = isSelected ? 60 : 70;
            for (int i = 0; i < sections; i++) {
                float angle1 = startAngle + (i / (float) sections) * angle;
                float angle2 = startAngle + ((i + 1) / (float) sections) * angle;

                // again using polar coordinate with (r=1, phi=0) <=> (x=0, y=1) and phi running clockwise
                float pos1InX = x + radiusIn * (float) Math.sin(angle1);
                float pos1InY = y - radiusIn * (float) Math.cos(angle1);
                float pos1OutX = x + radiusOut * (float) Math.sin(angle1);
                float pos1OutY = y - radiusOut * (float) Math.cos(angle1);
                float pos2OutX = x + radiusOut * (float) Math.sin(angle2);
                float pos2OutY = y - radiusOut * (float) Math.cos(angle2);
                float pos2InX = x + radiusIn * (float) Math.sin(angle2);
                float pos2InY = y - radiusIn * (float) Math.cos(angle2);

                buffer.vertex(pos1OutX, pos1OutY, 10).color(r, g, b, a).endVertex();
                buffer.vertex(pos1InX, pos1InY, 10).color(r, g, b, a).endVertex();
                buffer.vertex(pos2InX, pos2InY, 10).color(r, g, b, a).endVertex();
                buffer.vertex(pos2OutX, pos2OutY, 10).color(r, g, b, a).endVertex();

            }
        }

        protected void renderImage() {

        }
    }

}
