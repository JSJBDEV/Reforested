package ace.actually.reforested;

import ace.actually.reforested.datagen.RLootProvider;
import ace.actually.reforested.trees.blocks.wood_builders.PromisedWoodType;
import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;


public class RisingEarly implements Runnable{

    public static final ArrayList<PromisedWoodType> PROMISED_WOOD_TYPES = new ArrayList<>();


    //colour help:
    //planks distance from 83,83,83
    //leaves distance from 104,100,104
    //log distance from 45,45,45
    static
    {

        PROMISED_WOOD_TYPES.add(new PromisedWoodType("larch",new int[]{-2,-44,-117},new int[]{55,50,-200},null,"spruce",true));
        PROMISED_WOOD_TYPES.add(new PromisedWoodType("abura",new int[]{86,43,0},new int[]{-104,68,-90},null,"oak",false));
        PROMISED_WOOD_TYPES.add(new PromisedWoodType("pink_ivory",new int[]{-3,-83,-83},new int[]{-81,22,-49},new int[]{128,84,13},"oak",false));

        //20,47,17 - 5,136,0
        PROMISED_WOOD_TYPES.add(new PromisedWoodType("pistachio",new int[]{-63,-36,-66},new int[]{-99,36,-104},null,"cherry",false));

        //167,97,4 - 145, 4 113
        PROMISED_WOOD_TYPES.add(new PromisedWoodType("plum",new int[]{84,14,-79},new int[]{41,-96,9},null,"cherry",false));

        //63,36,0 - 132 217 0
        PROMISED_WOOD_TYPES.add(new PromisedWoodType("rosewood",new int[]{-20,-47,-83},new int[]{28,117,-104},null,"cherry",false));
    }


    @Override
    public void run() {
        var remapper = FabricLoader.getInstance().getMappingResolver();

        var boatType = remapper.mapClassName("intermediary", "net.minecraft.class_1690$class_1692");
        var block = remapper.mapClassName("intermediary","net.minecraft.class_2248");


        System.out.println("[MM->Reforested] Adding new boats for "+PROMISED_WOOD_TYPES.size()+" promised wood type(s)...");
        for(PromisedWoodType promisedWoodType: PROMISED_WOOD_TYPES)
        {
            String boat = promisedWoodType.name();
            ClassTinkerers.enumBuilder(boatType,"L"+block+";", "Ljava/lang/String;")
                    .addEnum("REFORESTED_"+boat.toUpperCase(), () -> new Object[]{Registries.BLOCK.get(Identifier.of("reforested",boat+"_planks")),boat})
                    .build();
        }

    }
}
