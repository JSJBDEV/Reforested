package ace.actually.reforested.trees.blocks.signs;

import ace.actually.reforested.trees.blocks.signs.be.ModdedSignBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ModdedSign extends SignBlock {
    public ModdedSign(WoodType woodType, Settings settings) {
        super(woodType, settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ModdedSignBlockEntity(pos, state);
    }
}
