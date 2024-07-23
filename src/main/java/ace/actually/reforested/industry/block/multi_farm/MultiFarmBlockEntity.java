package ace.actually.reforested.industry.block.multi_farm;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.bees.blocks.ApiaryBlock;
import ace.actually.reforested.bees.blocks.GenericInventory;
import ace.actually.reforested.bees.blocks.ProgressData;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.sql.Ref;

public class MultiFarmBlockEntity extends BlockEntity implements GenericInventory, ExtendedScreenHandlerFactory<ProgressData> {
    public MultiFarmBlockEntity(BlockPos pos, BlockState state) {
        super(Reforested.MULTIFARM_BLOCK_ENTITY, pos, state);
    }

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(1000, 100,0) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(19, ItemStack.EMPTY);


    @Override
    public Text getDisplayName() {
        return Text.of("Multi Farm");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MultiFarmScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    private int ticksToComplete = -1;

    public int getTicksToComplete() {
        return ticksToComplete;
    }

    public void setTicksToComplete(int ticksToComplete) {
        this.ticksToComplete = ticksToComplete;
        markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("percentageComplete", ticksToComplete);
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, this.inventory,registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        ticksToComplete =nbt.getInt("percentageComplete");
        Inventories.readNbt(nbt, this.inventory, registryLookup);
    }

    public static void tick(World world, BlockPos pos, BlockState state, MultiFarmBlockEntity be) {
        be.tickPlan();

    }

    public void tickPlan()
    {
        for (int i = 1; i < 5; i++) {
            for (int j = -(i-1); j <= (i-1); j++) {
                world.setBlockState(pos.add(i,0,j), Blocks.BAMBOO_PLANKS.getDefaultState());
            }
        }
        for (int i = 1; i < 5; i++) {
            for (int j = -(i-1); j <= (i-1); j++) {
                world.setBlockState(pos.add(-i,0,j), Blocks.OAK_PLANKS.getDefaultState());
            }
        }
        for (int i = 1; i < 5; i++) {
            for (int j = -(i-1); j <= (i-1); j++) {
                world.setBlockState(pos.add(j,0,-i), Blocks.BIRCH_PLANKS.getDefaultState());
            }
        }
        for (int i = 1; i < 5; i++) {
            for (int j = -(i-1); j <= (i-1); j++) {
                world.setBlockState(pos.add(-j,0,i), Blocks.JUNGLE_PLANKS.getDefaultState());
            }
        }

    }


    @Override
    public ProgressData getScreenOpeningData(ServerPlayerEntity player) {
        NbtCompound v = new NbtCompound();
        v.putInt("x",pos.getX());
        v.putInt("y",pos.getY());
        v.putInt("z",pos.getZ());
        return new ProgressData(v);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
