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

public class AlvearyDehumidifierBlockEntity extends BlockEntity {
    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            // Here, you can pick your capacity depending on the fluid variant.
            // For example, if we want to store 8 buckets of any fluid:
            return (8 * FluidConstants.BUCKET) / 81; // This will convert it to mB amount to be consistent;
        }

        @Override
        protected boolean canInsert(FluidVariant variant) {
            return variant.isOf(Fluids.WATER);
        }

        @Override
        protected void onFinalCommit() {
            // Called after a successful insertion or extraction, markDirty to ensure the new amount and variant will be saved properly.
            markDirty();
        }
    };


    public AlvearyDehumidifierBlockEntity(BlockPos pos, BlockState state) {
        super(Reforested.ALVEARY_DEHUMIDIFIER_BLOCK_ENTITY, pos, state);

    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putLong("fluid",fluidStorage.amount);
        super.writeNbt(nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        fluidStorage.amount=nbt.getLong("fluid");
    }
    public static void tick(World world, BlockPos pos, BlockState state, AlvearyDehumidifierBlockEntity be)
    {
        if(be.fluidStorage.amount<be.fluidStorage.getCapacity())
        {
            be.fluidStorage.variant=FluidVariant.of(Fluids.WATER);
            be.fluidStorage.amount++;
            be.markDirty();
        }
    }
}
