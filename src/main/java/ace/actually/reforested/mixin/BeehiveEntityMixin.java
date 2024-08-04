package ace.actually.reforested.mixin;


import ace.actually.reforested.Reforested;
import ace.actually.reforested.bees.BeeLookups;
import ace.actually.reforested.bees.IReforestedBee;
import ace.actually.reforested.bees.IReforestedBeehive;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Why we need this mixin:
 * - we want to know what type of comb this hive produces without being too intrusive
 * - we want to check to see if we can simulate bee mutation/breeding
 * - we do both of these things by writing additional data to the beehive block entity
 */
@Mixin(BeehiveBlockEntity.class)
public abstract class BeehiveEntityMixin implements IReforestedBeehive {

    @Shadow public abstract void markDirty();

    @Shadow protected abstract void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup);

    @Unique
    private String queenBeeType = "none";
    @Unique
    private long nextBreedingCheck = -1;

    @Inject(at = @At("TAIL"), method = "writeNbt")
    private void write(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, CallbackInfo ci) {
        nbt.putString("queenBeeType", reforested$getQueenBeeType());
        nbt.putLong("nextBreedingCheck",reforested$nextBreedingCheck());
    }
    @Inject(at = @At("TAIL"), method = "readNbt")
    private void read(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, CallbackInfo ci) {
        queenBeeType=nbt.getString("queenBeeType");
        nextBreedingCheck=nbt.getLong("nextBreedingCheck");
    }


    @Override
    public String reforested$getQueenBeeType() {
        return queenBeeType;
    }

    @Override
    public void reforested$setQueenBeeType(String beeType) {
        queenBeeType=beeType;
        markDirty();
    }

    @Override
    public long reforested$nextBreedingCheck() {
        return nextBreedingCheck;
    }

    @Override
    public void reforested$setNextBreedingCheck(long nextBreedingCheck) {
        this.nextBreedingCheck = nextBreedingCheck;
        markDirty();
    }

    @Inject(at = @At("TAIL"), method = "tryEnterHive")
    private void tryEnterHive(Entity entity, CallbackInfo ci) {
        if(entity instanceof IReforestedBee bee)
        {
            if(reforested$getQueenBeeType().equals("none"))
            {
                reforested$setQueenBeeType(bee.reforested$getBeeType());
            }

        }
    }

    @Inject(method = "releaseBee", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I", shift = At.Shift.AFTER))
    private static void injected(World world, BlockPos pos, BlockState state, BeehiveBlockEntity.BeeData bee, @Nullable List<Entity> entities, BeehiveBlockEntity.BeeState beeState, @Nullable BlockPos flowerPos, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) LocalIntRef localRef) {

        String btype = bee.entityData().copyNbt().getString("bee_type");
        if(BeeLookups.dislikesPosition(btype, world, pos))
        {
            localRef.set(localRef.get()-1);
        }

    }


    @Inject(at = @At("TAIL"), method = "serverTick")
    private static void tick(World world, BlockPos pos, BlockState state, BeehiveBlockEntity blockEntity, CallbackInfo ci) {
        if(blockEntity.getBeeCount()>1 && blockEntity instanceof IReforestedBeehive beehive && state.isOf(Reforested.APIARY_BLOCK))
        {
            if(beehive.reforested$nextBreedingCheck()==-1)
            {
                beehive.reforested$setNextBreedingCheck(world.getTime()+5000); //5000
            }
            if(!blockEntity.isFullOfBees() && world.getTime()>beehive.reforested$nextBreedingCheck())
            {
                List<String> typesInHive = new ArrayList<>();
                for(BeehiveBlockEntity.Bee bee: blockEntity.bees)
                {
                     NbtCompound compound = bee.createData().entityData().copyNbt();
                     if(compound.contains("bee_type"))
                     {
                         typesInHive.add(compound.getString("bee_type"));
                     }
                }
                for(BeeLookups.BreedingInstance instance: BeeLookups.BEE_BREEDING_CHANCES)
                {
                    if(typesInHive.contains(instance.type1()) && typesInHive.contains(instance.type2()))
                    {

                        if(world.random.nextFloat()<instance.chance())
                        {
                            NbtCompound compound = new NbtCompound();
                            compound.putString("id", Registries.ENTITY_TYPE.getId(EntityType.BEE).toString());
                            compound.putString("bee_type",instance.mutation());
                            blockEntity.addBee(new BeehiveBlockEntity.BeeData(NbtComponent.of(compound),2400,2400));
                            typesInHive.add(instance.mutation());
                        }
                        break;
                    }
                }
                beehive.reforested$setQueenBeeType(typesInHive.get(world.random.nextInt(typesInHive.size())));
                beehive.reforested$setNextBreedingCheck(world.getTime()+5000);
            }

        }
    }
}
