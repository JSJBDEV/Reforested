package ace.actually.reforested.trees.blocks.tree_breeding;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TreeCaneBlock extends Block {
    public TreeCaneBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        if(sourceBlock instanceof SaplingBlock)
        {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if(!sourcePos.equals(pos.add(i,0,j)))
                    {
                        if(world.getBlockState(pos.add(i,0,j)).getBlock() instanceof SaplingBlock)
                        {

                        }
                    }
                }
            }
        }
    }
}
