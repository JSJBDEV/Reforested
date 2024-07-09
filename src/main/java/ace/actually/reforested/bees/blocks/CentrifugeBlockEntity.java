package ace.actually.reforested.bees.blocks;

import ace.actually.reforested.Reforested;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CentrifugeBlockEntity extends BlockEntity implements GenericInventory, ExtendedScreenHandlerFactory<CentrifugeBlockEntity.ProgressData> {
    public CentrifugeBlockEntity(BlockPos pos, BlockState state) {
        super(Reforested.CENTRIFUGE_BLOCK_ENTITY, pos, state);
    }
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(10, ItemStack.EMPTY);


    @Override
    public Text getDisplayName() {
        return Text.of("Centrifuge");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CentrifugeScreenHandler(syncId, playerInventory, this);
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

    public static void tick(World world, BlockPos pos, BlockState state, CentrifugeBlockEntity be) {
        if(!be.inventory.get(9).isEmpty() && be.inventory.get(9).isOf(Items.HONEYCOMB))
        {
            if(be.getTicksToComplete()==-1)
            {
                be.setTicksToComplete(1000);
                System.out.println("started");
            }

            if(be.getTicksToComplete()==0)
            {
                System.out.println("finished");
            }
            else if(be.getTicksToComplete()>0)
            {
                be.setTicksToComplete(be.getTicksToComplete()-1);
                world.updateListeners(pos,state,state, ApiaryBlock.NOTIFY_LISTENERS);
            }
        }

    }

    public record ProgressData(NbtCompound label)
    {
        public static final PacketCodec<RegistryByteBuf, ProgressData> PACKET_CODEC = PacketCodec.tuple(PacketCodecs.NBT_COMPOUND,
                ProgressData::label,
                ProgressData::new);
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
