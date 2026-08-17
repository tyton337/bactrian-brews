package com.tyton.bactrian_brews.entity.client;

import com.tyton.bactrian_brews.entity.custom.BactrianCamelEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class BactrianCamelSaddleModel<T extends BactrianCamelEntity> extends SinglePartEntityModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;

	private final ModelPart saddle;
	private final ModelPart bridle;
	private final ModelPart reins;

	public BactrianCamelSaddleModel(ModelPart modelPart) {
		this.root = modelPart.getChild("root");
		this.body = this.root.getChild("body");
		this.head = this.body.getChild("head");

		this.saddle = this.body.getChild("saddle");
		this.bridle = this.head.getChild("bridle");
		this.reins = this.head.getChild("reins");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.5F, -20.0F, 9.5F));

		body.addChild("saddle", ModelPartBuilder.create().uv(72, 64).cuboid(-5.0F, -17.0F, -16.5F, 9.0F, 5.0F, 13.0F, new Dilation(0.1F))
				.uv(90, 112).cuboid(-4.0F, -20.0F, -16.5F, 7.0F, 3.0F, 13.0F, new Dilation(0.1F))
				.uv(12, 82).cuboid(-8.0F, -12.0F, -23.5F, 15.0F, 12.0F, 27.0F, new Dilation(0.1F))
				.uv(0, 85).cuboid(-5.0F, -17.0F, -14.4F, 9.0F, 5.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -5.0F, -19.5F));

		head.addChild("bridle", ModelPartBuilder.create().uv(69, 82).cuboid(-4.0F, -5.0F, -15.0F, 7.0F, 8.0F, 19.0F, new Dilation(0.1F))
				.uv(21, 64).cuboid(-4.0F, -19.0F, -15.0F, 7.0F, 14.0F, 7.0F, new Dilation(0.1F))
				.uv(50, 64).cuboid(-3.0F, -19.0F, -21.1F, 5.0F, 5.0F, 6.0F, new Dilation(0.1F))
				.uv(74, 70).cuboid(2.0F, -17.0F, -18.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(74, 70).mirrored().cuboid(-4.0F, -17.0F, -18.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		head.addChild("reins", ModelPartBuilder.create()
						// Right Rein Front
						.uv(104, 52).cuboid(-7.401F, 0.0F, 0.0F, 0.0F, 7.0F, 12.0F, new Dilation(0.0F))

						// Left Rein Front
						.uv(104, 52).cuboid(0.0F, 0.0F, 0.0F, 0.0F, 7.0F, 12.0F, new Dilation(0.0F))

						// Left Rein Back
						.uv(106, 47).cuboid(0.0F, 0.0F, 12.0F, 1.0F, 7.0F, 10.0F, new Dilation(0.0F))

						// Right Rein Back
						.uv(106, 47).mirrored().cuboid(-8.401F, 0.0F, 12.0F, 1.0F, 7.0F, 10.0F, new Dilation(0.0F)).mirrored(false),
				ModelTransform.pivot(3.2F, -16.0F, -17.0F));

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

    float currentTicks = (float) entity.getLastPoseTickDelta();
		float tickDelta = net.minecraft.client.MinecraftClient.getInstance().getTickDelta();

		float interpolatedTicks = MathHelper.clamp(currentTicks + tickDelta, 0.0F, 14.0F);
		float transitionProgress = interpolatedTicks / 14.0F;

		float sitProgress = entity.isSitting() ? transitionProgress : 1.0F - transitionProgress;
		this.body.pivotY = MathHelper.lerp(sitProgress, -20.0F, -12.0F);
	}

	public ModelPart getReins() { return this.reins; }
}
