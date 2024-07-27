package ace.actually.reforested.mixin;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.bees.BeeLookups;
import ace.actually.reforested.bees.IReforestedBee;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.UUID;

/**
 * Why we need this mixin:
 * - Bee types are the centre point of Reforested's bee module.
 * - we want to store the bees type on the bee
 * - we want to give bees that don't have bee types some bee types
 * TODO: does this cause compatibility issues?
 */
@Mixin(BeeEntity.class)
public abstract class BeeMixin extends AnimalEntity implements IReforestedBee {

    @Shadow public abstract boolean isBreedingItem(ItemStack stack);

    @Unique
    private static final TrackedData<String> reforested$BEE_TYPE
            = DataTracker.registerData(BeeEntity.class
            , TrackedDataHandlerRegistry.STRING);

    @Override
    public String reforested$getBeeType() {
        return this.dataTracker.get(reforested$BEE_TYPE);
    }

    @Override
    public void reforested$setBeeType(String s) {
        this.dataTracker.set(reforested$BEE_TYPE, s);
        markEffectsDirty();
    }

    protected BeeMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        reforested$setBeeType(nbt.getString("bee_type"));

    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putString("bee_type", reforested$getBeeType());

    }



    @Inject(method = "initDataTracker", at = @At("TAIL"))
    protected void initDataTracker(DataTracker.Builder builder,CallbackInfo ci) {
        //this builder doesn't ever seem to store an initial data...
        builder.add(reforested$BEE_TYPE, BeeLookups.NATURAL_BEE_TYPES.get(Random.create().nextInt(BeeLookups.NATURAL_BEE_TYPES.size())));
    }
    @Inject(method = "tick", at = @At("TAIL"))
    protected void tick(CallbackInfo ci) {
       if(!getEntityWorld().isClient && (reforested$getBeeType()==null || reforested$getBeeType().isEmpty()))
       {
           String v = BeeLookups.NATURAL_BEE_TYPES.get(Random.create().nextInt(BeeLookups.NATURAL_BEE_TYPES.size()));

           System.out.println(v);
           reforested$setBeeType(v);
           markEffectsDirty();
       }

    }

    @Inject(method = "setAngryAt", at = @At("HEAD"), cancellable = true)
    public void angery(UUID angryAt, CallbackInfo ci) {
        if(getEntityWorld() instanceof ServerWorld serverWorld)
        {
            if(serverWorld.getEntity(angryAt) instanceof PlayerEntity player)
            {
                if(player.getEquippedStack(EquipmentSlot.HEAD).isOf(Reforested.APIARISTS_HAT))
                {
                    if(player.getEquippedStack(EquipmentSlot.CHEST).isOf(Reforested.APIARISTS_JACKET))
                    {
                        if(player.getEquippedStack(EquipmentSlot.LEGS).isOf(Reforested.APIARISTS_PANTS))
                        {
                            if(player.getEquippedStack(EquipmentSlot.FEET).isOf(Reforested.APIARISTS_SHOES))
                            {
                                ci.cancel();
                            }
                        }
                    }
                }
            }
        }
    }






}
