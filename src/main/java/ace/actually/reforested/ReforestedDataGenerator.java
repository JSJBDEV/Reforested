package ace.actually.reforested;

import ace.actually.reforested.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class ReforestedDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(RModelProvider::new);
		pack.addProvider(RLootProvider::new);
		pack.addProvider(RLanguageProvider::new);
		pack.addProvider(RRecipeProvider::new);
		pack.addProvider(RBlockTagProvider::new);
		pack.addProvider(RItemTagProvider::new);
		pack.addProvider(RWorldGenProvider::new);
		RVibeBasedTexturer.make();
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, RConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE,RPlacedFeatures::bootstrap);
		DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);

	}
}
