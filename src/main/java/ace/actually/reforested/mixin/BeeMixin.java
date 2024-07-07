package ace.actually.reforested.mixin;

import ace.actually.reforested.bees.IReforestedBee;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeeEntity.class)
public abstract class BeeMixin extends AnimalEntity implements IReforestedBee {

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

    @Unique
    private static final String[] beeTypes = new String[]{"cool","kind","nice"};

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    protected void initDataTracker(DataTracker.Builder builder,CallbackInfo ci) {
        //this builder doesnt ever seem to store an initial data...
        builder.add(reforested$BEE_TYPE,beeTypes[Random.create().nextInt(beeTypes.length)]);

    }
    @Inject(method = "tick", at = @At("TAIL"))
    protected void tick(CallbackInfo ci) {
       if(!getEntityWorld().isClient && reforested$getBeeType()==null || reforested$getBeeType().isEmpty())
       {
           reforested$setBeeType(beeTypes[Random.create().nextInt(beeTypes.length)]);
           markEffectsDirty();
       }

    }

}
