package ace.actually.reforested.trees.blocks.signs.be;

import ace.actually.reforested.Reforested;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.util.math.BlockPos;

public class ModdedSignBlockEntity extends SignBlockEntity {

    public ModdedSignBlockEntity(BlockPos pos, BlockState state) {
        super(Reforested.MODDED_SIGN_BLOCK_ENTITY,pos, state);
    }
}
