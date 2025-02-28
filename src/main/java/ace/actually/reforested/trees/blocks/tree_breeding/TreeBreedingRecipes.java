package ace.actually.reforested.trees.blocks.tree_breeding;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class TreeBreedingRecipes {
    public static List<TreeBreedingPair> RECIPES = new ArrayList<>();

    public static void registerRecipes()
    {
        RECIPES.add(new TreeBreedingPair(Registries.BLOCK.get(Identifier.of("reforested","pink_ivory_sapling")),Blocks.CHERRY_SAPLING,Blocks.ACACIA_SAPLING,0.3f));
        RECIPES.add(new TreeBreedingPair(Registries.BLOCK.get(Identifier.of("reforested","abura_sapling")),Blocks.OAK_SAPLING,Blocks.JUNGLE_SAPLING,0.3f));
        RECIPES.add(new TreeBreedingPair(Registries.BLOCK.get(Identifier.of("reforested","plum_sapling")),Blocks.CHERRY_SAPLING,Blocks.DARK_OAK_SAPLING,0.3f));
        RECIPES.add(new TreeBreedingPair(Registries.BLOCK.get(Identifier.of("reforested","rosewood_sapling")),Blocks.CHERRY_SAPLING,Registries.BLOCK.get(Identifier.of("reforested","pink_ivory_sapling")),0.3f));
        RECIPES.add(new TreeBreedingPair(Registries.BLOCK.get(Identifier.of("reforested","pistachio_sapling")),Blocks.JUNGLE_SAPLING,Registries.BLOCK.get(Identifier.of("reforested","plum_sapling")),0.3f));

    }


    public record TreeBreedingPair(Block saplingOut, Block sapling1In, Block sapling2In, float chance) {}

}
