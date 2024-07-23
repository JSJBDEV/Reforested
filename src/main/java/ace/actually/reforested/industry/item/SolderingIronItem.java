package ace.actually.reforested.industry.item;

import ace.actually.reforested.Reforested;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SolderingIronItem extends Item {
    public SolderingIronItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = new ItemStack(Reforested.CIRCUIT_BOARD);
        NbtCompound compound = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA,NbtComponent.DEFAULT).copyNbt();
        compound.putString("east","minecraft:oak_sapling");
        compound.putString("west","minecraft:birch_sapling");
        compound.putString("south","minecraft:jungle_sapling");
        compound.putString("north","minecraft:spruce_sapling");
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(compound));

        user.giveItemStack(stack);
        return super.use(world, user, hand);
    }
}
