package ace.actually.reforested.mixin;

import ace.actually.reforested.bees.BeeLookups;
import ace.actually.reforested.bees.IReforestedBeehive;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * This is the equivilant of th Apiary class right now, it is not actually loaded however
 * I'm not entirely sure I want to overwrite vanilla behaviour just yet.
 */
@Mixin(BeehiveBlock.class)
public abstract class BeehiveMixin extends BlockWithEntity {


    @Shadow @Final public static IntProperty HONEY_LEVEL;

    @Shadow public abstract void takeHoney(World world, BlockState state, BlockPos pos, @Nullable PlayerEntity player, BeehiveBlockEntity.BeeState beeState);

    @Shadow public abstract void takeHoney(World world, BlockState state, BlockPos pos);

    @Shadow protected abstract boolean hasBees(World world, BlockPos pos);

    @Shadow protected abstract void angerNearbyBees(World world, BlockPos pos);

    protected BeehiveMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "onUseWithItem",cancellable = true)
    private void useItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ItemActionResult> cir) {
        int i = state.get(HONEY_LEVEL);
        boolean bl = false;
        if (i >= 5) {
            if(world.getBlockEntity(pos) instanceof IReforestedBeehive reforestedBeehive)
            {
                String t = reforestedBeehive.reforested$getQueenBeeType();
                if(BeeLookups.dislikesPosition(t, world, pos))
                {
                    cir.setReturnValue(ItemActionResult.FAIL);
                }

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
            cir.setReturnValue(ItemActionResult.success(world.isClient));
        } else {
            cir.setReturnValue(ItemActionResult.FAIL);
        }
    }
}



