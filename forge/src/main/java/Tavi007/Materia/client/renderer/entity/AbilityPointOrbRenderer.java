package Tavi007.Materia.client.renderer.entity;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import Tavi007.Materia.Materia;
import Tavi007.Materia.entities.AbilityPointOrb;
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
public class AbilityPointOrbRenderer extends EntityRenderer<AbilityPointOrb> {

    private static final ResourceLocation ABILITY_POINT_ORB = new ResourceLocation(Materia.MOD_ID, "textures/entity/ability_point_orb.png");
    private static final RenderType RENDER_TYPE = RenderType.itemEntityTranslucentCull(ABILITY_POINT_ORB);

    public AbilityPointOrbRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    @Override
    protected int getBlockLightLevel(AbilityPointOrb orbEntity, BlockPos blockPos) {
        return Mth.clamp(super.getBlockLightLevel(orbEntity, blockPos) + 7, 0, 15);
    }

    @Override
    public void render(AbilityPointOrb orbEntity, float p_114600_, float p_114601_, PoseStack poseStack, MultiBufferSource bufferSource, int p_114604_) {
        poseStack.pushPose();
        int i = orbEntity.getIcon();
        float f = (float) (i % 4 * 16 + 0) / 64.0F;
        float f1 = (float) (i % 4 * 16 + 16) / 64.0F;
        float f2 = (float) (i / 4 * 16 + 0) / 64.0F;
        float f3 = (float) (i / 4 * 16 + 16) / 64.0F;
        float f8 = ((float) orbEntity.tickCount + p_114601_) / 2.0F;
        int r = (int) ((Mth.sin(f8 + 0.0F) + 1.0F) * 0.5F * 255.0F);
        int g = (int) ((Mth.sin(f8 + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
        poseStack.translate(0.0F, 0.1F, 0.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.scale(0.3F, 0.3F, 0.3F);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RENDER_TYPE);
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        vertex(vertexconsumer, matrix4f, matrix3f, -0.5F, -0.25F, r, g, 255, f, f3, p_114604_);
        vertex(vertexconsumer, matrix4f, matrix3f, 0.5F, -0.25F, r, g, 255, f1, f3, p_114604_);
        vertex(vertexconsumer, matrix4f, matrix3f, 0.5F, 0.75F, r, g, 255, f1, f2, p_114604_);
        vertex(vertexconsumer, matrix4f, matrix3f, -0.5F, 0.75F, r, g, 255, f, f2, p_114604_);
        poseStack.popPose();
        super.render(orbEntity, p_114600_, p_114601_, poseStack, bufferSource, p_114604_);
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
    public ResourceLocation getTextureLocation(AbilityPointOrb orbEntity) {
        return ABILITY_POINT_ORB;
    }
}
