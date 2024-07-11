package ace.actually.reforested.datagen;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.wood_builders.WoodBlockBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * For the uncreative
 * This class allows for all wood/tree related textures to be generated at the same time as the models
 *
 * DEV NOTE: if your going to use this class make sure to change PATH_TO_MOD_INSTANCE!
 * this class should only technically be used in runDatagen, hence the hardcoded values
 */
public class RVibeBasedTexturer {

    public static final String PATH_TO_MOD_INSTANCE = "D:\\Programming\\Fabric\\Reforested\\";
    public static final String BLOCK_PATH = "src\\main\\resources\\assets\\reforested\\textures\\block\\";
    public static final String ITEM_PATH = "src\\main\\resources\\assets\\reforested\\textures\\item\\";
    public static final String MC_PATH = "src\\main\\resources\\assets\\minecraft\\textures\\";


    /**
     * leaving this in, because it's far easier to test wood generation this way
     * @param args you know what these do. I don't
     */
    public static void main(String[] args)
    {
        toyMake();
    }

    /**
     * make a hypothetical larch wood type (but this method specifically doesnt really on anything existing mod side
     */
    private static void toyMake()
    {
        String name = "larch";
        int[] c = new int[]{-2,-44,-117};
        int[] l = new int[]{55,50,-200};
        makeTexture(BLOCK_PATH+"base_planks.png",BLOCK_PATH+name+"_planks.png",c[0],c[1],c[2]);
        makeTexture(BLOCK_PATH+"base_log.png",BLOCK_PATH+name+"_log.png",c[0],c[1],c[2]);
        makeTexture(BLOCK_PATH+"base_log_top.png",BLOCK_PATH+name+"_log_top.png",c[0],c[1],c[2]);
        makeTexture(BLOCK_PATH+"base_door_bottom.png",BLOCK_PATH+name+"_door_bottom.png",c[0],c[1],c[2]);
        makeTexture(BLOCK_PATH+"base_door_top.png",BLOCK_PATH+name+"_door_top.png",c[0],c[1],c[2]);
        makeTexture(BLOCK_PATH+"base_trapdoor.png",BLOCK_PATH+name+"_trapdoor.png",c[0],c[1],c[2]);

        makeTexture(BLOCK_PATH+"stripped_base_log.png",BLOCK_PATH+"stripped_"+name+"_log.png",c[0],c[1],c[2]);
        makeTexture(BLOCK_PATH+"stripped_base_log_top.png",BLOCK_PATH+"stripped_"+name+"_log_top.png",c[0],c[1],c[2]);

        makeTexture(BLOCK_PATH+"base_leaves.png",BLOCK_PATH+name+"_leaves.png",l[0],l[1],l[2]);
        makeTexture(BLOCK_PATH+"base_sapling.png",BLOCK_PATH+name+"_sapling.png",l[0],l[1],l[2]);

        makeTexture(ITEM_PATH+"base_door.png",ITEM_PATH+name+"_door.png",c[0],c[1],c[2]);
        makeTexture(ITEM_PATH+"base_sign.png",ITEM_PATH+name+"_sign.png",c[0],c[1],c[2]);
        makeTexture(ITEM_PATH+"base_hanging_sign.png",ITEM_PATH+name+"_hanging_sign.png",c[0],c[1],c[2]);

        makeTexture(MC_PATH+"entity\\boat\\base.png",MC_PATH+"entity\\boat\\"+name+".png",c[0],c[1],c[2]);
        makeTexture(MC_PATH+"entity\\chest_boat\\base.png",MC_PATH+"entity\\chest_boat\\"+name+".png",c[0],c[1],c[2]);

        makeTexture(MC_PATH+"entity\\signs\\base.png",MC_PATH+"entity\\signs\\"+name+".png",c[0],c[1],c[2]);
        makeTexture(MC_PATH+"entity\\signs\\hanging\\base.png",MC_PATH+"entity\\signs\\hanging\\"+name+".png",c[0],c[1],c[2]);

        makeTexture(MC_PATH+"gui\\hanging_signs\\base.png",MC_PATH+"gui\\hanging_signs\\"+name+".png",c[0],c[1],c[2]);


    }

