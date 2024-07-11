package ace.actually.reforested.datagen;

import ace.actually.reforested.Reforested;
import ace.actually.reforested.trees.blocks.wood_builders.WoodBlockBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RRecipeProvider extends FabricRecipeProvider {
    public RRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        for(WoodBlockBuilder builder: Reforested.WOOD_BLOCKS)
        {
            builder.buildRecipes(exporter);
        }
    }

    public static AdvancementCriterion<InventoryChangedCriterion.Conditions> has(ItemConvertible cv)
    {
        return Criteria.INVENTORY_CHANGED.create(InventoryChangedCriterion.Conditions.items(cv).conditions());
    }
}
