package ace.actually.reforested.trees.blocks.signs;

import ace.actually.reforested.trees.blocks.signs.be.ModdedHangingSignBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.HangingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ModdedHangingSign extends HangingSignBlock {
    public ModdedHangingSign(WoodType woodType, Settings settings) {
        super(woodType, settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ModdedHangingSignBlockEntity(pos,state);
    }
}
