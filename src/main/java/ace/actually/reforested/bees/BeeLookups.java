package ace.actually.reforested.bees;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BeeLookups {
    public static List<String> BEE_TYPES = new ArrayList<>();
    public static HashMap<String,String> BEE_FAMILY_MAP = new HashMap<>();
    public static HashMap<String,Integer> BEE_HIVE_TICKS_MAP = new HashMap<>();
    public static HashMap<String,Float> BEE_PRODUCTIVITY_MAP = new HashMap<>();
    public static HashMap<String, Item[]> BEE_ITEM_MAP = new HashMap<>();
    public static List<String> NATURAL_BEE_TYPES = new ArrayList<>();

    public static List<String[]> BEE_BREEDING_COMBINATIONS = new ArrayList<>();
    public static List<Float> BEE_BREEDING_CHANCES = new ArrayList<>();
    static
    {
        addBee("busy","basic",600,3.0f,true, Items.HONEYCOMB);
        addBee("employed","basic",1000,3.5f,false, Items.HONEYCOMB);

        addBee("civil","courtly",700,3.0f,true,Items.HONEYCOMB);
        addBee("affable","courtly",700,3.4f,false,Items.HONEYCOMB);

        addBee("honey","honey",600,3.0f,true,Items.HONEYCOMB,Items.HONEY_BOTTLE);
        addBee("candied","honey",600,3.3f,false,Items.HONEYCOMB,Items.HONEY_BOTTLE);
        addBee("saccharine","honey",1000,4.1f,false,Items.HONEYCOMB,Items.HONEY_BOTTLE);

        addBee("fibrous","stringy",1000,4.1f,false,Items.HONEYCOMB);

    }

    public static void addBee(String name, String branch,int ticksNoNectar,float productivity,boolean isNatural, Item... products)
    {
        BEE_TYPES.add(name);
        BEE_FAMILY_MAP.put(name,branch);
        BEE_HIVE_TICKS_MAP.put(name,ticksNoNectar);
        BEE_PRODUCTIVITY_MAP.put(name,productivity);
        BEE_ITEM_MAP.put(name,products);
        if(isNatural)NATURAL_BEE_TYPES.add(name);
    }

    public static void addBreedingCombination(String type1, String type2, String mutation, float chance)
    {
        BEE_BREEDING_COMBINATIONS.add(new String[]{type1,type2,mutation});
        BEE_BREEDING_CHANCES.add(chance);
    }

}
