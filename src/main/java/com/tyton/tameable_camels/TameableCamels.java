package com.tyton.tameable_camels;

import com.tyton.tameable_camels.advancement.ModCriteria;
import com.tyton.tameable_camels.entity.ModEntities;
import com.tyton.tameable_camels.entity.custom.BactrianCamelEntity;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TameableCamels implements ModInitializer {
	public static final String MOD_ID = "tameable_camels";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		FabricDefaultAttributeRegistry.register(ModEntities.BACTRIAN_CAMEL,
				BactrianCamelEntity.createCamelAttributes());
		ModCriteria.register();
	}
}