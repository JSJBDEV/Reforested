package ace.actually.reforested;

import ace.actually.reforested.bees.items.BeeAnalyserItem;
import ace.actually.reforested.trees.blocks.WoodBlockBuilder;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
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
	static
	{
		WOOD_BLOCKS.add(new WoodBlockBuilder("larch"));
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		registerOtherBlocks();
		registerOtherItems();
		Registry.register(Registries.ITEM_GROUP, Identifier.of("reforested","tab"),TAB);
		registerOtherThings();

		ITEMS.forEach(item->
		{
			ItemGroupEvents.modifyEntriesEvent(Registries.ITEM_GROUP.getKey(TAB).get()).register(a->a.add(item));
		});
		LOGGER.info("Hello Fabric world!");
	}

	public static final BeehiveBlock APIARY_BLOCK = new BeehiveBlock(AbstractBlock.Settings.copy(Blocks.BEEHIVE));
	private void registerOtherBlocks()
	{
		Registry.register(Registries.BLOCK,Identifier.of("reforested","apiary"),APIARY_BLOCK);

	}


	public static final BeeAnalyserItem BEE_ANALYSER_ITEM = new BeeAnalyserItem(new Item.Settings());
	private void registerOtherItems()
	{

		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","bee_analyser"),BEE_ANALYSER_ITEM));
		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","apiary"),new BlockItem(APIARY_BLOCK,new Item.Settings())));
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