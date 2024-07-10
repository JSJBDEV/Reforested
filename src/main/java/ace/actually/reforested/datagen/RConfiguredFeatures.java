package ace.actually.reforested.datagen;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.WoodBlockBuilder;
import net.minecraft.registry.Registerable;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class RConfiguredFeatures {



    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context)
    {
        for(WoodBlockBuilder builder: Reforested.WOOD_BLOCKS)
        {
            context.register(builder.AS_CONFIGURED_FEATURE, like(builder));
        }

    }

    public static ConfiguredFeature like(WoodBlockBuilder builder)
    {
        switch (builder.LIKE_TREE)
        {
            case "spruce" ->
            {
                return new ConfiguredFeature(Feature.TREE,
                        new TreeFeatureConfig.Builder(
                                BlockStateProvider.of(builder.LOG),
                                new StraightTrunkPlacer(5, 2, 1),
                                BlockStateProvider.of(builder.LEAVES),
                                new SpruceFoliagePlacer(UniformIntProvider.create(2, 3), UniformIntProvider.create(0, 2), UniformIntProvider.create(1, 2)),
                                new TwoLayersFeatureSize(2, 0, 2)
                        )
                                .ignoreVines()
                                .build());
            }
        }
        return null;
    }
}
