package ace.actually.reforested;

import ace.actually.reforested.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ReforestedDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(RModelProvider::new);
		pack.addProvider(RLootProvider::new);
		pack.addProvider(RLanguageProvider::new);
		pack.addProvider(RBlockTagProvider::new);
		pack.addProvider(RItemTagProvider::new);
		pack.addProvider(RRecipeProvider::new);
	}
}
