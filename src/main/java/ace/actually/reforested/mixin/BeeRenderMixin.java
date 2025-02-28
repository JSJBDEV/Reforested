package ace.actually.reforested.mixin;

import ace.actually.reforested.bees.IReforestedBee;
import net.minecraft.client.render.entity.BeeEntityRenderer;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.*;

/**
 * Why we need this mixin:
 * - we want our new bee types to look different
 */
@Mixin(BeeEntityRenderer.class)
public class BeeRenderMixin {
    public BeeRenderMixin() {
    }

    @Inject(at = @At("HEAD"), method = "getTexture(Lnet/minecraft/entity/passive/BeeEntity;)Lnet/minecraft/util/Identifier;", cancellable = true)
    private void init(BeeEntity beeEntity, CallbackInfoReturnable<Identifier> cir) throws IOException {
        if(beeEntity instanceof IReforestedBee bee && bee.reforested$getBeeType()!=null && !bee.reforested$getBeeType().isEmpty() && !bee.reforested$getBeeType().contains("honey"))
        {

            if(!bee.reforested$getBeeType().equals("honey"))
            {
                if(beeEntity.hasAngerTime())
                {
                    if(beeEntity.hasNectar())
                    {
                        cir.setReturnValue(Identifier.of("reforested","textures/entity/bee/used/"+bee.reforested$getBeeType()+"_bee_angry_nectar.png"));
                    }
                    else
                    {
                        cir.setReturnValue(Identifier.of("reforested","textures/entity/bee/used/"+bee.reforested$getBeeType()+"_bee_angry.png"));
                    }
                }
                else
                {
                    if(beeEntity.hasNectar())
                    {
                        cir.setReturnValue(Identifier.of("reforested","textures/entity/bee/used/"+bee.reforested$getBeeType()+"_bee_nectar.png"));
                    }
                    else
                    {
                        cir.setReturnValue(Identifier.of("reforested","textures/entity/bee/used/"+bee.reforested$getBeeType()+"_bee.png"));
                    }
                }

            }
        }
    }







}
