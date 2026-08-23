package com.tyton.bactrian_brews;

import com.tyton.bactrian_brews.advancement.ModCriteria;
import com.tyton.bactrian_brews.entity.ModEntities;
import com.tyton.bactrian_brews.entity.ModEntitySpawns;
import com.tyton.bactrian_brews.entity.custom.BactrianCamelEntity;
import com.tyton.bactrian_brews.item.ModItems;
import com.tyton.bactrian_brews.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BactrianBrews implements ModInitializer {
	public static final String MOD_ID = "bactrian_brews";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		FabricDefaultAttributeRegistry.register(ModEntities.BACTRIAN_CAMEL,
				BactrianCamelEntity.createCamelAttributes());
		ModCriteria.register();
		ModItems.register();
		ModEntitySpawns.register();
		ModScreenHandlers.register();
	}
}