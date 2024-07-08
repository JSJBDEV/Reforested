package ace.actually.reforested.mixin;

import ace.actually.reforested.bees.BeeLookups;
import ace.actually.reforested.bees.IReforestedBee;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;

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



    @Inject(method = "initDataTracker", at = @At("TAIL"))
    protected void initDataTracker(DataTracker.Builder builder,CallbackInfo ci) {
        //this builder doesnt ever seem to store an initial data...
        builder.add(reforested$BEE_TYPE, BeeLookups.BEE_TYPES.get(Random.create().nextInt(BeeLookups.BEE_TYPES.size())));
    }
    @Inject(method = "tick", at = @At("TAIL"))
    protected void tick(CallbackInfo ci) throws IOException {
       if(!getEntityWorld().isClient && (reforested$getBeeType()==null || reforested$getBeeType().isEmpty()))
       {
           String v = BeeLookups.BEE_TYPES.get(Random.create().nextInt(BeeLookups.BEE_TYPES.size()));

           System.out.println(v);
           reforested$setBeeType(v);
           markEffectsDirty();
       }

    }






}
