package ace.actually.reforested.trees.blocks.signs;

import ace.actually.reforested.trees.blocks.signs.be.ModdedSignBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ModdedWallSign extends WallSignBlock {
    public ModdedWallSign(WoodType woodType, Settings settings) {
        super(woodType, settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ModdedSignBlockEntity(pos,state);
    }
}
