package ace.actually.reforested.bees.blocks;

import ace.actually.reforested.bees.BeeLookups;
import ace.actually.reforested.bees.IReforestedBeehive;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Right now this could be a mixin
 * the apiary is a more useful version of the vanilla beehive, it can deal with custom bee products.
 */
public class ApiaryBlock extends BeehiveBlock {
    public ApiaryBlock(Settings settings) {
        super(settings);

    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = state.get(HONEY_LEVEL);
        boolean bl = false;
        if (i >= 5) {
            if(world.getBlockEntity(pos) instanceof IReforestedBeehive reforestedBeehive)
            {
                if (stack.isOf(Items.SHEARS))
                {
                    world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    float d = BeeLookups.BEE_PRODUCTIVITY_MAP.get(reforestedBeehive.reforested$getQueenBeeType());
                    int total = (int) d;
                    if(world.random.nextFloat()<(d-Math.floor(d)))
                    {
                        total++;
                    }
                    dropStack(world, pos, new ItemStack(BeeLookups.BEE_ITEM_MAP.get(reforestedBeehive.reforested$getQueenBeeType())[0], total));
                    stack.damage(1, player, LivingEntity.getSlotForHand(hand));
                    bl = true;
                    world.emitGameEvent(player, GameEvent.SHEAR, pos);
                }
                else if (stack.isOf(Items.GLASS_BOTTLE))
                {
                    if(BeeLookups.BEE_ITEM_MAP.get(reforestedBeehive.reforested$getQueenBeeType()).length>1)
                    {
                        Item honeyMaybe = BeeLookups.BEE_ITEM_MAP.get(reforestedBeehive.reforested$getQueenBeeType())[1];
                        stack.decrement(1);
                        world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        if (stack.isEmpty()) {
                            player.setStackInHand(hand, new ItemStack(honeyMaybe));
                        } else if (!player.getInventory().insertStack(new ItemStack(honeyMaybe))) {
                            player.dropItem(new ItemStack(honeyMaybe), false);
                        }

                        bl = true;
                        world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);
                    }
                }

            }

            if (!world.isClient() && bl) {

                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            }
        }

        if (bl) {
            if (!CampfireBlock.isLitCampfireInRange(world, pos)) {
                if (this.hasBees(world, pos)) {
                    this.angerNearbyBees(world, pos);
                }

                this.takeHoney(world, state, pos, player, BeehiveBlockEntity.BeeState.EMERGENCY);
            } else {
                this.takeHoney(world, state, pos);
            }

            return ItemActionResult.success(world.isClient);
        } else {
            return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
        }
    }

    private boolean hasBees(World world, BlockPos pos) {
        return world.getBlockEntity(pos) instanceof BeehiveBlockEntity beehiveBlockEntity && !beehiveBlockEntity.hasNoBees();
    }

    private void angerNearbyBees(World world, BlockPos pos) {
        Box box = new Box(pos).expand(8.0, 6.0, 8.0);
        List<BeeEntity> list = world.getNonSpectatingEntities(BeeEntity.class, box);
        if (!list.isEmpty()) {
            List<PlayerEntity> list2 = world.getNonSpectatingEntities(PlayerEntity.class, box);
            if (list2.isEmpty()) {
                return;
            }

            for (BeeEntity beeEntity : list) {
                if (beeEntity.getTarget() == null) {
                    PlayerEntity playerEntity = Util.getRandom(list2, world.random);
                    beeEntity.setTarget(playerEntity);
                }
            }
        }
    }
}
