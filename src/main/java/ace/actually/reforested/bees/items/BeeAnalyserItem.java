package ace.actually.reforested.bees.items;

import ace.actually.reforested.bees.BeeLookups;
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

/**
 * It analyses bees, giving you information on what they do
 */
public class BeeAnalyserItem extends Item {
    public BeeAnalyserItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        if(!user.getEntityWorld().isClient && entity instanceof IReforestedBee bee)
        {
            user.sendMessage(Text.translatable("text.reforested.typeof").append(" ").append(Text.translatable("bee.reforested."+bee.reforested$getBeeType()).append(Text.translatable("thing.reforested.bee"))));
            user.sendMessage(Text.translatable("text.reforested.bee.temperature").append(" "+BeeLookups.BEE_PREF_TEMP_MAP.get(bee.reforested$getBeeType())));
            user.sendMessage(Text.translatable("text.reforested.bee.humidity").append(" "+BeeLookups.BEE_LIKES_HUMIDITY_MAP.get(bee.reforested$getBeeType())));
            user.sendMessage(Text.translatable("text.reforested.bee.natural").append(" "+BeeLookups.NATURAL_BEE_TYPES.contains(bee.reforested$getBeeType())));
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
