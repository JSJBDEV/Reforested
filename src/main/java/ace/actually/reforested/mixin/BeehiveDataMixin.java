package ace.actually.reforested.mixin;

import ace.actually.reforested.bees.BeeLookups;
import ace.actually.reforested.bees.IReforestedBee;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeehiveBlockEntity.BeeData.class)
public abstract class BeehiveDataMixin {

    @Shadow public abstract boolean equals(Object par1);

    @Inject(at = @At("HEAD"), method = "of", cancellable = true)
    private static void of(Entity entity, CallbackInfoReturnable<BeehiveBlockEntity.BeeData> cir) {
        if(entity instanceof IReforestedBee bee)
        {
            NbtCompound nbtCompound = new NbtCompound();
            entity.saveNbt(nbtCompound);
            BeehiveBlockEntity.IRRELEVANT_BEE_NBT_KEYS.forEach(nbtCompound::remove);
            boolean bl = nbtCompound.getBoolean("HasNectar");
            int v = BeeLookups.BEE_HIVE_TICKS_MAP.get(bee.reforested$getBeeType());
            cir.setReturnValue(new BeehiveBlockEntity.BeeData(NbtComponent.of(nbtCompound), 0, bl ? (v*4) : v));
        }
    }
}
