package ace.actually.reforested;

import ace.actually.reforested.bees.blocks.ApiaryBlock;
import ace.actually.reforested.bees.blocks.centrifuge.CentrifugeBlock;
import ace.actually.reforested.bees.blocks.centrifuge.CentrifugeBlockEntity;
import ace.actually.reforested.bees.blocks.centrifuge.CentrifugeRecipes;
import ace.actually.reforested.bees.blocks.centrifuge.CentrifugeScreenHandler;
import ace.actually.reforested.bees.items.BeeAnalyserItem;
import ace.actually.reforested.trees.BoatHelper;
import ace.actually.reforested.trees.blocks.WoodBlockBuilder;
import ace.actually.reforested.trees.blocks.signs.be.ModdedHangingSignBlockEntity;
import ace.actually.reforested.trees.blocks.signs.be.ModdedSignBlockEntity;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.poi.PointOfInterestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		WOOD_BLOCKS.add(new WoodBlockBuilder("larch"));
		registerBlockEntities();
		registerOtherBlocks();
		registerOtherItems();
		Registry.register(Registries.ITEM_GROUP, Identifier.of("reforested","tab"),TAB);
		registerOtherThings();
		CentrifugeRecipes.registerRecipes();
		ITEMS.forEach(item->
		{
			ItemGroupEvents.modifyEntriesEvent(Registries.ITEM_GROUP.getKey(TAB).get()).register(a->a.add(item));
		});
		LOGGER.info("Hello Fabric world!");
	}

	public static final ApiaryBlock APIARY_BLOCK = new ApiaryBlock(AbstractBlock.Settings.copy(Blocks.BEEHIVE));
	public static final CentrifugeBlock CENTRIFUGE_BLOCK = new CentrifugeBlock(AbstractBlock.Settings.create());
	private void registerOtherBlocks()
	{
		Registry.register(Registries.BLOCK,Identifier.of("reforested","apiary"),APIARY_BLOCK);
		Registry.register(Registries.BLOCK,Identifier.of("reforested","centrifuge"),CENTRIFUGE_BLOCK);

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
	}


	public static final BeeAnalyserItem BEE_ANALYSER_ITEM = new BeeAnalyserItem(new Item.Settings());
	public static final HoneycombItem FIBROUS_COMB = new HoneycombItem(new Item.Settings());
	public static final Item PROPOLIS = new Item(new Item.Settings());


	private void registerOtherItems()
	{
		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","fibrous_comb"), FIBROUS_COMB));
		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","bee_analyser"),BEE_ANALYSER_ITEM));
		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","propolis"),PROPOLIS));

		BoatHelper.registerBoatItems();

		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","apiary"),new BlockItem(APIARY_BLOCK,new Item.Settings())));
	}

	public static ExtendedScreenHandlerType<CentrifugeScreenHandler,CentrifugeBlockEntity.ProgressData> CENTRIFUGE_SCREEN_HANDLER =  new ExtendedScreenHandlerType<>(CentrifugeScreenHandler::new,CentrifugeBlockEntity.ProgressData.PACKET_CODEC);
	static
	{
		CENTRIFUGE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,Identifier.of("reforested","centrifuge_screen_handler"),CENTRIFUGE_SCREEN_HANDLER);
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






}