    public static void make()
    {
        Reforested.LOGGER.info("Generating textures by vibe...");
        for(WoodBlockBuilder builder: Reforested.WOOD_BLOCKS)
        {
            int[] c = builder.PLANKS_COLOUR;
            int[] l = builder.LEAVES_COLOUR;
            if(builder.LOG_COLOUR!=null)
            {
                int[] o = builder.LOG_COLOUR;
                makeBorderedTexture(BLOCK_PATH+"base_log_top.png",BLOCK_PATH+builder.woodName+"_log_top.png",c[0],c[1],c[2],o[0],o[1],o[2]);
                makeTexture(BLOCK_PATH+"base_log.png",BLOCK_PATH+builder.woodName+"_log.png",o[0],o[1],o[2]);
            }
            else
            {
                makeTexture(BLOCK_PATH+"base_log.png",BLOCK_PATH+builder.woodName+"_log.png",c[0],c[1],c[2]);
                makeTexture(BLOCK_PATH+"base_log_top.png",BLOCK_PATH+builder.woodName+"_log_top.png",c[0],c[1],c[2]);
            }

            makeTexture(BLOCK_PATH+"base_planks.png",BLOCK_PATH+builder.woodName+"_planks.png",c[0],c[1],c[2]);


            makeTexture(BLOCK_PATH+"base_door_bottom.png",BLOCK_PATH+builder.woodName+"_door_bottom.png",c[0],c[1],c[2]);
            makeTexture(BLOCK_PATH+"base_door_top.png",BLOCK_PATH+builder.woodName+"_door_top.png",c[0],c[1],c[2]);
            makeTexture(BLOCK_PATH+"base_trapdoor.png",BLOCK_PATH+builder.woodName+"_trapdoor.png",c[0],c[1],c[2]);

            makeTexture(BLOCK_PATH+"stripped_base_log.png",BLOCK_PATH+"stripped_"+builder.woodName+"_log.png",c[0],c[1],c[2]);
            makeTexture(BLOCK_PATH+"stripped_base_log_top.png",BLOCK_PATH+"stripped_"+builder.woodName+"_log_top.png",c[0],c[1],c[2]);

            makeTexture(BLOCK_PATH+"base_leaves.png",BLOCK_PATH+builder.woodName+"_leaves.png",l[0],l[1],l[2]);
            makeTexture(BLOCK_PATH+"base_sapling.png",BLOCK_PATH+builder.woodName+"_sapling.png",l[0],l[1],l[2]);

            makeTexture(ITEM_PATH+"base_door.png",ITEM_PATH+builder.woodName+"_door.png",c[0],c[1],c[2]);
            makeTexture(ITEM_PATH+"base_sign.png",ITEM_PATH+builder.woodName+"_sign.png",c[0],c[1],c[2]);
            makeTexture(ITEM_PATH+"base_hanging_sign.png",ITEM_PATH+builder.woodName+"_hanging_sign.png",c[0],c[1],c[2]);

            makeTexture(ITEM_PATH+"base_boat.png",ITEM_PATH+builder.woodName+"_boat.png",c[0],c[1],c[2]);
            makeTexture(ITEM_PATH+"base_chest_boat.png",ITEM_PATH+builder.woodName+"_chest_boat.png",c[0],c[1],c[2]);

            makeTexture(MC_PATH+"entity\\boat\\base.png",MC_PATH+"entity\\boat\\"+builder.woodName+".png",c[0],c[1],c[2]);
            makeTexture(MC_PATH+"entity\\chest_boat\\base.png",MC_PATH+"entity\\chest_boat\\"+builder.woodName+".png",c[0],c[1],c[2]);

            makeTexture(MC_PATH+"entity\\signs\\base.png",MC_PATH+"entity\\signs\\"+builder.woodName+".png",c[0],c[1],c[2]);
            makeTexture(MC_PATH+"entity\\signs\\hanging\\base.png",MC_PATH+"entity\\signs\\hanging\\"+builder.woodName+".png",c[0],c[1],c[2]);

            makeTexture(MC_PATH+"gui\\hanging_signs\\base.png",MC_PATH+"gui\\hanging_signs\\"+builder.woodName+".png",c[0],c[1],c[2]);
        }

    }

    /**
     * make a texture located at "to" using "base" and then adding rgb values to it.
     * @param base a texture to work from (these are greyscale in my case)
     * @param to the place to put the new texture
     * @param addRed how much red to add to each pixel
     * @param addGreen how much green to add to each pixel
     * @param addBlue how much blue to add to each pixel
     */
    public static void makeTexture(String base, String to, int addRed, int addGreen, int addBlue)
    {
        try {
            BufferedImage original = ImageIO.read(new File(PATH_TO_MOD_INSTANCE +base));
            BufferedImage image= new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
            image.getGraphics().drawImage(original, 0, 0, null);

            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {

                    Color color = new Color(image.getRGB(i,j),true);

                    if(color.getAlpha()>200)
                    {
                        int ra = color.getRed()+addRed;
                        int ga = color.getGreen()+addGreen;
                        int ba = color.getBlue()+addBlue;

                        if(ra>255)
                        {
                            ra=255;
                        }
                        if(ra<0)
                        {
                            ra=0;
                        }
                        if(ga>255)
                        {
                            ga=255;
                        }
                        if(ga<0)
                        {
                            ga=0;
                        }
                        if(ba>255)
                        {
                            ba=255;
                        }
                        if(ba<0)
                        {
                            ba=0;
                        }

                        Color ncolor = new Color(ra,ga,ba,255);

                        image.setRGB(i,j,ncolor.getRGB());
                    }

                }
            }


            ImageIO.write(image,"png",new File(PATH_TO_MOD_INSTANCE+to));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void makeBorderedTexture(String base, String to, int addRed, int addGreen, int addBlue,int addRedBorder, int addBlueBorder, int addGreenBorder)
    {
        try {
            BufferedImage original = ImageIO.read(new File(PATH_TO_MOD_INSTANCE +base));
            BufferedImage image= new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
            image.getGraphics().drawImage(original, 0, 0, null);

            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {

                    Color color = new Color(image.getRGB(i,j),true);

                    if(color.getAlpha()>200)
                    {

                        int ra;
                        int ga;
                        int ba;

                        if(i==0 || j==0 || i==image.getWidth()-1 || j==image.getHeight()-1)
                        {
                            ra = color.getRed()+addRedBorder;
                            ga = color.getGreen()+addGreenBorder;
                            ba = color.getBlue()+addBlueBorder;
                        }
                        else
                        {
                            ra = color.getRed()+addRed;
                            ga = color.getGreen()+addGreen;
                            ba = color.getBlue()+addBlue;
                        }


                        if(ra>255)
                        {
                            ra=255;
                        }
                        if(ra<0)
                        {
                            ra=0;
                        }
                        if(ga>255)
                        {
                            ga=255;
                        }
                        if(ga<0)
                        {
                            ga=0;
                        }
                        if(ba>255)
                        {
                            ba=255;
                        }
                        if(ba<0)
                        {
                            ba=0;
                        }

                        Color ncolor = new Color(ra,ga,ba,255);

                        image.setRGB(i,j,ncolor.getRGB());
                    }

                }
            }


            ImageIO.write(image,"png",new File(PATH_TO_MOD_INSTANCE+to));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
