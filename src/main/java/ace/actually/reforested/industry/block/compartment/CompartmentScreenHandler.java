package ace.actually.reforested.industry.block.compartment;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.bees.blocks.ProgressData;
import ace.actually.reforested.bees.blocks.centrifuge.CentrifugeScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class CompartmentScreenHandler extends ScreenHandler {


    private final Inventory inventory;
    private NbtCompound label;




    public NbtCompound getLabel() {
        return label;
    }

    public CompartmentScreenHandler(int i, PlayerInventory playerInventory, ProgressData progressData)
    {
        this(i,playerInventory, new SimpleInventory(progressData.label().getInt("tabCount")*27));
        label=progressData.label();



    }

    //This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
    //and can therefore directly provide it as an argument. This inventory will then be synced to the client.
    public CompartmentScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(Reforested.COMPARTMENT_SCREEN_HANDLER, syncId);
        checkSize(inventory, 27);
        this.inventory = inventory;
        //some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player);
        System.out.println(this.inventory.size());


        int m;
        int l;
        int v = 0;


        //first tab
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new DisablableSlot(inventory, v, 8 + l * 18, 18 + m * 18));
                v++;
            }
        }

        //extra tabs
        for (int tabs = 1; tabs < inventory.size()/27; tabs++) {
            for (m = 0; m < 3; ++m) {
                for (l = 0; l < 9; ++l) {
                    DisablableSlot slot = new DisablableSlot(inventory, v, 8 + l * 18, 18 + m * 18);
                    slot.enableSlot(false);
                    this.addSlot(slot);
                    v++;
                }
            }
        }

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }




    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }


    public static class DisablableSlot extends Slot
    {
        private boolean enb = true;
        public DisablableSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }
        public void enableSlot(boolean s)
        {
            this.enb=s;
            markDirty();
        }

        @Override
        public boolean isEnabled() {
            return this.enb;
        }
    }
}
