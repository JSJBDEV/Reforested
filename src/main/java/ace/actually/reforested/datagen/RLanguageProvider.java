package ace.actually.reforested.datagen;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.WoodBlockBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.List;
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
            translationBuilder.add(Reforested.CENTRIFUGE_BLOCK,"Centrifuge");
            translationBuilder.add(Reforested.PROPOLIS,"Propolis");
            translationBuilder.add(Reforested.FIBROUS_COMB,"Fibrous Comb");
            translationBuilder.add(Reforested.APIARY_BLOCK,"Apiary");
            translationBuilder.add(Reforested.BEE_ANALYSER_ITEM,"Bee Analyser");

            translationBuilder.add("text.reforested.typeof","This is a ");
            translationBuilder.add("thing.reforested.bee","Bee");

            translationBuilder.add("bee.reforested.busy","Busy");
            translationBuilder.add("bee.reforested.civil","Civil");
            translationBuilder.add("bee.reforested.employed","Employed");
            translationBuilder.add("bee.reforested.honey","Honey");
            translationBuilder.add("bee.reforested.candied","Candied");
            translationBuilder.add("bee.reforested.saccharine","Saccharine");
            translationBuilder.add("bee.reforested.lanky","Lanky");
            translationBuilder.add("bee.reforested.stringy","Stringy");
            translationBuilder.add("bee.reforested.affable","Affable");

            translationBuilder.add("tag.item.reforested.larch_logs","Larch Logs");
        }
    }
}
