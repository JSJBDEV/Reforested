package ace.actually.reforested;

import ace.actually.reforested.bees.blocks.ApiaryBlock;
import ace.actually.reforested.bees.blocks.ProgressData;
import ace.actually.reforested.bees.blocks.centrifuge.*;
import ace.actually.reforested.bees.blocks.peat_engine.PeatEngineBlock;
import ace.actually.reforested.bees.blocks.peat_engine.PeatEngineBlockEntity;
import ace.actually.reforested.bees.blocks.peat_engine.PeatEngineScreenHandler;
import ace.actually.reforested.bees.items.BeeAnalyserItem;
import ace.actually.reforested.datagen.RLootProvider;
import ace.actually.reforested.trees.blocks.tree_breeding.TreeBreedingRecipes;
import ace.actually.reforested.trees.blocks.tree_breeding.TreeCaneBlock;
import ace.actually.reforested.trees.blocks.wood_builders.PromisedWoodType;
import ace.actually.reforested.trees.blocks.wood_builders.WoodBlockBuilder;
import ace.actually.reforested.trees.blocks.signs.be.ModdedHangingSignBlockEntity;
import ace.actually.reforested.trees.blocks.signs.be.ModdedSignBlockEntity;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.poi.PointOfInterestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.reborn.energy.api.EnergyStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static net.minecraft.world.poi.PointOfInterestTypes.POI_STATES_TO_TYPE;

