package ace.actually.reforested.bees.blocks.alveary;

import ace.actually.reforested.Reforested;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class AlvearyPoweredBlockEntity extends BlockEntity {
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(1000, 100,0) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }

        @Override
        public boolean supportsInsertion() {
            return true;
        }
    };


    public AlvearyPoweredBlockEntity(BlockPos pos, BlockState state) {
        super(Reforested.ALVEARY_POWERED_BLOCK_ENTITY, pos, state);

    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putLong("energy",energyStorage.amount);
        super.writeNbt(nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        energyStorage.amount=nbt.getLong("fluid");
    }
    public static void tick(World world, BlockPos pos, BlockState state, AlvearyPoweredBlockEntity be)
    {
       if(be.energyStorage.amount>0)
       {
           be.energyStorage.amount--;
           be.markDirty();
       }
    }
}
