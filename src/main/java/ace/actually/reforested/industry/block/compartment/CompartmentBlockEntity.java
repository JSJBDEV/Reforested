package ace.actually.reforested.industry.block.compartment;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.industry.GenericInventory;
import ace.actually.reforested.bees.blocks.ProgressData;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CompartmentBlockEntity extends BlockEntity implements GenericInventory, ExtendedScreenHandlerFactory<ProgressData> {



    public CompartmentBlockEntity(BlockPos pos, BlockState state) {
        super(Reforested.COMPARTMENT_BLOCK_ENTITY, pos, state);
    }

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27*Reforested.COMPARTMENT_BLOCK_TO_TABS.get(getCachedState().getBlock()), ItemStack.EMPTY);


    @Override
    public Text getDisplayName() {
        return Text.of("Compartment");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CompartmentScreenHandler(syncId, playerInventory, this);
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

    public static void tick(World world, BlockPos pos, BlockState state, CompartmentBlockEntity be) {


    }




    @Override
    public ProgressData getScreenOpeningData(ServerPlayerEntity player) {
        NbtCompound v = new NbtCompound();
        v.putInt("tabCount",Reforested.COMPARTMENT_BLOCK_TO_TABS.get(getCachedState().getBlock()));
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
