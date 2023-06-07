package Tavi007.Materia.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import Tavi007.Materia.capabilities.materia.collection.handler.CollectionToEffectRecipeMapper;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.client.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
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

    private MateriaToolComponent materiaToolComponent;

    private int newSelectedConfiguration;

    public SelectMateriaEffectScreen(MateriaToolComponent materiaToolComponent) {
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
    public void onClose() {
        super.onClose();
        if (newSelectedConfiguration != materiaToolComponent.getMateriaCollection().getSelectedConfigurationsIndex()) {
            materiaToolComponent.getMateriaCollection().setSelectedConfigurationsIndex(newSelectedConfiguration);
        }
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
        float posX = mouseX - centerOfScreenX;
        float posY = mouseY - centerOfScreenY;

        // polar coordinate with (r=1, phi=0) <=> (x=0, y= -1) and phi running clockwise
        double rSquared = posX * posX + posY * posY;
        double phi = Math.atan2(-posX, posY) + Math.PI;

        // animation progress
        float openAnimation = opening ? totalTime / ANIMATION_LENGTH : 1.0f - totalTime / ANIMATION_LENGTH;
        float currTick = minecraft.getFrameTime();
        totalTime += (currTick + extraTick - prevTick) / 20f;
        extraTick = 0;
        prevTick = currTick;
        float animationProgress = Mth.clamp(openAnimation, 0, 1);
        animationProgress = (float) (1 - Math.pow(1 - animationProgress, 3));

        // radius definitions
        float radiusIn = Math.max(0.1f, 45);
        float radiusOut = radiusIn * (2 * animationProgress);

        // divide selection ring into slices
        List<CollectionToEffectRecipeMapper> collectionToEffectMapper = materiaToolComponent
            .getMateriaCollection()
            .getCollectionToEffectRecipeMapper();
        int numberOfOptions = Math.min(MateriaCollectionHandler.MAX_ITEM_SLOTS, collectionToEffectMapper.size());

        // compute slices
        List<SelectSlice> slices = new ArrayList<>();
        if (opening) {
            newSelectedConfiguration = -1;

            int i = 0;
            for (CollectionToEffectRecipeMapper mapping : collectionToEffectMapper) {
                float angle = (float) Math.PI * 2 / numberOfOptions;
                float sliceBorderLeft = (i - 0.5f) * angle;
                float sliceBorderRight = sliceBorderLeft + angle;

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

                List<ItemStack> stacks = new ArrayList<>();
                mapping.getSlotIndexList().forEach(slotIndex -> stacks.add(materiaToolComponent.getMateriaCollection().getStackInSlot(slotIndex)));
                slices.add(new SelectSlice(sliceBorderLeft, sliceBorderRight, radiusIn, radiusOut, isSelected, stacks));
                i++;
            }
        }

        // draw select ring
        drawSlices(poseStack, slices, centerOfScreenX, centerOfScreenY, animationProgress > 0.99f);

        // draw description of selected effect in the middle
        MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        List<Pair<String, Integer>> descriptionsWithColor = materiaToolComponent.getEffectDescriptions(newSelectedConfiguration);
        int yOffSet = -Math.round(((float) descriptionsWithColor.size()) / 2 * font.lineHeight);
        int xOffSet = -materiaToolComponent.getWidth() / 2;
        for (Pair<String, Integer> pair : descriptionsWithColor) {
            Component localizedDescription = Component.translatable(pair.getLeft());

            if (localizedDescription != null) {
                font.drawInBatch(localizedDescription,
                    centerOfScreenX + xOffSet,
                    centerOfScreenY + yOffSet,
                    pair.getRight(),
                    false,
                    poseStack.last().pose(),
                    bufferSource,
                    false,
                    0,
                    15728880);
                yOffSet += font.lineHeight;
            }
        }
        bufferSource.endBatch();

    }

    private void drawSlices(PoseStack poseStack, List<SelectSlice> slices, int posX, int posY, boolean animationFinished) {
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

        if (animationFinished) {
            for (SelectSlice slice : slices) {
                slice.renderImage(poseStack, itemRenderer, posX, posY);
            }
        }
    }

    private class SelectSlice {

        private float startAngle;
        private float endAngle;
        private float radiusIn;
        private float radiusOut;
        private boolean isSelected;

        private List<ItemStack> stacks;
        private float radiusStacks;

        private SelectSlice(float startAngle, float endAngle, float radiusIn, float radiusOut, boolean isSelected, List<ItemStack> stacks) {
            this.startAngle = startAngle;
            this.endAngle = endAngle;
            this.radiusIn = radiusIn;
            this.radiusOut = isSelected ? radiusOut * 1.15f : radiusOut;
            this.isSelected = isSelected;
            this.stacks = stacks;
            this.radiusStacks = (radiusIn + radiusOut) * 0.5f;
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
                float pos1InX = x + (radiusIn * (float) Math.sin(angle1));
                float pos1InY = y - (radiusIn * (float) Math.cos(angle1));
                float pos1OutX = x + (radiusOut * (float) Math.sin(angle1));
                float pos1OutY = y - (radiusOut * (float) Math.cos(angle1));
                float pos2OutX = x + (radiusOut * (float) Math.sin(angle2));
                float pos2OutY = y - (radiusOut * (float) Math.cos(angle2));
                float pos2InX = x + (radiusIn * (float) Math.sin(angle2));
                float pos2InY = y - (radiusIn * (float) Math.cos(angle2));

                buffer.vertex(pos1OutX, pos1OutY, 10).color(r, g, b, a).endVertex();
                buffer.vertex(pos1InX, pos1InY, 10).color(r, g, b, a).endVertex();
                buffer.vertex(pos2InX, pos2InY, 10).color(r, g, b, a).endVertex();
                buffer.vertex(pos2OutX, pos2OutY, 10).color(r, g, b, a).endVertex();
            }
        }

        protected void renderImage(PoseStack poseStack, ItemRenderer itemRenderer, int x, int y) {
            float angle = startAngle + ((endAngle - startAngle) / 2);
            int posX = Math.round(x + (radiusStacks * (float) Math.sin(angle)) - (stacks.size() * 10));
            int posY = Math.round(y - (radiusStacks * (float) Math.cos(angle)) - 10);

            RenderUtil.drawMateriaCollectionSlots(poseStack, posX, posY, Arrays.asList(stacks.size()));

            int xOffSet = -1;
            for (ItemStack stack : stacks) {
                if (stack != null && !stack.isEmpty()) {
                    itemRenderer.renderGuiItem(stack, posX + xOffSet, posY);
                    itemRenderer.renderGuiItemDecorations(font, stack, posX + xOffSet, posY);
                }
                xOffSet += 20;
            }
        }
    }

}