public class Reforested implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("reforested");


	public static ArrayList<Item> ITEMS = new ArrayList<>();
	public static final ItemGroup TAB = FabricItemGroup.builder()
			.icon(() -> new ItemStack(Items.ACACIA_LEAVES))
			.displayName(Text.translatable("itemgroup.reforested"))
			.build();

	public static final ArrayList<WoodBlockBuilder> WOOD_BLOCKS = new ArrayList<>();



	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		for(PromisedWoodType promisedWoodType: RisingEarly.PROMISED_WOOD_TYPES)
		{
			WOOD_BLOCKS.add(promisedWoodType.create());
		}
		RLootProvider.ADDITIONAL_LEAF_DROPS.put("pistachio", Reforested.PISTACHIO_NUT);
		registerBlockEntities();
		registerOtherBlocks();
		registerOtherItems();
		Registry.register(Registries.ITEM_GROUP, Identifier.of("reforested","tab"),TAB);
		registerOtherThings();
		CentrifugeRecipes.registerRecipes();
		TreeBreedingRecipes.registerRecipes();

		BiomeModifications.create(Identifier.of("reforested","larch_trees")).add(ModificationPhase.ADDITIONS,
				biomeSelectionContext -> biomeSelectionContext.getBiomeKey()== BiomeKeys.TAIGA,
				biomeModificationContext -> biomeModificationContext.getGenerationSettings()
						.addFeature(GenerationStep.Feature.LAKES,RegistryKey.of(RegistryKeys.PLACED_FEATURE,Identifier.of("reforested","larch"))));


		ITEMS.forEach(item->
		{
			ItemGroupEvents.modifyEntriesEvent(Registries.ITEM_GROUP.getKey(TAB).get()).register(a->a.add(item));
		});
		LOGGER.info("Hello Fabric world!");

	}

	public static final ApiaryBlock APIARY_BLOCK = new ApiaryBlock(AbstractBlock.Settings.copy(Blocks.BEEHIVE));
	public static final CentrifugeBlock CENTRIFUGE_BLOCK = new CentrifugeBlock(AbstractBlock.Settings.create());
	public static final TreeCaneBlock TREE_CANE_BLOCK = new TreeCaneBlock(AbstractBlock.Settings.create());
	public static final PeatEngineBlock PEAT_ENGINE_BLOCK = new PeatEngineBlock(AbstractBlock.Settings.create());
	private void registerOtherBlocks()
	{
		Registry.register(Registries.BLOCK,Identifier.of("reforested","apiary"),APIARY_BLOCK);
		Registry.register(Registries.BLOCK,Identifier.of("reforested","centrifuge"),CENTRIFUGE_BLOCK);
		Registry.register(Registries.BLOCK,Identifier.of("reforested","tree_cane"),TREE_CANE_BLOCK);
		Registry.register(Registries.BLOCK,Identifier.of("reforested","peat_engine"),PEAT_ENGINE_BLOCK);
	}

	public static List<Block> ADD_BEEHIVE = new ArrayList<>();
	static
	{
		ADD_BEEHIVE.add(Blocks.BEEHIVE);
		ADD_BEEHIVE.add(Blocks.BEE_NEST);
		ADD_BEEHIVE.add(Reforested.APIARY_BLOCK);


	}


	public static BlockEntityType<CentrifugeBlockEntity> CENTRIFUGE_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			Identifier.of("reforested", "centrifuge_block_entity"),
			FabricBlockEntityTypeBuilder.create(CentrifugeBlockEntity::new, CENTRIFUGE_BLOCK).build()
	);

	public static BlockEntityType<PeatEngineBlockEntity> PEAT_ENGINE_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			Identifier.of("reforested", "peat_engine_block"),
			FabricBlockEntityTypeBuilder.create(PeatEngineBlockEntity::new, PEAT_ENGINE_BLOCK).build()
	);

	public static BlockEntityType<ModdedSignBlockEntity> MODDED_SIGN_BLOCK_ENTITY;
	public static BlockEntityType<ModdedHangingSignBlockEntity> MODDED_HANGING_SIGN_BLOCK_ENTITY;

	private void registerBlockEntities()
	{
		List<Block> allWoodSigns = new ArrayList<>();
		List<Block> allHangingWoodSigns = new ArrayList<>();
		for(WoodBlockBuilder builder: WOOD_BLOCKS)
		{
			allWoodSigns.add(builder.SIGN);
			allWoodSigns.add(builder.WALL_SIGN);
			allHangingWoodSigns.add(builder.HANGING_SIGN);
			allHangingWoodSigns.add(builder.WALL_HANGING_SIGN);
		}

		MODDED_SIGN_BLOCK_ENTITY = Registry.register(
				Registries.BLOCK_ENTITY_TYPE,
				Identifier.of("reforested", "sign_block_entity"),
				BlockEntityType.Builder.create(ModdedSignBlockEntity::new, allWoodSigns.toArray(new Block[]{})).build());



		MODDED_HANGING_SIGN_BLOCK_ENTITY = Registry.register(
				Registries.BLOCK_ENTITY_TYPE,
				Identifier.of("reforested", "hanging_sign_block_entity"),
				BlockEntityType.Builder.create(ModdedHangingSignBlockEntity::new, allHangingWoodSigns.toArray(new Block[]{})).build());

		EnergyStorage.SIDED.registerForBlockEntity((myBlockEntity, direction) -> myBlockEntity.energyStorage, CENTRIFUGE_BLOCK_ENTITY);
		EnergyStorage.SIDED.registerForBlockEntity((myBlockEntity, direction) -> myBlockEntity.energyStorage, PEAT_ENGINE_BLOCK_ENTITY);

	}


	public static final BeeAnalyserItem BEE_ANALYSER_ITEM = new BeeAnalyserItem(new Item.Settings());
	public static final HoneycombItem FIBROUS_COMB = new HoneycombItem(new Item.Settings());
	public static final Item PROPOLIS = new Item(new Item.Settings());
	public static final Item PISTACHIO_NUT = new Item(new Item.Settings());
	public static final Item PEAT = new Item(new Item.Settings());


	private void registerOtherItems()
	{
		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","fibrous_comb"), FIBROUS_COMB));
		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","bee_analyser"),BEE_ANALYSER_ITEM));
		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","propolis"),PROPOLIS));
		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","pistachio_nut"),PISTACHIO_NUT));
		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","peat"),PEAT));

		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","apiary"),new BlockItem(APIARY_BLOCK,new Item.Settings())));
		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","centrifuge"),new BlockItem(CENTRIFUGE_BLOCK,new Item.Settings())));
		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","peat_engine"),new BlockItem(PEAT_ENGINE_BLOCK,new Item.Settings())));
		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","tree_cane"),new BlockItem(TREE_CANE_BLOCK,new Item.Settings())));
	}

	public static ExtendedScreenHandlerType<CentrifugeScreenHandler, ProgressData> CENTRIFUGE_SCREEN_HANDLER =  new ExtendedScreenHandlerType<>(CentrifugeScreenHandler::new, ProgressData.PACKET_CODEC);
	public static ExtendedScreenHandlerType<PeatEngineScreenHandler, ProgressData> PEAT_ENGINE_SCREEN_HANDLER =  new ExtendedScreenHandlerType<>(PeatEngineScreenHandler::new, ProgressData.PACKET_CODEC);

	static
	{
		CENTRIFUGE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,Identifier.of("reforested","centrifuge_screen_handler"),CENTRIFUGE_SCREEN_HANDLER);
		PEAT_ENGINE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,Identifier.of("reforested","peat_engine_screen_handler"),PEAT_ENGINE_SCREEN_HANDLER);

	}


	private void registerOtherThings()
	{
		registerPOI(
				RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE,Identifier.of("reforested","apiary")),
				ImmutableSet.copyOf(APIARY_BLOCK.getStateManager().getStates()), 0, 1);
	}

	private static PointOfInterestType registerPOI(
			RegistryKey<PointOfInterestType> key, Set<BlockState> states, int ticketCount, int searchDistance
	) {
		PointOfInterestType pointOfInterestType = new PointOfInterestType(states, ticketCount, searchDistance);
		Registry.register(Registries.POINT_OF_INTEREST_TYPE, key, pointOfInterestType);
		registerStatesForPOI(Registries.POINT_OF_INTEREST_TYPE.entryOf(key), states);
		return pointOfInterestType;
	}

	private static void registerStatesForPOI(RegistryEntry<PointOfInterestType> poiTypeEntry, Set<BlockState> states) {
		states.forEach(state -> {
			RegistryEntry<PointOfInterestType> registryEntry2 = POI_STATES_TO_TYPE.put(state, poiTypeEntry);
			if (registryEntry2 != null) {
				throw Util.throwOrPause(new IllegalStateException(String.format(Locale.ROOT, "%s is defined in more than one PoI type", state)));
			}
		});
	}

	public static String toTranslation(String name)
	{
		StringBuilder builder = new StringBuilder();
		String[] parts = name.split("_");
		for(String part: parts)
		{
			builder.append(part.substring(0, 1).toUpperCase()).append(part.substring(1)).append(" ");
		}


		String v = builder.toString();
		return v.substring(0,v.length()-1);
	}






}