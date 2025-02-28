package ace.actually.reforested.datagen;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.wood_builders.WoodBlockBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class RModelProvider extends FabricModelProvider {
    public RModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator gen) {
        for(WoodBlockBuilder builder: Reforested.WOOD_BLOCKS)
        {
            gen.registerLog(builder.LOG).log(builder.LOG).wood(builder.WOOD);
            gen.registerLog(builder.STRIPPED_LOG).log(builder.STRIPPED_LOG).wood(builder.STRIPPED_WOOD);
            gen.registerSimpleCubeAll(builder.LEAVES);
            //gen.registerTintableCross(builder.SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
            gen.registerCubeAllModelTexturePool(builder.PLANKS).family(builder.createFamily());
            gen.registerHangingSign(builder.STRIPPED_LOG,builder.HANGING_SIGN,builder.WALL_HANGING_SIGN);
            gen.registerFlowerPotPlant(builder.SAPLING,builder.POTTED_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);



        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        //You would think Id have to generate item models for wood here
        //apparently not.
        for(WoodBlockBuilder builder: Reforested.WOOD_BLOCKS)
        {
            gen.register(Registries.ITEM.get(Identifier.of("reforested",builder.woodName+"_boat")), Models.GENERATED);
            gen.register(Registries.ITEM.get(Identifier.of("reforested",builder.woodName+"_chest_boat")), Models.GENERATED);
        }
    }
}
