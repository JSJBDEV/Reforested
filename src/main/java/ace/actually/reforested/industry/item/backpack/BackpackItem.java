package ace.actually.reforested.industry.item.backpack;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.industry.GenericInventory;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

public class BackpackItem extends Item {
    private int maxStorage = 200;

    public BackpackItem(Settings settings,int maxStorage) {
        super(settings);
        this.maxStorage=maxStorage;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if(entity instanceof PlayerEntity player && stack.getOrDefault(Reforested.BACKPACK_PICKUP_MODE,false))
        {
            for (int i = 9; i < player.getInventory().size(); i++) {
                if(player.getInventory().getStack(i).isIn(Reforested.DIGGINGS))
                {
                    ItemStack in = player.getInventory().getStack(i);
                    if (addItemToBag(stack,in))
                    {
                        in.setCount(0);
                    }

                }
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient && user.getStackInHand(hand).isOf(this))
        {
            ItemStack stack = user.getStackInHand(hand);
            stack.set(Reforested.BACKPACK_PICKUP_MODE,user.isSneaking());
            user.sendMessage(Text.translatable("text.reforested.backpack.mode").append(": "+user.isSneaking()));
        }

        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getPlayer().isSneaking() && context.getWorld().getBlockEntity(context.getBlockPos()) instanceof Inventory inventory)
        {
            List<String> toRemove = new ArrayList<>();
            NbtCompound compound = context.getStack().getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
            for(int i = 0; i < inventory.size(); i++)
            {
                for (String key: compound.getKeys()) {
                    if(inventory.getStack(i).isEmpty())
                    {
                        if(compound.getInt(key)==0)
                        {
                            continue;
                        }
                        if(compound.getInt(key)<=64)
                        {
                            inventory.setStack(i,new ItemStack(Registries.ITEM.get(Identifier.of(key)),compound.getInt(key)));
                            compound.putInt(key,0);
                            toRemove.add(key);
                            break;
                        }
                        else
                        {
                            inventory.setStack(i,new ItemStack(Registries.ITEM.get(Identifier.of(key)),64));
                            compound.putInt(key,compound.getInt(key)-64);
                        }
                    }
                    else
                    {
                        break;
                    }
                }
            }
            for(String v: toRemove)
            {
                compound.remove(v);
            }
            context.getWorld().playSound(null,context.getPlayer().getBlockPos().up(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER.value(), SoundCategory.BLOCKS,1,1);
            context.getStack().set(DataComponentTypes.CUSTOM_DATA,NbtComponent.of(compound));
        }
        return super.useOnBlock(context);
    }

    private boolean addItemToBag(ItemStack bag, ItemStack item)
    {
        NbtCompound compound = bag.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
        int fromCount = 0;
        String id = Registries.ITEM.getId(item.getItem()).toString();
        if(compound.contains(id))
        {
            fromCount=compound.getInt(id);
        }
        if(fromCount<200)
        {
            compound.putInt(id,fromCount+item.getCount());
            bag.set(DataComponentTypes.CUSTOM_DATA,NbtComponent.of(compound));
            return true;
        }
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        NbtCompound compound = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
        tooltip.add(Text.translatable("text.reforested.backpack.mode").append(": "+stack.getOrDefault(Reforested.BACKPACK_PICKUP_MODE,false)));
        tooltip.add(Text.translatable("text.reforested.backpack.max").append(": "+maxStorage));
        for(String key: compound.getKeys())
        {
            tooltip.add(Text.translatable(Registries.ITEM.get(Identifier.of(key)).getTranslationKey()).append(" "+compound.getInt(key)));
        }
    }
}
