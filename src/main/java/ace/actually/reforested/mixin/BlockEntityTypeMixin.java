package ace.actually.reforested.mixin;

import ace.actually.reforested.Reforested;
import com.mojang.datafixers.types.Type;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.sql.Ref;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {


    @Inject(at = @At("HEAD"), method = "create", cancellable = true)
    private static <T extends BlockEntity> void create(String id, BlockEntityType.Builder<T> builder, CallbackInfoReturnable<BlockEntityType<T>> cir) {
        if (id.equals("beehive")) {
            BlockEntityType.Builder<T> nb = (BlockEntityType.Builder<T>) BlockEntityType.Builder.create(BeehiveBlockEntity::new, Blocks.BEE_NEST, Blocks.BEEHIVE, Reforested.APIARY_BLOCK);
            Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
            cir.setReturnValue(Registry.register(Registries.BLOCK_ENTITY_TYPE, id, nb.build(type)));
        }


    }
}
