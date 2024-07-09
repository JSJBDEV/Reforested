package ace.actually.reforested;

import ace.actually.reforested.bees.blocks.CentrifugeScreen;
import ace.actually.reforested.bees.blocks.CentrifugeScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(Reforested.CENTRIFUGE_SCREEN_HANDLER, CentrifugeScreen::new);
    }
}
