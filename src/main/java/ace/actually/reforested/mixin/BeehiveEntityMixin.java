package ace.actually.reforested.mixin;


import ace.actually.reforested.bees.IReforestedBee;
import ace.actually.reforested.bees.IReforestedBeehive;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeehiveBlockEntity.class)
public abstract class BeehiveEntityMixin implements IReforestedBeehive {

    @Shadow public abstract void markDirty();

    @Unique
    private String queenBeeType = "none";

    @Inject(at = @At("TAIL"), method = "writeNbt")
    private void write(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, CallbackInfo ci) {
        nbt.putString("queenBeeType", reforested$getQueenBeeType());
    }
    @Inject(at = @At("TAIL"), method = "readNbt")
    private void read(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, CallbackInfo ci) {
        queenBeeType=nbt.getString("queenBeeType");
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
}
