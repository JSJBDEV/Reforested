package ace.actually.reforested.industry.block.peat_engine;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class PeatEngineScreen extends HandledScreen<PeatEngineScreenHandler> {
    public PeatEngineScreen(PeatEngineScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);


    }

    private static final Identifier TEXTURE = Identifier.of("reforested","textures/gui/engine.png");


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        drawBackground(context,delta,mouseX,mouseY);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);

        NbtCompound v = handler.getLabel();
        BlockPos pos = new BlockPos(v.getInt("x"),v.getInt("y"),v.getInt("z"));
        PeatEngineBlockEntity be = (PeatEngineBlockEntity) MinecraftClient.getInstance().world.getBlockEntity(pos);
        if(be.getTicksToComplete()!=-1)
        {
            context.drawText(textRenderer,Text.translatable("text.reforested.engine.burn_time").append(": "+be.getTicksToComplete()),(width/2)-35,height/4+10, Colors.WHITE,true);
        }
        context.drawText(textRenderer,Text.translatable("text.reforested.engine.energy").append(": "+be.energyStorage.amount),(width/2)-35,height/4 +20, Colors.WHITE,true);

    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE,x,y,0,0,backgroundWidth,backgroundHeight);
    }
}
