package ace.actually.reforested.trees.blocks;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.BoatHelper;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

import static ace.actually.reforested.datagen.RRecipeProvider.has;

public class WoodBlockBuilder {
    public String woodName;
    public MapColor topColor;
    public MapColor sideColor;
    public SaplingGenerator saplingGenerator = SaplingGenerator.OAK;
    public BlockSoundGroup woodSoundGroup = BlockSoundGroup.WOOD;
    public BlockSoundGroup leavesSoundGroup = BlockSoundGroup.GRASS;
    public BlockSetType blockSetType = BlockSetType.OAK;
    public WoodType woodType = WoodType.OAK;
    public Block LOG;
    public Block STRIPPED_LOG;
    public Block WOOD;
    public Block STRIPPED_WOOD;
    public Block LEAVES;
    public Block PLANKS;
    public Block SAPLINGS;
    public Block SLAB;
    public Block STAIRS;
    public Block PRESSURE_PLATE;
    public Block BUTTON;
    public Block FENCE;
    public Block FENCE_GATE;
    public Block DOOR;
    public Block TRAPDOOR;
    public Block SIGN;
    public Block WALL_SIGN;
    public Block HANGING_SIGN;
    public Block WALL_HANGING_SIGN;

    public Item SIGN_ITEM;

    public Item HANGING_SIGN_ITEM;
    public BlockFamily FAMILY;

    public TagKey<Item> LOGS_ITEMS_TAG = TagKey.of(Registries.ITEM.getKey(), Identifier.of("reforested",woodName+"_logs"));
    public TagKey<Block> LOGS_BLOCKS_TAG = TagKey.of(Registries.BLOCK.getKey(), Identifier.of("reforested",woodName+"_logs"));



