package ace.actually.reforested.bees.items;

import ace.actually.reforested.bees.IReforestedBee;
import ace.actually.reforested.datagen.RVibeBasedTexturer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class BeeAnalyserItem extends Item {
    public BeeAnalyserItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        if(!user.getEntityWorld().isClient && entity instanceof IReforestedBee bee)
        {
            user.sendMessage(Text.translatable("text.reforested.typeof").append(" ").append(Text.translatable("bee.reforested."+bee.reforested$getBeeType()).append(Text.translatable("thing.reforested.bee"))));

        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        RVibeBasedTexturer.makeTexture(RVibeBasedTexturer.BLOCK_PATH+"larch_planks.png",RVibeBasedTexturer.BLOCK_PATH+"wenge_planks.png",0,5,0);
        return super.useOnBlock(context);
    }
}
