package ace.actually.reforested.datagen;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.WoodBlockBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RLanguageProvider extends FabricLanguageProvider {
    public RLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        for(WoodBlockBuilder builder: Reforested.WOOD_BLOCKS)
        {
            Map<Block,String> t = builder.produceBlockTranslations();
            for(Block block: t.keySet())
            {
                translationBuilder.add(block,t.get(block));
            }
            translationBuilder.add("itemgroup.reforested","Reforested");
        }
    }
}
