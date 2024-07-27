package ace.actually.reforested.trees.blocks.wood_builders;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.signs.ModdedHangingSign;
import ace.actually.reforested.trees.blocks.signs.ModdedHangingWallSign;
import ace.actually.reforested.trees.blocks.signs.ModdedSign;
import ace.actually.reforested.trees.blocks.signs.ModdedWallSign;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static ace.actually.reforested.datagen.RRecipeProvider.has;

/**
 * TREES!
 * want to add a new wood type to the game? it's a lot of effort.
 * unless of course you use my handy dandy generator here (with big thanks to TechTastic)
 * constructing a WoodBlockBuilder will:
 * - register all the wood related blocks, their items, their base translations and their tags
 * - register all the wood related models, the blocks, the items and the boats
 * - register a treeGrower so the sapling can grow into a tree
 * - generate textures using 2 sets of rgb values.
 * - register boat types (using the power of jank)
 * If you want to use this class then use RisingEarly.PROMISED_WOOD_TYPES.add() to do so.
 * make sure that you use this method in a static context (see the main Reforested file)
 * als see the note in PromisedWoodType for context on why we have to do it this way.
 */
public class WoodBlockBuilder {
    public String woodName;
    public MapColor topColor;
    public MapColor sideColor;
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
    public Block SAPLING;
    public Block POTTED_SAPLING;
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

    public Item BOAT;
    public Item CHEST_BOAT;

    public TagKey<Item> LOGS_ITEMS_TAG;
    public TagKey<Block> LOGS_BLOCKS_TAG;

    public int[] PLANKS_COLOUR;
    public int[] LEAVES_COLOUR;


    public SaplingGenerator saplingGenerator;

    public RegistryKey<ConfiguredFeature<?,?>> AS_CONFIGURED_FEATURE;
    public RegistryKey<PlacedFeature> AS_PLACED_FEATURE;
    public String LIKE_TREE;
    public boolean NATURAL;
    public int[] LOG_COLOUR;



