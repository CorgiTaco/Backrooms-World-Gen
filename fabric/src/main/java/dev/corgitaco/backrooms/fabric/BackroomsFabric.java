package dev.corgitaco.backrooms.fabric;

import dev.corgitaco.backrooms.Backrooms;
import net.fabricmc.api.ModInitializer;

/**
 * This class is the entrypoint for the mod on the Fabric platform.
 */
public class BackroomsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Backrooms.init();
    }
}
