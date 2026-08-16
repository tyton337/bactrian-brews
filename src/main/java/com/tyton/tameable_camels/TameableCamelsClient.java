package com.tyton.tameable_camels;

import com.tyton.tameable_camels.entity.ModEntities;
import com.tyton.tameable_camels.entity.client.BactrianCamelModel;
import com.tyton.tameable_camels.entity.client.BactrianCamelRenderer;
import com.tyton.tameable_camels.entity.client.BactrianCamelSaddleModel;
import com.tyton.tameable_camels.entity.client.ModModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class TameableCamelsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BACTRIAN_CAMEL,
				BactrianCamelModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BACTRIAN_CAMEL_SADDLE,
				BactrianCamelSaddleModel::getTexturedModelData);
		EntityRendererRegistry.register(ModEntities.BACTRIAN_CAMEL, BactrianCamelRenderer::new);
	}
}