package ace.actually.reforested.mixin;

import ace.actually.reforested.trees.BoatHelper;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public abstract class ChestBoatEntityMixin {
    @Shadow public abstract BoatEntity.Type getVariant();

    @Inject(at = @At("HEAD"), method = "asItem", cancellable = true)
    private void dropCorrectBoat(CallbackInfoReturnable<Item> cir) {
        for(String boat: BoatHelper.BOATS_TYPES)
        {
            if(getVariant()== BoatEntity.Type.getType(boat))
            {
                cir.setReturnValue(Registries.ITEM.get(Identifier.of("reforested",boat+"_chest_boat")));

            }
        }
    }
}
