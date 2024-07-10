package ace.actually.reforested.trees.blocks.signs.be;

import ace.actually.reforested.Reforested;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HangingSignBlockEntity;
import net.minecraft.util.math.BlockPos;

import java.sql.Ref;

public class ModdedHangingSignBlockEntity extends HangingSignBlockEntity {
    public ModdedHangingSignBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return Reforested.MODDED_HANGING_SIGN_BLOCK_ENTITY;
    }
}
