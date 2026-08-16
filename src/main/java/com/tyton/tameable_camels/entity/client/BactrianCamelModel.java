package com.tyton.tameable_camels.entity.client;

import com.tyton.tameable_camels.entity.custom.BactrianCamelEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class BactrianCamelModel<T extends BactrianCamelEntity> extends SinglePartEntityModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private static final float BABY_SCALE = 0.5F;

	// Limb trackers for standard continuous leg animations
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;

	public BactrianCamelModel(ModelPart modelPart) {
		this.root = modelPart.getChild("root");

		this.body = this.root.getChild("body");
		this.head = this.body.getChild("head");

		this.rightFrontLeg = this.root.getChild("right_front_leg");
		this.leftFrontLeg = this.root.getChild("left_front_leg");
		this.rightHindLeg = this.root.getChild("right_hind_leg");
		this.leftHindLeg = this.root.getChild("left_hind_leg");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 25).cuboid(-8.0F, -12.0F, -23.5F, 15.0F, 12.0F, 27.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, -20.0F, 9.5F));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(-0.5F, -9.0F, 3.5F));

		tail.addChild("tail_r1", ModelPartBuilder.create().uv(122, 0).cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(60, 24).cuboid(-4.0F, -5.0F, -15.0F, 7.0F, 8.0F, 19.0F, new Dilation(0.0F))
				.uv(21, 0).cuboid(-4.0F, -19.0F, -15.0F, 7.0F, 14.0F, 7.0F, new Dilation(0.0F))
				.uv(50, 0).cuboid(-3.0F, -19.0F, -21.0F, 5.0F, 5.0F, 6.0F, new Dilation(0.0F))
				.uv(28, 11).cuboid(-3.0F, -14.0F, -16.0F, 5.0F, 9.0F, 1.0F, new Dilation(0.0F))
				.uv(29, 11).cuboid(-2.0F, -14.0F, -17.0F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F))
				.uv(29, 11).cuboid(-2.0F, -14.0F, -18.0F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.0F, -19.5F));

		head.addChild("left_ear", ModelPartBuilder.create().uv(45, 0).cuboid(0.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, -18.0F, -9.5F));

		head.addChild("right_ear", ModelPartBuilder.create().uv(67, 0).cuboid(-3.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, -18.0F, -9.5F));

		body.addChild("hump", ModelPartBuilder.create().uv(77, 3).cuboid(-5.0F, -5.0F, 4.0F, 9.0F, 5.0F, 8.0F, new Dilation(0.0F))
				.uv(77, 3).cuboid(-5.0F, -5.0F, -13.0F, 9.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, -9.5F));

		root.addChild("right_front_leg", ModelPartBuilder.create().uv(0, 26).cuboid(-2.5F, 2.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.9F, -23.0F, -10.5F));

		root.addChild("left_front_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, 2.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(4.9F, -23.0F, -10.5F));

		root.addChild("left_hind_leg", ModelPartBuilder.create().uv(58, 16).cuboid(-2.5F, 2.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(4.9F, -23.0F, 9.5F));

		root.addChild("right_hind_leg", ModelPartBuilder.create().uv(94, 16).cuboid(-2.5F, 2.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.9F, -23.0F, 9.5F));

		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		this.head.pitch = headPitch * ((float)Math.PI / 180F);
		this.head.yaw = headYaw * ((float)Math.PI / 180F);
		this.rightFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
		this.leftFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + (float)Math.PI) * 1.4F * limbDistance;
		this.rightHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + (float)Math.PI) * 1.4F * limbDistance;
		this.leftHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;

    float currentTicks = (float) entity.getLastPoseTickDelta();
		float tickDelta = net.minecraft.client.MinecraftClient.getInstance().getTickDelta();
		float interpolatedTicks = MathHelper.clamp(currentTicks + tickDelta, 0.0F, 14.0F);
		float transitionProgress = interpolatedTicks / 14.0F;
		float sitProgress = entity.isSitting() ? transitionProgress : 1.0F - transitionProgress;

		this.body.pivotY = MathHelper.lerp(sitProgress, -20.0F, -12.0F);

		float legPivotY = MathHelper.lerp(sitProgress, -23.0F, -15.0F);
		this.rightFrontLeg.pivotY = legPivotY;
		this.leftFrontLeg.pivotY = legPivotY;
		this.rightHindLeg.pivotY = legPivotY;
		this.leftHindLeg.pivotY = legPivotY;

		this.rightFrontLeg.pitch += MathHelper.lerp(sitProgress, 0.0F, -1.2F);
		this.leftFrontLeg.pitch += MathHelper.lerp(sitProgress, 0.0F, -1.2F);
		this.rightHindLeg.pitch += MathHelper.lerp(sitProgress, 0.0F, 1.2F);
		this.leftHindLeg.pitch += MathHelper.lerp(sitProgress, 0.0F, 1.2F);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		if (this.child) {
			matrices.push();
			matrices.scale(BABY_SCALE, BABY_SCALE, BABY_SCALE);
			matrices.translate(0.0, 1.5, 0.0);
			this.getPart().render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
			matrices.pop();
		} else {
			this.getPart().render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		}
	}
}
