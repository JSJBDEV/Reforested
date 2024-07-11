package ace.actually.reforested.trees.blocks.wood_builders;

import org.jetbrains.annotations.Nullable;

/**
 * We need to "promise" that in the future we will make a WoodBlockBuilder.
 * this is because we need to edit the boat enum before the mod is actually instantiated
 * because of this, we cannot use WoodBlockBuilder as it requires runtime data,
 * so instead we have this simple record that we can get the name from for the boat enums
 * then when the mod is actually instantiated we run create() to make it an actual WoodBlockBuilder.
 * There is more text in this comment then there was in the file before the comment was added.
 * @param name
 * @param planksColour
 * @param leavesColour
 * @param likeTree
 */
public record PromisedWoodType(String name, int[] planksColour, int[] leavesColour, int @Nullable [] logsColour , String likeTree, boolean natural) {


    public WoodBlockBuilder create()
    {
        return new WoodBlockBuilder(name,planksColour,leavesColour,logsColour,likeTree,natural);
    }
}
