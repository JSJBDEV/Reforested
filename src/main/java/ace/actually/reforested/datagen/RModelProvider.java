package ace.actually.reforested.datagen;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.WoodBlockBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

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
            gen.registerTintableCross(builder.SAPLINGS, BlockStateModelGenerator.TintType.NOT_TINTED);
            gen.registerCubeAllModelTexturePool(builder.PLANKS).family(builder.FAMILY);
            gen.registerHangingSign(builder.STRIPPED_LOG,builder.HANGING_SIGN,builder.WALL_HANGING_SIGN);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        //You would think Id have to generate item models for wood here
        //apparently not.
    }
}
