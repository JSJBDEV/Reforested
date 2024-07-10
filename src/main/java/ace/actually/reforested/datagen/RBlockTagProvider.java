package ace.actually.reforested.datagen;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.WoodBlockBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class RBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public RBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        for(WoodBlockBuilder builder: Reforested.WOOD_BLOCKS)
        {
            this.getTagBuilder(builder.LOGS_BLOCKS_TAG)
                    .add(Registries.BLOCK.getId(builder.LOG))
                    .add(Registries.BLOCK.getId(builder.STRIPPED_LOG))
                    .add(Registries.BLOCK.getId(builder.WOOD))
                    .add(Registries.BLOCK.getId(builder.STRIPPED_WOOD));

            this.getTagBuilder(BlockTags.WOODEN_FENCES).add(Registries.BLOCK.getId(builder.FENCE));
            this.getTagBuilder(BlockTags.FENCE_GATES).add(Registries.BLOCK.getId(builder.FENCE_GATE));

            this.getTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(builder.LOGS_BLOCKS_TAG.id());
            this.getTagBuilder(BlockTags.OVERWORLD_NATURAL_LOGS).addTag(builder.LOGS_BLOCKS_TAG.id());

            this.getTagBuilder(BlockTags.LEAVES).add(Registries.BLOCK.getId(builder.LEAVES));
            this.getTagBuilder(BlockTags.PLANKS).add(Registries.BLOCK.getId(builder.PLANKS));

            this.getTagBuilder(BlockTags.WOODEN_BUTTONS).add(Registries.BLOCK.getId(builder.BUTTON));
            this.getTagBuilder(BlockTags.WOODEN_DOORS).add(Registries.BLOCK.getId(builder.DOOR));
            this.getTagBuilder(BlockTags.WOODEN_SLABS).add(Registries.BLOCK.getId(builder.SLAB));
            this.getTagBuilder(BlockTags.WOODEN_STAIRS).add(Registries.BLOCK.getId(builder.STAIRS));

            this.getTagBuilder(BlockTags.STANDING_SIGNS).add(Registries.BLOCK.getId(builder.SIGN));
            this.getTagBuilder(BlockTags.WALL_SIGNS).add(Registries.BLOCK.getId(builder.WALL_SIGN));
            this.getTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(Registries.BLOCK.getId(builder.WALL_HANGING_SIGN));
            this.getTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(Registries.BLOCK.getId(builder.HANGING_SIGN));

            this.getTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(Registries.BLOCK.getId(builder.TRAPDOOR));
        }
    }
}
