package ace.actually.reforested.mixin;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.wood_builders.WoodBlockBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Why we need this mixin:
 * - boats use enums; we get around this using MM
 * - adding a new item to the enum does not update the entities drop tables
 */
@Mixin(ChestBoatEntity.class)
public abstract class ChestBoatEntityMixin extends BoatEntity {


    public ChestBoatEntityMixin(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("entity.reforested."+this.getVariant().getName()+"_chest_boat.name");
    }

    @Inject(at = @At("HEAD"), method = "asItem", cancellable = true)
    private void dropCorrectBoat(CallbackInfoReturnable<Item> cir) {
        for(WoodBlockBuilder builder: Reforested.WOOD_BLOCKS)
        {
            String boat = builder.woodName;
            if(this.getVariant()== BoatEntity.Type.getType(boat))
            {
                cir.setReturnValue(Registries.ITEM.get(Identifier.of("reforested",boat+"_chest_boat")));

            }
        }
    }
}
