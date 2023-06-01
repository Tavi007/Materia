package Tavi007.Materia.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class SelectMateriaEffectScreen extends Screen {

    // open/close animation
    private final float ANIMATION_LENGTH = 0.40f;
    private float totalTime;
    private float prevTick;
    private float extraTick;
    private boolean opening;

    MateriaCollectionHandler materiaCollection;

    protected SelectMateriaEffectScreen(MateriaCollectionHandler materiaCollection) {
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
        int centerOfScreenX = width / 2;
        int centerOfScreenY = height / 2;

        float openAnimation = opening ? totalTime / ANIMATION_LENGTH : 1.0f - totalTime / ANIMATION_LENGTH;
        float currTick = minecraft.getFrameTime();
        totalTime += (currTick + extraTick - prevTick) / 20f;
        extraTick = 0;
        prevTick = currTick;

        float animationProgress = Mth.clamp(openAnimation, 0, 1);
        animationProgress = (float) (1 - Math.pow(1 - animationProgress, 3));

        // TODO: selectable effects going from midpoint to maxRange radial?

    }

    public void onClose() {
        super.onClose();

        // TODO: compare mouse and effects position, update materiaCollection accordingly
        // -> need method that maps position to effect
        // -> need networking with server for updating
    }

}
