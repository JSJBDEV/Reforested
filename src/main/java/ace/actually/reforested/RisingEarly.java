package ace.actually.reforested;

import ace.actually.reforested.trees.blocks.PromisedWoodType;
import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;


public class RisingEarly implements Runnable{

    public static final ArrayList<PromisedWoodType> PROMISED_WOOD_TYPES = new ArrayList<>();

    static
    {
        PROMISED_WOOD_TYPES.add(new PromisedWoodType("larch",new int[]{-2,-44,-117},new int[]{55,50,-200},"spruce"));
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
