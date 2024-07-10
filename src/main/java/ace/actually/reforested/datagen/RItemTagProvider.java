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
                    .add(Registries.ITEM.getId(builder.LOG.asItem()))
                    .add(Registries.ITEM.getId(builder.STRIPPED_LOG.asItem()))
                    .add(Registries.ITEM.getId(builder.WOOD.asItem()))
                    .add(Registries.ITEM.getId(builder.STRIPPED_WOOD.asItem()));

            this.getTagBuilder(ItemTags.WOODEN_FENCES).add(Registries.ITEM.getId(builder.FENCE.asItem()));
            this.getTagBuilder(ItemTags.LOGS_THAT_BURN).addTag(builder.LOGS_ITEMS_TAG.id());
            this.getTagBuilder(ItemTags.LEAVES).add(Registries.ITEM.getId(builder.LEAVES.asItem()));
            this.getTagBuilder(ItemTags.PLANKS).add(Registries.ITEM.getId(builder.PLANKS.asItem()));
            this.getTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(Registries.ITEM.getId(builder.PRESSURE_PLATE.asItem()));
            this.getTagBuilder(ItemTags.FENCE_GATES).add(Registries.ITEM.getId(builder.FENCE_GATE.asItem()));
            this.getTagBuilder(ItemTags.WOODEN_BUTTONS).add(Registries.ITEM.getId(builder.BUTTON.asItem()));
            this.getTagBuilder(ItemTags.WOODEN_DOORS).add(Registries.ITEM.getId(builder.DOOR.asItem()));
            this.getTagBuilder(ItemTags.WOODEN_SLABS).add(Registries.ITEM.getId(builder.SLAB.asItem()));
            this.getTagBuilder(ItemTags.WOODEN_STAIRS).add(Registries.ITEM.getId(builder.STAIRS.asItem()));
            this.getTagBuilder(ItemTags.SIGNS).add(Registries.ITEM.getId(builder.SIGN_ITEM.asItem()));
            this.getTagBuilder(ItemTags.WOODEN_TRAPDOORS).add(Registries.ITEM.getId(builder.TRAPDOOR.asItem()));
            this.getTagBuilder(ItemTags.HANGING_SIGNS).add(Registries.ITEM.getId(builder.HANGING_SIGN_ITEM));

        }
    }
}
