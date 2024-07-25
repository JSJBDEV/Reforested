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
        translationBuilder.add(Reforested.BASIC_COMPARTMENT_BLOCK,"Basic Compartment");
        translationBuilder.add(Reforested.INT_COMPARTMENT_BLOCK,"Intermediate Compartment");
        translationBuilder.add(Reforested.MULTI_FARM_BLOCK,"Multi-Farm");
        translationBuilder.add(Reforested.CIRCUIT_BOARD,"Circuit Board");
        translationBuilder.add(Reforested.SOLDERING_IRON,"Soldering Iron");
        translationBuilder.add(Reforested.COPPER_GEAR,"Copper Gear");

        for(String bee: BeeLookups.BEE_TYPES)
        {
            translationBuilder.add("bee.reforested."+bee,Reforested.toTranslation(bee));
        }
        for(String beeFamily: BeeLookups.allFamilies())
        {
            translationBuilder.add("family.bee.reforested."+beeFamily,Reforested.toTranslation(beeFamily));
        }

        translationBuilder.add("thing.reforested.bee","Bee");

        translationBuilder.add("category.reforested","Reforested");
        translationBuilder.add("key.reforested.consider","Open information about Reforested");


        translationBuilder.add("text.reforested.typeof","This is a ");
        translationBuilder.add("text.reforested.bee.temperature","Preferred Temperature: ");
        translationBuilder.add("text.reforested.bee.humidity","Likes Humidity: ");
        translationBuilder.add("text.reforested.bee.natural","Is Natural: ");

        translationBuilder.add("information.reforested.bees","Bees are neat. \n - \n Around the world, ask you would expect in vanilla you will find hives, these hives no longer contain normal minecraft bees but special Reforested variants, they have working conditions that need to be met, but some bees can produce fascinating things in their combs (you need a centrifuge to process combs) and honey; to get at this new stuff, you will need your bees to be in an apiary \n - \n two bees may breed if they are in a hive with a free space");
        translationBuilder.add("information.reforested.trees","Tree breeding is one of the core tenants of Reforested \n - \n To start you will need to craft yourself some Tree Canes and place them with two saplings either side of them, after some time, before the sapling grows into a tree, one of the saplings may mutate into another type of sapling");
        translationBuilder.add("information.reforested.compartments","Compartments are a bit like chests, but they have tabs so you can organise your stuff more adequately and a search bar so you can find the stuff you supposedly organised, one other advantage of compartments is you can just shift-click items into them and they will go into the next available slot in any tab (starting from tab 1) which is pretty neat \n - \n Think of them as early/mid game organisation");
        translationBuilder.add("information.reforested.multi_farm","The Multi-Farm is block that can be used to plant and harvest saplings and crops \n - \n the farm will collect any items around it and replant as it receives things to do so, it requires power to function, and you are probably best off making 2 peat-fired engines for this");
        translationBuilder.add("information.reforested.peat","Peat is an item used in peat-fired engines to make power, it is obtained by placing Block o' Bog near water and waiting until it matures");
        translationBuilder.add("information.reforested.centrifuge","The centrifuge is an industry book that has 1 input and 9 outputs, it is mainly used to process combs into their constituent parts. The block requires a small amount of power to function, so only 1 peat-fired engine is needed to maintain it");

    }
}
