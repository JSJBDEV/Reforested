package ace.actually.reforested.datagen;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.bees.BeeLookups;
import ace.actually.reforested.trees.blocks.wood_builders.WoodBlockBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;

import java.sql.Ref;
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

            String caps = Reforested.toTranslation(builder.woodName);
            translationBuilder.add("entity.reforested."+builder.woodName+"_chest_boat.name",caps+" Boat With Chest");
            translationBuilder.add("item.reforested."+builder.woodName+"_boat",caps+" Boat");
            translationBuilder.add("item.reforested."+builder.woodName+"_chest_boat",caps+" Boat With Chest");
            translationBuilder.add(builder.LOGS_BLOCKS_TAG,caps+" Logs");
            translationBuilder.add(builder.LOGS_ITEMS_TAG,caps+" Logs");

        }

        translationBuilder.add("tag.item.c.honeycombs","Honeycombs");
        translationBuilder.add("itemgroup.reforested.trees","Reforested: Arboriculture");
        translationBuilder.add("itemgroup.reforested.bees","Reforested: Apiculture");
        translationBuilder.add("itemgroup.reforested.industry","Reforested: Industria");
        translationBuilder.add(Reforested.CENTRIFUGE_BLOCK,"Centrifuge");
        translationBuilder.add(Reforested.PEAT_ENGINE_BLOCK,"Peat Engine");
        translationBuilder.add(Reforested.PROPOLIS,"Propolis");
        translationBuilder.add(Reforested.PEAT,"Peat");
        translationBuilder.add(Reforested.FIBROUS_COMB,"Fibrous Comb");
        translationBuilder.add(Reforested.PEATY_COMB,"Peaty Comb");
        translationBuilder.add(Reforested.STONEY_COMB,"Stoney Comb");
        translationBuilder.add(Reforested.COLD_COMB,"Cold Comb");
        translationBuilder.add(Reforested.HOT_COMB,"Hot Comb");
        translationBuilder.add(Reforested.APIARY_BLOCK,"Apiary");
        translationBuilder.add(Reforested.BEE_ANALYSER_ITEM,"Bee Analyser");
        translationBuilder.add(Reforested.BOG_BLOCK,"Block o' Bog");
        translationBuilder.add(Reforested.ROYAL_JELLY,"Royal Jelly");
        translationBuilder.add(Reforested.PISTACHIO_NUT,"Pistachio Nut");
        translationBuilder.add(Reforested.PLUM,"Plum");
        translationBuilder.add(Reforested.TREE_CANE_BLOCK,"Tree Breeding Canes");

        for(String bee: BeeLookups.BEE_TYPES)
        {
            translationBuilder.add("bee.reforested."+bee,Reforested.toTranslation(bee));
        }
        for(String beeFamily: BeeLookups.allFamilies())
        {
            translationBuilder.add("family.bee.reforested."+beeFamily,Reforested.toTranslation(beeFamily));
        }

        translationBuilder.add("thing.reforested.bee","Bee");


        translationBuilder.add("text.reforested.typeof","This is a ");
        translationBuilder.add("text.reforested.bee.temperature","Preferred Temperature: ");
        translationBuilder.add("text.reforested.bee.humidity","Likes Humidity: ");
        translationBuilder.add("text.reforested.bee.natural","Is Natural: ");


    }
}
