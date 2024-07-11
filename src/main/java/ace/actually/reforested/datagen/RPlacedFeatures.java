package ace.actually.reforested.datagen;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.wood_builders.WoodBlockBuilder;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.*;

public class RPlacedFeatures {

    public static void bootstrap(Registerable<PlacedFeature> context)
    {
        for(WoodBlockBuilder builder: Reforested.WOOD_BLOCKS)
        {
            RegistryEntryLookup<ConfiguredFeature<?,?>> configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
            context.register(builder.AS_PLACED_FEATURE,new PlacedFeature(
                    configuredFeatures.getOrThrow(builder.AS_CONFIGURED_FEATURE),
                    VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.02f, 1),builder.SAPLING)
            ));

        }

    }
}
