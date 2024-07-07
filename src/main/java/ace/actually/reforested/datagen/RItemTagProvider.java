package ace.actually.reforested.datagen;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.WoodBlockBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class RItemTagProvider extends FabricTagProvider.ItemTagProvider {


    public RItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        for(WoodBlockBuilder builder: Reforested.WOOD_BLOCKS)
        {
            this.getTagBuilder(builder.LOGS_ITEMS_TAG)
                    .add(Registries.BLOCK.getId(builder.LOG))
                    .add(Registries.BLOCK.getId(builder.STRIPPED_LOG))
                    .add(Registries.BLOCK.getId(builder.WOOD))
                    .add(Registries.BLOCK.getId(builder.STRIPPED_WOOD));

            this.getTagBuilder(ItemTags.WOODEN_FENCES).add(Registries.BLOCK.getId(builder.FENCE));
            this.getTagBuilder(ItemTags.LOGS_THAT_BURN).addTag(builder.LOGS_ITEMS_TAG.id());
            this.getTagBuilder(ItemTags.LEAVES).add(Registries.BLOCK.getId(builder.LEAVES));
            this.getTagBuilder(ItemTags.PLANKS).add(Registries.BLOCK.getId(builder.PLANKS));
            this.getTagBuilder(ItemTags.FENCE_GATES).add(Registries.BLOCK.getId(builder.FENCE_GATE));
            this.getTagBuilder(ItemTags.WOODEN_BUTTONS).add(Registries.BLOCK.getId(builder.BUTTON));
            this.getTagBuilder(ItemTags.WOODEN_DOORS).add(Registries.BLOCK.getId(builder.DOOR));
            this.getTagBuilder(ItemTags.WOODEN_SLABS).add(Registries.BLOCK.getId(builder.SLAB));
            this.getTagBuilder(ItemTags.WOODEN_STAIRS).add(Registries.BLOCK.getId(builder.STAIRS));
            this.getTagBuilder(ItemTags.SIGNS).add(Registries.BLOCK.getId(builder.SIGN));
            this.getTagBuilder(ItemTags.WOODEN_TRAPDOORS).add(Registries.BLOCK.getId(builder.TRAPDOOR));
            this.getTagBuilder(ItemTags.HANGING_SIGNS).add(Registries.BLOCK.getId(builder.HANGING_SIGN)).add(Registries.BLOCK.getId(builder.WALL_HANGING_SIGN));

        }
    }
}
