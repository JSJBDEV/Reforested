package ace.actually.reforested.datagen;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.WoodBlockBuilder;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;

public class RPlacedFeatures {

    public static void bootstrap(Registerable<PlacedFeature> context)
    {
        for(WoodBlockBuilder builder: Reforested.WOOD_BLOCKS)
        {
            RegistryEntryLookup<ConfiguredFeature<?,?>> configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
            context.register(builder.AS_PLACED_FEATURE,new PlacedFeature(
                    configuredFeatures.getOrThrow(builder.AS_CONFIGURED_FEATURE),
                    VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(3, 0.1f, 2),Reforested.WOOD_BLOCKS.get(0).SAPLING)
            ));
        }

    }
}
