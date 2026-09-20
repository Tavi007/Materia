package Tavi007.Materia.client.renderer.entity;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import Tavi007.Materia.entities.SpellProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpellProjectileRenderer extends EntityRenderer<SpellProjectileEntity> {

    public SpellProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    @Override
    protected int getBlockLightLevel(SpellProjectileEntity spellEntity, BlockPos blockPos) {
        return Mth.clamp(super.getBlockLightLevel(spellEntity, blockPos) + 7, 0, 15);
    }

    @Override
    public void render(SpellProjectileEntity spellEntity, float p_114600_, float p_114601_, PoseStack poseStack, MultiBufferSource bufferSource,
            int lightLevel) {

        // Maybe add options of 3d models here
        renderSprite(spellEntity, poseStack, bufferSource, lightLevel);
        super.render(spellEntity, p_114600_, p_114601_, poseStack, bufferSource, lightLevel);
    }

    private void renderSprite(SpellProjectileEntity spellEntity, PoseStack poseStack, MultiBufferSource bufferSource, int lightLevel) {
        poseStack.pushPose();

        poseStack.translate(0.0F, 0.1F, 0.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.scale(0.3F, 0.3F, 0.3F);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(spellEntity)));
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();

        vertex(vertexconsumer, matrix4f, matrix3f, -0.5F, -0.25F, 255, 255, 255, 0.0f, 0.0f, lightLevel);
        vertex(vertexconsumer, matrix4f, matrix3f, 0.5F, -0.25F, 255, 255, 255, 1.0f, 0.0f, lightLevel);
        vertex(vertexconsumer, matrix4f, matrix3f, 0.5F, 0.75F, 255, 255, 255, 1.0f, 1.0f, lightLevel);
        vertex(vertexconsumer, matrix4f, matrix3f, -0.5F, 0.75F, 255, 255, 255, 0.0f, 1.0f, lightLevel);
        poseStack.popPose();

    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, float p_253952_, float p_254066_, int r,
            int g, int b, float p_254434_, float p_254223_, int p_254372_) {
        vertexConsumer.vertex(matrix4f, p_253952_, p_254066_, 0.0F)
            .color(r, g, b, 128)
            .uv(p_254434_, p_254223_)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(p_254372_)
            .normal(matrix3f, 0.0F, 1.0F, 0.0F)
            .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(SpellProjectileEntity spellEntity) {
        return spellEntity.getTexture();
    }
}
