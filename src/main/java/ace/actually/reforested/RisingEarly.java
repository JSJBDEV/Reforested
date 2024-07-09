package ace.actually.reforested;

import ace.actually.reforested.trees.BoatHelper;
import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;


public class RisingEarly implements Runnable{
    @Override
    public void run() {
        var remapper = FabricLoader.getInstance().getMappingResolver();

        var boatType = remapper.mapClassName("intermediary", "net.minecraft.class_1690$class_1692");
        var block = remapper.mapClassName("intermediary","net.minecraft.class_2248");


        for(String boat: BoatHelper.BOATS_TYPES)
        {
            ClassTinkerers.enumBuilder(boatType,"L"+block+";", "Ljava/lang/String;")
                    .addEnum("REFORESTED_LARCH", () -> new Object[]{Registries.BLOCK.get(Identifier.of("reforested",boat+"_planks")),boat})
                    .build();
        }

    }
}
