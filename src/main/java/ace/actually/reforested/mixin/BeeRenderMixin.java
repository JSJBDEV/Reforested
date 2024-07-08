package ace.actually.reforested.mixin;

import ace.actually.reforested.bees.BeeLookups;
import ace.actually.reforested.bees.IReforestedBee;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.BeeEntityRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.util.HashMap;

@Mixin(BeeEntityRenderer.class)
public class BeeRenderMixin {
    public BeeRenderMixin() throws IOException {
    }

    @Inject(at = @At("HEAD"), method = "getTexture(Lnet/minecraft/entity/passive/BeeEntity;)Lnet/minecraft/util/Identifier;", cancellable = true)
    private void init(BeeEntity beeEntity, CallbackInfoReturnable<Identifier> cir) throws IOException {
        if(beeEntity instanceof IReforestedBee bee && bee.reforested$getBeeType()!=null && !bee.reforested$getBeeType().isEmpty() && !bee.reforested$getBeeType().contains("honey"))
        {
            cir.setReturnValue(Identifier.of("reforested","textures/entity/bee/"+bee.reforested$getBeeType()+"_bee.png"));
        }
    }







}
