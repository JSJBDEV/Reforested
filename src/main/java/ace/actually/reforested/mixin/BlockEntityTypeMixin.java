package ace.actually.reforested.mixin;

import ace.actually.reforested.Reforested;
import com.mojang.datafixers.types.Type;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Why we need this mixin:
 * - we want to add new beehives that do different things
 * - we can only add new beehives by telling the beehive block entity that it also likes our custom blocks
 * - this is extendable using the ADD_BEEHIVE list.
 */
@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {

    @Inject(at = @At("HEAD"), method = "create", cancellable = true)
    private static <T extends BlockEntity> void create(String id, BlockEntityType.Builder<T> builder, CallbackInfoReturnable<BlockEntityType<T>> cir) {
        if (id.equals("beehive")) {

            BlockEntityType.Builder<T> nb = (BlockEntityType.Builder<T>) BlockEntityType.Builder.create(BeehiveBlockEntity::new, Reforested.ADD_BEEHIVE.toArray(new Block[]{}));
            Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
            cir.setReturnValue(Registry.register(Registries.BLOCK_ENTITY_TYPE, id, nb.build(type)));
        }


    }
}
