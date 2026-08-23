package com.tyton.bactrian_brews;

import com.tyton.bactrian_brews.entity.ModEntities;
import com.tyton.bactrian_brews.entity.client.*;
import com.tyton.bactrian_brews.screen.custom.BactrianCamelScreen;
import com.tyton.bactrian_brews.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class BactrianBrewsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BACTRIAN_CAMEL,
				BactrianCamelModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BACTRIAN_CAMEL_SADDLE,
				BactrianCamelSaddleModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BACTRIAN_CAMEL_CHEST,
				BactrianCamelChestModel::getTexturedModelData);
		EntityRendererRegistry.register(ModEntities.BACTRIAN_CAMEL, BactrianCamelRenderer::new);
		HandledScreens.register(ModScreenHandlers.BACTRIAN_CAMEL_SCREEN_HANDLER, BactrianCamelScreen::new);
	}
}