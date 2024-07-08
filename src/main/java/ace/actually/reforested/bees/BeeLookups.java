package ace.actually.reforested.bees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BeeLookups {
    public static List<String> BEE_TYPES = new ArrayList<>();
    public static HashMap<String,String> BEE_FAMILY_MAP = new HashMap<>();
    public static HashMap<String,Integer> BEE_HIVE_TICKS_MAP = new HashMap<>();
    static
    {
        addBee("busy","basic",600);
        addBee("employed","basic",600);


    }

    public static void addBee(String name, String branch,int ticksNoNectar)
    {
        BEE_TYPES.add(name);
        BEE_FAMILY_MAP.put(name,branch);
        BEE_HIVE_TICKS_MAP.put(name,ticksNoNectar);

    }

}
