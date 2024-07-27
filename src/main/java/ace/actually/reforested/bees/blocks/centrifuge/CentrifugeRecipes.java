package ace.actually.reforested.bees.blocks.centrifuge;

import ace.actually.reforested.Reforested;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;

/**
 * Here you can add a centrifuge recipe
 * just use CentrifugeRecipes.RECIPES.put();
 * The centrifuge doesn't actually check recipes until you put an item in
 * and even then it's a HashMap, so it's fast at determining if it exists or not
 */
public class CentrifugeRecipes {
    public static HashMap<Item, ChancePair[]> RECIPES = new HashMap<>();

    public static void registerRecipes()
    {
        RECIPES.put(Items.HONEYCOMB,new ChancePair[]{new ChancePair(Items.SUGAR,0.4f),new ChancePair(Reforested.PROPOLIS,0.5f)});
        RECIPES.put(Reforested.FIBROUS_COMB,new ChancePair[]{new ChancePair(Reforested.SILK,0.9f),new ChancePair(Items.STRING,0.5f),new ChancePair(Reforested.PROPOLIS,0.3f)});
        RECIPES.put(Reforested.COLD_COMB,new ChancePair[]{new ChancePair(Items.ICE,0.9f),new ChancePair(Items.SUGAR,0.5f),new ChancePair(Reforested.PROPOLIS,0.3f)});
        RECIPES.put(Reforested.HOT_COMB,new ChancePair[]{new ChancePair(Items.BLAZE_POWDER,0.9f),new ChancePair(Items.SUGAR,0.5f),new ChancePair(Reforested.PROPOLIS,0.3f)});
        RECIPES.put(Reforested.STONEY_COMB,new ChancePair[]{new ChancePair(Items.DIORITE,0.9f),new ChancePair(Items.SUGAR,0.2f),new ChancePair(Reforested.PROPOLIS,0.3f)});
        RECIPES.put(Reforested.PEATY_COMB,new ChancePair[]{new ChancePair(Reforested.PEAT,0.9f),new ChancePair(Items.SUGAR,0.3f),new ChancePair(Reforested.PROPOLIS,0.3f)});

    }


    public record ChancePair(Item item, float chance) {}
}
