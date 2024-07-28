package ace.actually.reforested.industry.block.compartment;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class CompartmentScreen extends HandledScreen<CompartmentScreenHandler> {
    public CompartmentScreen(CompartmentScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);


    }

    private static final Identifier TEXTURE = Identifier.of("reforested","textures/gui/compartment.png");


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        drawBackground(context,delta,mouseX,mouseY);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
        if(!widget.getText().isEmpty() && !widget.getText().equals("..."))
        {
            for (int i = 0; i < this.handler.slots.size(); i++) {
                if(this.handler.slots.get(i) instanceof CompartmentScreenHandler.DisablableSlot disablableSlot)
                {
                    disablableSlot.enableSlot(disablableSlot.getStack().getTranslationKey().contains(widget.getText()));
                }
            }
        }

    }
    private TextFieldWidget widget;

    @Override
    protected void init() {
        super.init();
        // Center the title
        //titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        this.addDrawableChild(ButtonWidget.builder(Text.of("Tab 1"),a->
        {
            widget.setText("...");
            for (int i = 0; i < this.handler.slots.size(); i++) {

                if(this.handler.slots.get(i) instanceof CompartmentScreenHandler.DisablableSlot disablableSlot)
                {
                    disablableSlot.enableSlot(i < 27);
                }
            }

        }).dimensions(80,50,40,20).build());

        for (int tabs = 1; tabs < this.handler.getLabel().getInt("tabCount"); tabs++) {
            int finalTabs = tabs;
            this.addDrawableChild(ButtonWidget.builder(Text.of("Tab "+(tabs+1)), a->
            {
                for (int i = 0; i < this.handler.slots.size(); i++) {

                    if(this.handler.slots.get(i) instanceof CompartmentScreenHandler.DisablableSlot disablableSlot)
                    {
                        //+35  +63
                        disablableSlot.enableSlot(i >= ((finalTabs * 27)) && i < ((finalTabs * 27)+27));
                    }
                }
                widget.setText("...");
            }).dimensions(80,50+(20*tabs),40,20).build());
        }


        widget = this.addDrawableChild(new TextFieldWidget(textRenderer,75+(width - backgroundWidth) / 2,5+(height - backgroundHeight) / 2,90,10,Text.of("...")));
        widget.setFocused(true);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE,x,y,0,0,backgroundWidth,backgroundHeight);
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (client.options.inventoryKey.matchesKey(keyCode, scanCode)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
