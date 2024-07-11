package ace.actually.reforested.datagen;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.wood_builders.WoodBlockBuilder;
import net.minecraft.registry.Registerable;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.OptionalInt;

public class RConfiguredFeatures {




    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context)
    {
        for(WoodBlockBuilder builder: Reforested.WOOD_BLOCKS)
        {
            context.register(builder.AS_CONFIGURED_FEATURE, like(builder));
        }

    }

    /**
     * the idea of this method is to give the builder options for what the tree looks like
     * @param builder the wood type
     * @return the feature built with the correct names, or null
     */
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
            case "oak" ->
            {
                return new ConfiguredFeature(Feature.TREE,
                        new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(builder.LOG),
                        new LargeOakTrunkPlacer(3, 11, 0),
                        BlockStateProvider.of(builder.LEAVES),
                        new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
                )
                        .ignoreVines()
                        .build());

            }
        }
        return null;
    }
}
