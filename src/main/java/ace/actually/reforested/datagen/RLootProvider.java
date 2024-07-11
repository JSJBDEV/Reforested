package ace.actually.reforested.datagen;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.wood_builders.WoodBlockBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RLootProvider extends FabricBlockLootTableProvider {
    public RLootProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        for(WoodBlockBuilder builder: Reforested.WOOD_BLOCKS)
        {
            this.addDrop(builder.LOG,builder.LOG.asItem());
            this.addDrop(builder.WOOD,builder.WOOD.asItem());
            this.addDrop(builder.STRIPPED_LOG,builder.STRIPPED_LOG.asItem());
            this.addDrop(builder.STRIPPED_WOOD,builder.STRIPPED_WOOD.asItem());
            this.addDrop(builder.PLANKS,builder.PLANKS.asItem());
            this.addDrop(builder.SAPLING,builder.SAPLING.asItem());
            this.addDrop(builder.SIGN,builder.SIGN_ITEM);
            this.addDrop(builder.HANGING_SIGN,builder.HANGING_SIGN_ITEM);
            this.addDrop(builder.WALL_SIGN,builder.SIGN_ITEM);
            this.addDrop(builder.WALL_HANGING_SIGN,builder.HANGING_SIGN_ITEM);
            this.addDrop(builder.PRESSURE_PLATE,builder.PRESSURE_PLATE.asItem());
            this.addDrop(builder.SLAB,builder.SLAB.asItem());
            this.addDrop(builder.DOOR,doorDrops(builder.DOOR));
            this.addDrop(builder.TRAPDOOR,builder.TRAPDOOR.asItem());
            this.addDrop(builder.FENCE,builder.FENCE.asItem());
            this.addDrop(builder.FENCE_GATE,builder.FENCE_GATE.asItem());
            this.addDrop(builder.STAIRS,builder.STAIRS.asItem());

            this.addDrop(builder.LEAVES,dropsWithShears(builder.LEAVES.asItem())
                    .pool(LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(0.2f)).with(ItemEntry.builder(Items.STICK)))
                    .pool(LootPool.builder()
                            .conditionally(this.createSilkTouchCondition())
                            .rolls(ConstantLootNumberProvider.create(1.0F))
                            .with(ItemEntry.builder(builder.LEAVES.asItem()))));
        }

        //return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(WITH_SHEARS).with(ItemEntry.builder(drop)))
    }
}
