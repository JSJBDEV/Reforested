package ace.actually.reforested.industry.block.multi_farm;

import ace.actually.reforested.Reforested;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class MultiFarmScreen extends HandledScreen<MultiFarmScreenHandler> {
    public MultiFarmScreen(MultiFarmScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);


    }

    private static final Identifier TEXTURE = Identifier.of("reforested","textures/gui/multifarm.png");


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        drawBackground(context,delta,mouseX,mouseY);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);

        NbtCompound v = handler.getLabel();
        BlockPos pos = new BlockPos(v.getInt("x"),v.getInt("y"),v.getInt("z"));
        MultiFarmBlockEntity be = (MultiFarmBlockEntity) MinecraftClient.getInstance().world.getBlockEntity(pos);


        if(this.handler.slots.get(9).getStack().isOf(Reforested.CIRCUIT_BOARD))
        {
            NbtCompound compound = this.handler.slots.get(9).getStack().get(DataComponentTypes.CUSTOM_DATA).copyNbt();
            if(compound.contains("east"))
            {
                context.drawItem(Registries.ITEM.get(Identifier.of(compound.getString("east"))).getDefaultStack(),
                        10+ width / 2,
                        35 + (height - backgroundHeight) / 2);
            }
            if(compound.contains("west"))
            {
                context.drawItem(Registries.ITEM.get(Identifier.of(compound.getString("west"))).getDefaultStack(),
                        -26 + width / 2,
                        35 + (height - backgroundHeight) / 2);
            }
            if(compound.contains("south"))
            {
                context.drawItem(Registries.ITEM.get(Identifier.of(compound.getString("south"))).getDefaultStack(),
                        -8+ width / 2,
                        53 + (height - backgroundHeight) / 2);
            }
            if(compound.contains("north"))
            {
                context.drawItem(Registries.ITEM.get(Identifier.of(compound.getString("north"))).getDefaultStack(),
                        -8+ width / 2,
                        17 + (height - backgroundHeight) / 2);
            }

        }

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
