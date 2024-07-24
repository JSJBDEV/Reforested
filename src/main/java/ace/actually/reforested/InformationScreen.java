package ace.actually.reforested;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

public class InformationScreen extends Screen {
    protected InformationScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
        this.addDrawableChild(ButtonWidget.builder(Text.of("Trees"),a->{}).dimensions(2 + width / 4,25,100,20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(textRenderer,"Reforested - Information",2 + width / 4,25, Color.WHITE.getRGB(),true);
    }

    private static final Identifier TEXTURE = Identifier.of("reforested","textures/gui/information.png");
    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        int x = width / 4;
        int y = height / 7;
        context.drawTexture(TEXTURE,x,y,0,0,247,167);
    }
}
