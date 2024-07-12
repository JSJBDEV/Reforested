package ace.actually.reforested.trees.blocks.tree_breeding;

import net.minecraft.block.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class TreeCaneBlock extends Block {
    public TreeCaneBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(
                Block.createCuboidShape(0,0,0,2,16,2),
                Block.createCuboidShape(14,0,14,16,16,16),
                Block.createCuboidShape(0,0,14,2,16,16),
                Block.createCuboidShape(14,0,0,16,16,2));
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        System.out.println("get: "+neighborState);
        if(neighborState.getBlock() instanceof SaplingBlock saplingBlock && neighborState.get(Properties.STAGE)!=0)
        {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if(!neighborPos.equals(pos.add(i,0,j)))
                    {
                        if(world.getBlockState(pos.add(i,0,j)).getBlock() instanceof SaplingBlock otherSapling)
                        {
                            for(TreeBreedingRecipes.TreeBreedingPair breedingPair: TreeBreedingRecipes.RECIPES)
                            {
                                if((saplingBlock==breedingPair.sapling1In() && otherSapling==breedingPair.sapling2In()) || (saplingBlock==breedingPair.sapling2In() && otherSapling==breedingPair.sapling1In()))
                                {
                                    System.out.println(saplingBlock.getTranslationKey()+" "+otherSapling.getTranslationKey());
                                    if(world.getRandom().nextFloat()<breedingPair.chance())
                                    {
                                        world.setBlockState(pos.add(i,0,j),breedingPair.saplingOut().getDefaultState(),3);
                                    }
                                    if(world.getRandom().nextInt(3)==0)
                                    {
                                        world.setBlockState(pos, Blocks.AIR.getDefaultState(),3);
                                    }
                                    return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
                                }
                            }
                        }
                    }
                }
            }
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        System.out.println(sourceBlock);
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        System.out.println(sourceBlock);


    }
}
