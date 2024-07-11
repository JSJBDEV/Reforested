package ace.actually.reforested.mixin;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.wood_builders.WoodBlockBuilder;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Why we need this mixin:
 * - boats use enums; we get around this using MM
 * - adding a new item to the enum does not update the entities drop tables
 */
@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {
    @Shadow public abstract BoatEntity.Type getVariant();


    @Inject(at = @At("HEAD"), method = "asItem", cancellable = true)
    private void dropCorrectBoat(CallbackInfoReturnable<Item> cir) {
        for(WoodBlockBuilder builder: Reforested.WOOD_BLOCKS)
        {
            String boat = builder.woodName;
            if(getVariant()== BoatEntity.Type.getType(boat))
            {
                cir.setReturnValue(Registries.ITEM.get(Identifier.of("reforested",boat+"_boat")));

            }
        }

    }
}
