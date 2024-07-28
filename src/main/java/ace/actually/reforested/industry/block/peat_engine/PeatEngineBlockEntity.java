package ace.actually.reforested.industry.block.peat_engine;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.industry.GenericInventory;
import ace.actually.reforested.bees.blocks.ProgressData;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class PeatEngineBlockEntity extends BlockEntity implements GenericInventory, ExtendedScreenHandlerFactory<ProgressData> {
    public PeatEngineBlockEntity(BlockPos pos, BlockState state) {
        super(Reforested.PEAT_ENGINE_BLOCK_ENTITY, pos, state);
    }

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(1000, 0,100) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(10, ItemStack.EMPTY);


    @Override
    public Text getDisplayName() {
        return Text.of("Peat-Fired Engine");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PeatEngineScreenHandler(syncId, playerInventory, this);
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
        nbt.putLong("energy",energyStorage.amount);
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, this.inventory,registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        ticksToComplete =nbt.getInt("percentageComplete");
        energyStorage.amount=nbt.getLong("energy");
        Inventories.readNbt(nbt, this.inventory, registryLookup);
    }

    public static void tick(World world, BlockPos pos, BlockState state, PeatEngineBlockEntity be) {
        if(be.ticksToComplete<1 && be.inventory.getFirst().isOf(Reforested.PEAT))
        {
            be.inventory.getFirst().decrement(1);
            be.setTicksToComplete(1000);
            world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
        }
        if(be.ticksToComplete>0)
        {
            if(be.energyStorage.capacity>be.energyStorage.amount)
            {
                be.energyStorage.amount+=6;
            }
            be.setTicksToComplete(be.getTicksToComplete()-1);
        }
        for(Direction direction: Direction.values())
        {
            EnergyStorage storage = EnergyStorage.SIDED.find(world,pos.offset(direction),direction.getOpposite());
            if(storage!=null)
            {
                EnergyStorageUtil.move(be.energyStorage,storage,Long.MAX_VALUE,null);
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
