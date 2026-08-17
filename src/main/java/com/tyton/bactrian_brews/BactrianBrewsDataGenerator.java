package com.tyton.bactrian_brews;

import com.tyton.bactrian_brews.datagen.ModAdvancementProvider;
import com.tyton.bactrian_brews.datagen.ModModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BactrianBrewsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModAdvancementProvider::new);
		pack.addProvider(ModModelProvider::new);
	}
}
