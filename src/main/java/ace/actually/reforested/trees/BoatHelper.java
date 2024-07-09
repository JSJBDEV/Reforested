package ace.actually.reforested.trees;

import ace.actually.reforested.Reforested;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class BoatHelper {

    public static List<String> BOATS_TYPES = new ArrayList<>();

    public static void registerBoatItems()
    {
        for(String boat: BOATS_TYPES)
        {
            BoatItem BOAT_ITEM = new BoatItem(false, BoatEntity.Type.getType(boat),new Item.Settings());
            BoatItem CHEST_BOAT_ITEM = new BoatItem(true, BoatEntity.Type.getType(boat),new Item.Settings());


            Reforested.ITEMS.add(Registry.register(Registries.ITEM, Identifier.of("reforested",boat+"_boat"),BOAT_ITEM));
            Reforested.ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested",boat+"_chest_boat"),CHEST_BOAT_ITEM));

        }

    }


}
