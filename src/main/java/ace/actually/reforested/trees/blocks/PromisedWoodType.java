package ace.actually.reforested.trees.blocks;

public record PromisedWoodType(String name,int[] planksColour, int[] leavesColour,String likeTree) {

    public WoodBlockBuilder create()
    {
        return new WoodBlockBuilder(name,planksColour,leavesColour,likeTree);
    }
}
