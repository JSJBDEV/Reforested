package ace.actually.reforested.industry.block.multi_farm;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.industry.GenericInventory;
import ace.actually.reforested.bees.blocks.ProgressData;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class MultiFarmBlockEntity extends BlockEntity implements GenericInventory, ExtendedScreenHandlerFactory<ProgressData> {
    public MultiFarmBlockEntity(BlockPos pos, BlockState state) {
        super(Reforested.MULTIFARM_BLOCK_ENTITY, pos, state);
    }

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

    public static void tick(World world, BlockPos pos, BlockState state, MultiFarmBlockEntity be) {
        be.tickPlan();

    }

    private void harvest(BlockPos n)
    {
        //this will break if the target crop is in the crops tag, but doesnt have a MAX_AGE field!
        if(world.getBlockState(n).isIn(BlockTags.CROPS) && world.getBlockState(n).get(CropBlock.AGE)==CropBlock.MAX_AGE)
        {
            world.breakBlock(n,true);

        }
        if(world.getBlockState(n).isIn(BlockTags.LOGS))
        {
            for (int k = -7; k < 8; k++) {
                for (int l = 0; l < 20; l++) {
                    for (int m = -7; m < 8; m++) {
                        BlockPos logsLeaves = n.add(k,l,m);
                        if(world.getBlockState(logsLeaves).isIn(BlockTags.LOGS) || world.getBlockState(logsLeaves).isIn(BlockTags.LEAVES))
                        {
                            world.breakBlock(logsLeaves,true);
                        }
                    }
                }
            }
        }
    }

    private void canPutInInventory(ItemEntity entity)
    {
        ItemStack stack = entity.getStack();
        for (int i = 0; i < getItems().size(); i++) {
            if(getItems().get(i).isOf(stack.getItem()) && getItems().get(i).getCount()<getItems().get(i).getMaxCount())
            {
                int canAdd = getItems().get(i).getMaxCount()-getItems().get(i).getCount();
                if(stack.getCount()<=canAdd)
                {
                    getItems().get(i).increment(stack.getCount());
                    entity.discard();
                    markDirty();
                    break;
                }
            }
            if(getItems().get(i).isEmpty())
            {
                getItems().set(i,stack);
                entity.discard();
                markDirty();
                break;
            }
        }

    }

    private List<BlockPos> neededCrops = new ArrayList<>();
    private void getNeededCrops()
    {
        neededCrops=new ArrayList<>();
        for (int i = 2; i < 6; i++) {
            for (int j = -(i-1); j <= (i-1); j++) {
                BlockPos n = pos.add(i,0,j);
                if(world.getBlockState(n).isAir())
                {
                    neededCrops.add(n);
                }
                harvest(n);
            }
        }
        for (int i = 2; i < 6; i++) {
            for (int j = -(i-1); j <= (i-1); j++) {
                BlockPos n = pos.add(-i,0,j);
                if(world.getBlockState(n).isAir())
                {
                    neededCrops.add(n);
                }
                harvest(n);

            }
        }
        for (int i = 2; i < 6; i++) {
            for (int j = -(i-1); j <= (i-1); j++) {
                BlockPos n = pos.add(j,0,-i);
                if(world.getBlockState(n).isAir())
                {
                    neededCrops.add(n);
                }
                harvest(n);
            }
        }
        for (int i = 2; i < 6; i++) {
            for (int j = -(i-1); j <= (i-1); j++) {
                BlockPos n = pos.add(-j,0,i);
                if(world.getBlockState(n).isAir())
                {
                    neededCrops.add(n);
                }
                harvest(n);
            }
        }
        world.getEntitiesByClass(ItemEntity.class,new Box(pos.add(-8,-2,-8).toCenterPos(),pos.add(8,3,8).toCenterPos()),(a)-> true).forEach(this::canPutInInventory);
    }

    public void tickPlan()
    {
        if(energyStorage.amount>99)
        {
            if(world.getTimeOfDay()%100==0)
            {
                getNeededCrops();
                energyStorage.amount-=100;
                markDirty();
            }
        }
        if(energyStorage.amount>9)
        {
            if(!neededCrops.isEmpty())
            {
                energyStorage.amount-=10;
                BlockState cropState;
                for(ItemStack stack: getItems())
                {
                    if(stack.isIn(ItemTags.SAPLINGS))
                    {

                        cropState = Registries.BLOCK.get(Registries.ITEM.getId(stack.getItem())).getDefaultState();
                        while (!neededCrops.isEmpty() && stack.getCount()>0)
                        {
                            stack.decrement(1);
                            world.setBlockState(neededCrops.getFirst(),cropState);
                            neededCrops.removeFirst();
                        }

                    }
                    else if(stack.getItem() instanceof AliasedBlockItem aliasedBlockItem)
                    {
                        if(aliasedBlockItem.getBlock().getDefaultState().isIn(BlockTags.CROPS))
                        {
                            cropState = aliasedBlockItem.getBlock().getDefaultState();
                            while (!neededCrops.isEmpty() && stack.getCount()>0)
                            {
                                stack.decrement(1);
                                world.setBlockState(neededCrops.getFirst().down(),Blocks.FARMLAND.getDefaultState());
                                world.setBlockState(neededCrops.getFirst(),cropState);
                                neededCrops.removeFirst();
                            }
                        }

                    }
                }
                markDirty();
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
