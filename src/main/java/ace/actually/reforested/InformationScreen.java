package ace.actually.reforested;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.nbt.visitor.NbtTextFormatter;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InformationScreen extends Screen {

    public static HashMap<String,String> PAGES = new HashMap<>();
    static
    {
        PAGES.put("Index","information.reforested.index");
        PAGES.put("Trees","information.reforested.trees");
        PAGES.put("Bees","information.reforested.bees");
        PAGES.put("Compartments","information.reforested.compartments");
        PAGES.put("Multi Farm","information.reforested.multi_farm");
        PAGES.put("Peat","information.reforested.peat");
        PAGES.put("Centrifuge","information.reforested.centrifuge");
        PAGES.put("Backpacks","information.reforested.backpacks");
    }
    private static HashMap<String,String[]> REFORMATTED_PAGES = new HashMap<>();

    protected InformationScreen(Text title) {
        super(title);
    }

    private void reformatPages()
    {
        for(String page: PAGES.keySet())
        {
            String pageInformation = PAGES.get(page);
            if(page.equals("Index"))
            {
                StringBuilder v = new StringBuilder();
                for(String ipage:PAGES.keySet())
                {
                    if(!ipage.equals(page))
                    {
                        v.append(ipage).append(" \n ");
                    }
                }

                pageInformation=v.toString();
            }

            String[] split = Text.translatable(pageInformation).getString().split(" ");
            List<String> strings = getStrings(split);
            REFORMATTED_PAGES.put(page,strings.toArray(new String[0]));
        }
    }

    private static @NotNull List<String> getStrings(String[] split) {
        List<String> strings = new ArrayList<>();
        String current = "";
        for(String part: split)
        {
            if(part.equals("\n"))
            {
                strings.add(current);
                current="";
            }
            else if(current.length()+part.length()<30)
            {
                current+=part+" ";
            }
            else
            {
                strings.add(current);
                current=part+" ";
            }
        }
        strings.add(current);
        return strings;
    }

    private TextFieldWidget search;
    private List<ButtonWidget> pageWidgets = new ArrayList<>();

    private int pageOffset = 0;
    @Override
    protected void init() {
        super.init();
        reformatPages();
        //this.addDrawableChild(ButtonWidget.builder(Text.of("Bees"),a->{}).dimensions(3 + width / 4,62,75,20).build());

        search=this.addDrawableChild(new TextFieldWidget(textRenderer,3 + width / 4,42,75,20,Text.of("...")));
        int i = 1;
        for(String page: REFORMATTED_PAGES.keySet())
        {
            pageWidgets.add(this.addDrawableChild(ButtonWidget.builder(Text.of(page),a->
                    {
                        PAGE_OPEN=REFORMATTED_PAGES.get(page);
                        pageOffset=0;
                    } )
                    .dimensions(3 + width / 4,(20*i)+42,75,20).build()));
            i++;
        }
        for (int j = 6; j < pageWidgets.size(); j++) {
            pageWidgets.get(j).visible=false;
        }
        this.addDrawableChild(ButtonWidget.builder(Text.of(">"),a-> pageOffset++)
                .dimensions(153 + width / 4,80+height/2,20,20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.of("<"),a-> pageOffset--)
                .dimensions(133 + width / 4,80+height/2,20,20).build());
    }

    private String[] PAGE_OPEN = new String[0];



    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(textRenderer,"Reforested - Information",2 + width / 4,25, Color.WHITE.getRGB(),true);

        if(pageOffset<0)
        {
            pageOffset=0;
        }

        int line = 0;
        for (int i = pageOffset*14; i < 14*(1+pageOffset); i++) {
            if(i<PAGE_OPEN.length)
            {
                context.drawText(textRenderer,Text.translatable(PAGE_OPEN[i]),81 + width / 4,45+(line*10), Color.WHITE.getRGB(),true);
            }
            line++;
        }
        int i = 1;
        for(ButtonWidget widget: pageWidgets)
        {
            if(widget.getMessage().getString().toLowerCase().contains(search.getText().toLowerCase()))
            {
                if(i<6)
                {
                    widget.visible=true;
                    widget.setY((20*i)+42);
                    i++;
                }

            }
            else if(Text.translatable(PAGES.get(widget.getMessage().getString())).getString().toLowerCase().contains(search.getText().toLowerCase()))
            {
                if(i<6)
                {
                    widget.visible=true;
                    widget.setY((20*i)+42);
                    i++;
                }

            }
            else
            {
                widget.visible=false;
            }
        }


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
