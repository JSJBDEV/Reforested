package ace.actually.reforested.bees;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.bees.blocks.alveary.AlvearyDehumidifierBlockEntity;
import ace.actually.reforested.bees.blocks.alveary.AlvearyHumidifierBlockEntity;
import ace.actually.reforested.bees.blocks.alveary.AlvearyPoweredBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Do ya like jazz?
 * this class is responsible for all bee related things
 * to add a new bee, simply use addBee statically
 * to add a new breeding pair, use addBreedingCombination
 * it really is that simple.
 * NOTE: when adding a new bee you should put its texture in resources/assets/reforested/textures/entity/bee
 *
 */
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

        addBee("gracious","noble",1000,3.0f,false,false,0.75f,Items.HONEYCOMB,Reforested.ROYAL_JELLY);
        addBee("titled","noble",1100,4.0f,false,false,0.75f,Items.HONEYCOMB,Reforested.ROYAL_JELLY);

        addBee("honey","honey",600,3.0f,true,false,0.75f,Items.HONEYCOMB,Items.HONEY_BOTTLE);
        addBee("candied","honey",600,3.3f,false,false,0.75f,Items.HONEYCOMB,Items.HONEY_BOTTLE);
        addBee("saccharine","honey",1000,4.1f,false,false,0.75f,Items.HONEYCOMB,Items.HONEY_BOTTLE);

        addBee("stringy","fibrous",1000,3.1f,false,false,0.75f, Reforested.FIBROUS_COMB);
        addBee("lanky","fibrous",1000,4.1f,false,false,0.75f, Reforested.FIBROUS_COMB);

        addBee("cold","frozen",1200,3f,true,false,0.2f,Reforested.COLD_COMB);
        addBee("barren","frozen",1200,4f,false,false,0.2f,Reforested.COLD_COMB);

        addBee("burning","warm",500,2f,true,false,1.2f,Reforested.HOT_COMB);
        addBee("blazing","warm",600,3f,false,false,1.2f,Reforested.HOT_COMB);

        addBee("gravely","stoney",900,3f,true,false,0.5f,Reforested.STONEY_COMB);
        addBee("earthen","stoney",800,3f,false,false,0.2f,Reforested.STONEY_COMB);

        addBee("fen","boggy",800,3f,false,true,0.75f,Reforested.PEATY_COMB);
        addBee("morass","boggy",800,5f,false,true,0.75f,Reforested.PEATY_COMB);


        addBreedingCombination("busy","civil","stringy",0.3f);
        addBreedingCombination("civil","employed","affable",0.3f);
        addBreedingCombination("honey","busy","candied",0.3f);
        addBreedingCombination("candied","honey","saccharine",0.3f);
        addBreedingCombination("affable","stringy","lanky",0.3f);
        addBreedingCombination("civil","candied","gracious",0.3f);
        addBreedingCombination("gracious","affable","titled",0.3f);
        addBreedingCombination("burning","stringy","blazing",0.3f);
        addBreedingCombination("cold","gravely","barren",0.3f);
        addBreedingCombination("employed","gravely","earthen",0.3f);
        addBreedingCombination("cold","busy","fen",0.3f);
        addBreedingCombination("fen","earthen","morass",0.3f);
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

    public static List<String> allFamilies()
    {
        List<String> families = new ArrayList<>();
        for(String key: BEE_FAMILY_MAP.keySet())
        {
            if(!families.contains(BEE_FAMILY_MAP.get(key)))
            {
                families.add(BEE_FAMILY_MAP.get(key));
            }
        }
        return families;
    }

    public record BreedingInstance(String type1, String type2, String mutation, float chance){}

    public static boolean dislikesPosition(String btype, World world, BlockPos pos)
    {
        boolean notHumid = true;
        boolean notDry = true;
        boolean notHot = true;
        boolean notCold = true;
        if(world.getBlockState(pos).isOf(Reforested.ALVEARY_MAIN_BLOCK))
        {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    for (int k = -1; k < 2; k++) {
                        BlockPos vpos = pos.add(i,j,k);
                        if(world.getBlockEntity(vpos) instanceof AlvearyHumidifierBlockEntity humidifierBlockEntity)
                        {
                            if(humidifierBlockEntity.fluidStorage.amount>0)
                            {
                                notHumid=false;
                            }
                        }
                        else if(world.getBlockEntity(vpos) instanceof AlvearyDehumidifierBlockEntity dehumidifierBlockEntity)
                        {
                            if(dehumidifierBlockEntity.fluidStorage.amount<dehumidifierBlockEntity.fluidStorage.getCapacity())
                            {
                                notDry=false;
                            }
                        }
                        else if(world.getBlockEntity(vpos) instanceof AlvearyPoweredBlockEntity poweredBlockEntity)
                        {
                            if(poweredBlockEntity.energyStorage.amount>0)
                            {
                                if(poweredBlockEntity.getCachedState().isOf(Reforested.ALVEARY_COOLER_BLOCK))
                                {
                                    notHot=false;
                                }
                                else if(poweredBlockEntity.getCachedState().isOf(Reforested.ALVEARY_HEATER_BLOCK))
                                {
                                    notCold=false;
                                }
                            }
                        }
                    }
                }
            }
        }
        //if the bee doesnt like humidity but is in a humid biome, dislike
        if(world.getBiome(pos).isIn(BiomeTags.INCREASED_FIRE_BURNOUT) && !BeeLookups.BEE_LIKES_HUMIDITY_MAP.get(btype) && notDry)
        {
            return true;
        }
        //if a bee does like humidty, but is not in a humid biome (and the alveary isn't making it humid), dislike
        if(!world.getBiome(pos).isIn(BiomeTags.INCREASED_FIRE_BURNOUT) && BeeLookups.BEE_LIKES_HUMIDITY_MAP.get(btype) && notHumid)
        {
            return true;
        }
        float preferred = BeeLookups.BEE_PREF_TEMP_MAP.get(btype);
        if(world.getBiome(pos).value().getTemperature()>preferred+0.2 && notCold)
        {
            return true;
        }
        if(world.getBiome(pos).value().getTemperature()<preferred-0.2 && notHot)
        {
            return true;
        }
        return false;
    }
}