    public WoodBlockBuilder(String name)
    {
        this.woodName=name;


        LOG = registerWithItem( Identifier.of("reforested",woodName+"_log"),
                Blocks.createLogBlock(topColor,sideColor,woodSoundGroup));
        STRIPPED_LOG =registerWithItem( Identifier.of("reforested","stripped_"+woodName+"_log"),
                Blocks.createLogBlock(topColor,sideColor,woodSoundGroup));
        WOOD = registerWithItem( Identifier.of("reforested",woodName+"_wood"),
                new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD)));
        STRIPPED_WOOD = registerWithItem( Identifier.of("reforested","stripped_"+woodName+"_wood"),
                new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD)));
        LEAVES = registerWithItem(Identifier.of("reforested",woodName+"_leaves"),
                Blocks.createLeavesBlock(leavesSoundGroup));
        PLANKS = registerWithItem( Identifier.of("reforested",woodName+"_planks"),
                new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
        SAPLINGS = registerWithItem(Identifier.of("reforested",woodName+"_sapling"),
                new SaplingBlock(saplingGenerator,AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)));
        TRAPDOOR = registerWithItem(Identifier.of("reforested",woodName+"_trapdoor"),new TrapdoorBlock(blockSetType,AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR)));
        SLAB = registerWithItem( Identifier.of("reforested",woodName+"_slab"),
                new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB)));
        STAIRS = registerWithItem( Identifier.of("reforested",woodName+"_stairs"),
                new StairsBlock(PLANKS.getDefaultState(),AbstractBlock.Settings.copy(Blocks.OAK_WOOD)));
        PRESSURE_PLATE = registerWithItem( Identifier.of("reforested",woodName+"_pressure_plate"),
                new PressurePlateBlock(blockSetType,AbstractBlock.Settings.copy(Blocks.OAK_WOOD)));
        BUTTON = registerWithItem( Identifier.of("reforested",woodName+"_button"),
                Blocks.createWoodenButtonBlock(blockSetType));
        FENCE = registerWithItem( Identifier.of("reforested",woodName+"_fence"),
                new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)));
        FENCE_GATE = registerWithItem( Identifier.of("reforested",woodName+"_fence_gate"),
                new FenceGateBlock(woodType,AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE)));
        DOOR = registerWithItem( Identifier.of("reforested",woodName+"_door"),
                new DoorBlock(blockSetType,AbstractBlock.Settings.copy(Blocks.OAK_DOOR)));

        SIGN = Registry.register(Registries.BLOCK,Identifier.of("reforested",woodName+"_sign"),
                new SignBlock(woodType,AbstractBlock.Settings.copy(Blocks.OAK_SIGN)));
        WALL_SIGN = Registry.register(Registries.BLOCK,Identifier.of("reforested",woodName+"_wall_sign"),
                new WallSignBlock(woodType,AbstractBlock.Settings.copy(Blocks.OAK_SIGN)));
        HANGING_SIGN = Registry.register(Registries.BLOCK,Identifier.of("reforested",woodName+"_hanging_sign"),
                new HangingSignBlock(woodType,AbstractBlock.Settings.copy(Blocks.OAK_SIGN)));
        WALL_HANGING_SIGN = Registry.register(Registries.BLOCK,Identifier.of("reforested",woodName+"_wall_hanging_sign"),
                new WallHangingSignBlock(woodType,AbstractBlock.Settings.copy(Blocks.OAK_SIGN)));

        SIGN_ITEM = Registry.register(Registries.ITEM,Identifier.of("reforested",woodName+"_sign"),
                new SignItem(new Item.Settings(),SIGN,WALL_SIGN));
        HANGING_SIGN_ITEM = Registry.register(Registries.ITEM,Identifier.of("reforested",woodName+"_hanging_sign"),
                new HangingSignItem(HANGING_SIGN,WALL_HANGING_SIGN,new Item.Settings()));




        FAMILY = BlockFamilies.register(PLANKS)
                .button(BUTTON)
                .fence(FENCE)
                .fenceGate(FENCE_GATE)
                .door(DOOR)
                .slab(SLAB)
                .pressurePlate(PRESSURE_PLATE)
                .trapdoor(TRAPDOOR)
                .sign(SIGN,WALL_SIGN)
                .stairs(STAIRS)
                .group("wooden")
                .unlockCriterionName("has_planks")
                .build();

        BoatHelper.BOATS_TYPES.add(name);
    }


    private Block registerWithItem(Identifier id, Block block)
    {
        Registry.register(Registries.BLOCK,id,block);

        Reforested.ITEMS.add(Registry.register(Registries.ITEM,id,new BlockItem(block,new Item.Settings())));

        return block;
    }

    //Big up to TechTastic who saved me approximately 1 billion hours by doing a lot of this first.
    public void buildRecipes(RecipeExporter exporter)
    {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, PLANKS)
                .input(LOGS_ITEMS_TAG)
                .criterion("criteria", has(LOG))
                .offerTo(exporter,Identifier.of("reforested","craft_"+ PLANKS.getName().toString()));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, WOOD)
                .pattern("LL")
                .pattern("LL")
                .input('L',LOG)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("Reforested", "craft_"+ WOOD.getName().toString()));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, STRIPPED_WOOD)
                .pattern("LL")
                .pattern("LL")
                .input('L', STRIPPED_LOG)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("Reforested", "craft_"+ STRIPPED_WOOD.getName().toString()));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, SLAB, 6)
                .pattern("PPP")
                .input('P', PLANKS)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("Reforested", "craft_"+ SLAB.getName().toString()));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, STAIRS, 4)
                .pattern("P  ")
                .pattern("PP ")
                .pattern("PPP")
                .input('P', PLANKS)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("Reforested", "craft_"+ STAIRS.getName().toString()));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, PRESSURE_PLATE)
                .pattern("PP")
                .input('P', PLANKS)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("Reforested", "craft_"+ PRESSURE_PLATE.getName().toString()));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BUTTON)
                .input(PLANKS)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("Reforested", "craft_"+ BUTTON.getName().toString()));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, FENCE, 3)
                .pattern("PSP")
                .pattern("PSP")
                .input('P', PLANKS)
                .input('S', Items.STICK)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("Reforested", "craft_"+ FENCE.getName().toString()));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, FENCE_GATE)
                .pattern("SPS")
                .pattern("SPS")
                .input('P', PLANKS)
                .input('S', Items.STICK)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("Reforested", "craft_"+ FENCE_GATE.getName().toString()));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, DOOR, 3)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .input('P', PLANKS)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("Reforested", "craft_"+ DOOR.getName().toString()));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, TRAPDOOR, 2)
                .pattern("PPP")
                .pattern("PPP")
                .input('P', PLANKS)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("Reforested", "craft_"+ TRAPDOOR.getName().toString()));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, SIGN_ITEM, 3)
                .pattern("PPP")
                .pattern("PPP")
                .pattern(" S ")
                .input('P', PLANKS)
                .input('S', Items.STICK)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("Reforested", "craft_"+ SIGN.getName().toString()));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, HANGING_SIGN_ITEM, 6)
                .pattern("C C")
                .pattern("LLL")
                .pattern("LLL")
                .input('L', STRIPPED_LOG)
                .input('C', Blocks.CHAIN)
                .criterion("criteria", has(STRIPPED_LOG))
                .offerTo(exporter, Identifier.of("Reforested", "craft_"+ HANGING_SIGN.getName().toString()));
    }

    public Map<Block,String> produceBlockTranslations()
    {
        String caps = woodName = woodName.substring(0, 1).toUpperCase() + woodName.substring(1);

        HashMap<Block,String> t = new HashMap<>();
        t.put(PLANKS,caps+" Planks");
        t.put(LOG,caps+" Log");
        t.put(STRIPPED_LOG,"Stripped "+caps+" Log");
        t.put(WOOD,caps+" Wood");
        t.put(BUTTON,caps+" Button");
        t.put(PRESSURE_PLATE,caps+" Pressure Plate");
        t.put(STAIRS,caps+" Stairs");
        t.put(SLAB,caps+" Slab");
        t.put(FENCE,caps+" Fence");
        t.put(FENCE_GATE,caps+" Fence Gate");
        t.put(DOOR,caps+" Door");
        t.put(SAPLINGS,caps+" Sapling");
        t.put(SIGN,caps+" Sign");
        t.put(HANGING_SIGN,caps+" Hanging Sign");
        t.put(TRAPDOOR,caps+" Trapdoor");

        return t;
    }
}
