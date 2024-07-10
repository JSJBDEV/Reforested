package ace.actually.reforested.bees.items;

import ace.actually.reforested.bees.IReforestedBee;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
}
