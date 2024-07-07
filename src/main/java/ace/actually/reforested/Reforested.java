package ace.actually.reforested;

import ace.actually.reforested.bees.items.BeeAnalyserItem;
import ace.actually.reforested.trees.blocks.WoodBlockBuilder;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

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
		registerOtherItems();
		Registry.register(Registries.ITEM_GROUP, Identifier.of("reforested","tab"),TAB);
		ITEMS.forEach(item->
		{
			ItemGroupEvents.modifyEntriesEvent(Registries.ITEM_GROUP.getKey(TAB).get()).register(a->a.add(item));
		});
		LOGGER.info("Hello Fabric world!");
	}

	public static final BeeAnalyserItem BEE_ANALYSER_ITEM = new BeeAnalyserItem(new Item.Settings());
	private void registerOtherItems()
	{
		ITEMS.add(Registry.register(Registries.ITEM,Identifier.of("reforested","bee_analyser"),BEE_ANALYSER_ITEM));
	}
}