    public WoodBlockBuilder(String name, int[] planksColour, int[] leavesColour, int @Nullable [] logColour, String likeTree, boolean natural)
    {
        LOG_COLOUR = logColour;
        NATURAL=natural;
        LIKE_TREE=likeTree;
        PLANKS_COLOUR = planksColour;
        LEAVES_COLOUR = leavesColour;

        this.woodName=name;
        LOGS_ITEMS_TAG = TagKey.of(Registries.ITEM.getKey(), Identifier.of("reforested",woodName+"_logs"));
        LOGS_BLOCKS_TAG = TagKey.of(Registries.BLOCK.getKey(), Identifier.of("reforested",woodName+"_logs"));

        blockSetType=BlockSetType.register(new BlockSetType(name));
        woodType=WoodType.register(new WoodType(name,blockSetType));

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

        BOAT = Registry.register(Registries.ITEM,Identifier.of("reforested",woodName+"_boat"),
                new BoatItem(false, BoatEntity.Type.getType(woodName),new Item.Settings()));

        CHEST_BOAT = Registry.register(Registries.ITEM,Identifier.of("reforested",woodName+"_chest_boat"),
                new BoatItem(true, BoatEntity.Type.getType(woodName),new Item.Settings()));

        AS_CONFIGURED_FEATURE = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of("reforested",woodName));
        AS_PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("reforested",woodName));


        saplingGenerator =new SaplingGenerator(
                woodName,
                Optional.empty(), // Mega Tree
                Optional.of(AS_CONFIGURED_FEATURE), // Tree
                Optional.empty()); // Tree With Flowers

        SAPLING = registerWithItem(Identifier.of("reforested",woodName+"_sapling"),
                new SaplingBlock(saplingGenerator,AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)));

        POTTED_SAPLING = Registry.register(Registries.BLOCK,Identifier.of("reforested",woodName+"_potted_sapling"),
                new FlowerPotBlock(SAPLING,AbstractBlock.Settings.copy(Blocks.POTTED_OAK_SAPLING)));

        TRAPDOOR = registerWithItem(Identifier.of("reforested",woodName+"_trapdoor"),
                new TrapdoorBlock(blockSetType,AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR)));

        SLAB = registerWithItem( Identifier.of("reforested",woodName+"_slab"),
                new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB)));

        PRESSURE_PLATE = registerWithItem( Identifier.of("reforested",woodName+"_pressure_plate"),
                new PressurePlateBlock(blockSetType,AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE)));

        BUTTON = registerWithItem( Identifier.of("reforested",woodName+"_button"),
                Blocks.createWoodenButtonBlock(blockSetType));

        FENCE = registerWithItem( Identifier.of("reforested",woodName+"_fence"),
                new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)));

        FENCE_GATE = registerWithItem( Identifier.of("reforested",woodName+"_fence_gate"),
                new FenceGateBlock(woodType,AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE)));

        DOOR = registerWithItem( Identifier.of("reforested",woodName+"_door"),
                new DoorBlock(blockSetType,AbstractBlock.Settings.copy(Blocks.OAK_DOOR)));

        STAIRS = registerWithItem( Identifier.of("reforested",woodName+"_stairs"),
                new StairsBlock(PLANKS.getDefaultState(),AbstractBlock.Settings.copy(Blocks.OAK_STAIRS)));

        SIGN = Registry.register(Registries.BLOCK,Identifier.of("reforested",woodName+"_sign"),
                new ModdedSign(woodType,AbstractBlock.Settings.copy(Blocks.OAK_SIGN)));

        WALL_SIGN = Registry.register(Registries.BLOCK,Identifier.of("reforested",woodName+"_wall_sign"),
                new ModdedWallSign(woodType,AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN)));

        HANGING_SIGN = Registry.register(Registries.BLOCK,Identifier.of("reforested",woodName+"_hanging_sign"),
                new ModdedHangingSign(woodType,AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN)));

        WALL_HANGING_SIGN = Registry.register(Registries.BLOCK,Identifier.of("reforested",woodName+"_wall_hanging_sign"),
                new ModdedHangingWallSign(woodType,AbstractBlock.Settings.copy(Blocks.OAK_WALL_HANGING_SIGN)));

        SIGN_ITEM = Registry.register(Registries.ITEM,Identifier.of("reforested",woodName+"_sign"),
                new SignItem(new Item.Settings(),SIGN,WALL_SIGN));

        HANGING_SIGN_ITEM = Registry.register(Registries.ITEM,Identifier.of("reforested",woodName+"_hanging_sign"),
                new HangingSignItem(HANGING_SIGN,WALL_HANGING_SIGN,new Item.Settings()));

        Reforested.TREE_ITEMS.add(SIGN_ITEM);
        Reforested.TREE_ITEMS.add(HANGING_SIGN_ITEM);
        Reforested.TREE_ITEMS.add(BOAT);
        Reforested.TREE_ITEMS.add(CHEST_BOAT);
    }

    public BlockFamily createFamily()
    {
       return BlockFamilies.register(PLANKS)
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
    }


    private Block registerWithItem(Identifier id, Block block)
    {
        Registry.register(Registries.BLOCK,id,block);

        Reforested.TREE_ITEMS.add(Registry.register(Registries.ITEM,id,new BlockItem(block,new Item.Settings())));

        return block;
    }

    //Big up to TechTastic who saved me approximately 1 billion hours by doing a lot of this first.
    public void buildRecipes(RecipeExporter exporter)
    {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, PLANKS)
                .input(LOGS_ITEMS_TAG)
                .criterion("criteria", has(LOG))
                .offerTo(exporter,Identifier.of("reforested","craft_"+ PLANKS.getName().getString().toLowerCase().replace(" ","_")));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, CHEST_BOAT)
                .input(BOAT)
                .input(Items.CHEST)
                .criterion("criteria", has(PLANKS))
                .offerTo(exporter,Identifier.of("reforested","craft_"+ CHEST_BOAT.getName().getString().toLowerCase().replace(" ","_")));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, WOOD)
                .pattern("LL")
                .pattern("LL")
                .input('L',LOG)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("reforested", "craft_"+ WOOD.getName().getString().toLowerCase().replace(" ","_")));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, STRIPPED_WOOD)
                .pattern("LL")
                .pattern("LL")
                .input('L', STRIPPED_LOG)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("reforested", "craft_"+ STRIPPED_WOOD.getName().getString().toLowerCase().replace(" ","_")));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, SLAB, 6)
                .pattern("PPP")
                .input('P', PLANKS)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("reforested", "craft_"+ SLAB.getName().getString().toLowerCase().replace(" ","_")));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, STAIRS, 4)
                .pattern("P  ")
                .pattern("PP ")
                .pattern("PPP")
                .input('P', PLANKS)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("reforested", "craft_"+ STAIRS.getName().getString().toLowerCase().replace(" ","_")));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, BOAT, 1)
                .pattern("P P")
                .pattern("PPP")
                .input('P', PLANKS)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("reforested", "craft_"+ BOAT.getName().getString().toLowerCase().replace(" ","_")));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, PRESSURE_PLATE)
                .pattern("PP")
                .input('P', PLANKS)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("reforested", "craft_"+ PRESSURE_PLATE.getName().getString().toLowerCase().replace(" ","_")));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BUTTON)
                .input(PLANKS)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("reforested", "craft_"+ BUTTON.getName().getString().toLowerCase().replace(" ","_")));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, FENCE, 3)
                .pattern("PSP")
                .pattern("PSP")
                .input('P', PLANKS)
                .input('S', Items.STICK)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("reforested", "craft_"+ FENCE.getName().getString().toLowerCase().replace(" ","_")));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, FENCE_GATE)
                .pattern("SPS")
                .pattern("SPS")
                .input('P', PLANKS)
                .input('S', Items.STICK)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("reforested", "craft_"+ FENCE_GATE.getName().getString().toLowerCase().replace(" ","_")));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, DOOR, 3)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .input('P', PLANKS)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("reforested", "craft_"+ DOOR.getName().getString().toLowerCase().replace(" ","_")));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, TRAPDOOR, 2)
                .pattern("PPP")
                .pattern("PPP")
                .input('P', PLANKS)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("reforested", "craft_"+ TRAPDOOR.getName().getString().toLowerCase().replace(" ","_")));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, SIGN_ITEM, 3)
                .pattern("PPP")
                .pattern("PPP")
                .pattern(" S ")
                .input('P', PLANKS)
                .input('S', Items.STICK)
                .criterion("has_planks", has(PLANKS))
                .offerTo(exporter, Identifier.of("reforested", "craft_"+ SIGN.getName().getString().toLowerCase().replace(" ","_")));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, HANGING_SIGN_ITEM, 6)
                .pattern("C C")
                .pattern("LLL")
                .pattern("LLL")
                .input('L', STRIPPED_LOG)
                .input('C', Blocks.CHAIN)
                .criterion("criteria", has(STRIPPED_LOG))
                .offerTo(exporter, Identifier.of("reforested", "craft_"+ HANGING_SIGN.getName().getString().toLowerCase().replace(" ","_")));
    }

    public Map<Block,String> produceBlockTranslations()
    {
        String caps = Reforested.toTranslation(woodName);

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
        t.put(SAPLING,caps+" Sapling");
        t.put(SIGN,caps+" Sign");
        t.put(HANGING_SIGN,caps+" Hanging Sign");
        t.put(TRAPDOOR,caps+" Trapdoor");
        t.put(STRIPPED_WOOD,caps+" Stripped Wood");
        t.put(LEAVES,caps+" Leaves");

        return t;
    }
}
