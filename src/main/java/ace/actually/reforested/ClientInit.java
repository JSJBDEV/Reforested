package ace.actually.reforested;

import ace.actually.reforested.bees.blocks.centrifuge.CentrifugeScreen;
import ace.actually.reforested.industry.block.compartment.CompartmentScreen;
import ace.actually.reforested.industry.block.multi_farm.MultiFarmScreen;
import ace.actually.reforested.industry.block.peat_engine.PeatEngineScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ClientInit implements ClientModInitializer {

    public static final KeyBinding CONSIDER_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.reforested.consider",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_BRACKET,
            "category.reforested"
    ));

    @Override
    public void onInitializeClient() {
        HandledScreens.register(Reforested.CENTRIFUGE_SCREEN_HANDLER, CentrifugeScreen::new);
        HandledScreens.register(Reforested.PEAT_ENGINE_SCREEN_HANDLER, PeatEngineScreen::new);
        HandledScreens.register(Reforested.COMPARTMENT_SCREEN_HANDLER, CompartmentScreen::new);
        HandledScreens.register(Reforested.MULTI_FARM_SCREEN_HANDLER, MultiFarmScreen::new);

        for (int i = 0; i < Reforested.WOOD_BLOCKS.size(); i++) {
            BlockRenderLayerMap.INSTANCE.putBlock(Reforested.WOOD_BLOCKS.get(i).LEAVES, RenderLayer.getTranslucent());
            BlockRenderLayerMap.INSTANCE.putBlock(Reforested.WOOD_BLOCKS.get(i).SAPLING, RenderLayer.getCutout());
            BlockRenderLayerMap.INSTANCE.putBlock(Reforested.WOOD_BLOCKS.get(i).POTTED_SAPLING, RenderLayer.getCutout());
            BlockRenderLayerMap.INSTANCE.putBlock(Reforested.WOOD_BLOCKS.get(i).DOOR,RenderLayer.getCutout());
            BlockRenderLayerMap.INSTANCE.putBlock(Reforested.WOOD_BLOCKS.get(i).TRAPDOOR,RenderLayer.getCutout());
        }


        BlockEntityRendererFactories.register(Reforested.MODDED_SIGN_BLOCK_ENTITY, SignBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(Reforested.MODDED_HANGING_SIGN_BLOCK_ENTITY, HangingSignBlockEntityRenderer::new);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (CONSIDER_KEY.wasPressed()) {
                System.out.println("considering...");
                client.setScreen(new InformationScreen(Text.of("title")));
            }
        });
    }
}
