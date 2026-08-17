package com.tyton.bactrian_brews;

import com.tyton.bactrian_brews.entity.ModEntities;
import com.tyton.bactrian_brews.entity.client.BactrianCamelModel;
import com.tyton.bactrian_brews.entity.client.BactrianCamelRenderer;
import com.tyton.bactrian_brews.entity.client.BactrianCamelSaddleModel;
import com.tyton.bactrian_brews.entity.client.ModModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class BactrianBrewsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BACTRIAN_CAMEL,
				BactrianCamelModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BACTRIAN_CAMEL_SADDLE,
				BactrianCamelSaddleModel::getTexturedModelData);
		EntityRendererRegistry.register(ModEntities.BACTRIAN_CAMEL, BactrianCamelRenderer::new);
	}
}