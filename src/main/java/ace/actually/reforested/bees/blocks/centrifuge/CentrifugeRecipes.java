package ace.actually.reforested.bees.blocks.centrifuge;

import ace.actually.reforested.Reforested;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;

public class CentrifugeRecipes {
    public static HashMap<Item, ChancePair[]> RECIPES = new HashMap<>();

    public static void registerRecipes()
    {
        RECIPES.put(Items.HONEYCOMB,new ChancePair[]{new ChancePair(Items.SUGAR,0.4f),new ChancePair(Reforested.PROPOLIS,0.5f)});
    }


    public record ChancePair(Item item, float chance) {
    }
}
