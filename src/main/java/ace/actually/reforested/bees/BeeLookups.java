package ace.actually.reforested.bees;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.datagen.RLanguageProvider;
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
    public static HashMap<String,Boolean> BEE_LIKES_HUMIDITY_MAP = new HashMap<>();
    public static HashMap<String,Float> BEE_PREF_TEMP_MAP = new HashMap<>();

    public static List<String> NATURAL_BEE_TYPES = new ArrayList<>();



    public static List<BreedingInstance> BEE_BREEDING_CHANCES = new ArrayList<>();
    static
    {
        addBee("busy","basic",600,3.0f,true,false,0.75f, Items.HONEYCOMB);
        addBee("employed","basic",1000,3.5f,false,false,0.75f, Items.HONEYCOMB);

        addBee("civil","courtly",700,3.0f,true,false,0.75f,Items.HONEYCOMB);
        addBee("affable","courtly",700,3.4f,false,false,0.75f,Items.HONEYCOMB);

        addBee("honey","honey",600,3.0f,true,false,0.75f,Items.HONEYCOMB,Items.HONEY_BOTTLE);
        addBee("candied","honey",600,3.3f,false,false,0.75f,Items.HONEYCOMB,Items.HONEY_BOTTLE);
        addBee("saccharine","honey",1000,4.1f,false,false,0.75f,Items.HONEYCOMB,Items.HONEY_BOTTLE);

        addBee("stringy","fibrous",1000,3.1f,false,false,0.75f, Reforested.FIBROUS_COMB);
        addBee("lanky","fibrous",1000,4.1f,false,false,0.75f, Reforested.FIBROUS_COMB);

        addBreedingCombination("busy","civil","stringy",0.3f);
        addBreedingCombination("civil","employed","affable",0.3f);
        addBreedingCombination("honey","busy","candied",0.3f);
        addBreedingCombination("candied","honey","saccharine",0.3f);
        addBreedingCombination("affable","stringy","lanky",0.3f);
    }

    public static void addBee(String name, String branch,int ticksNoNectar,float productivity,boolean isNatural,boolean likesHumidity,float preferredTemperature, Item... products)
    {
        BEE_TYPES.add(name);
        BEE_FAMILY_MAP.put(name,branch);
        BEE_HIVE_TICKS_MAP.put(name,ticksNoNectar);
        BEE_PRODUCTIVITY_MAP.put(name,productivity);
        BEE_ITEM_MAP.put(name,products);
        BEE_PREF_TEMP_MAP.put(name,preferredTemperature);
        BEE_LIKES_HUMIDITY_MAP.put(name,likesHumidity);
        if(isNatural)NATURAL_BEE_TYPES.add(name);
    }

    public static void addBreedingCombination(String type1, String type2, String mutation, float chance)
    {
        BEE_BREEDING_CHANCES.add(new BreedingInstance(type1, type2, mutation, chance));
    }

    public record BreedingInstance(String type1, String type2, String mutation, float chance){}

}
