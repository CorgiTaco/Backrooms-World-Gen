package dev.corgitaco.backrooms;

import com.mojang.logging.LogUtils;
import dev.corgitaco.backrooms.core.BackroomChunkGenerators;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class Backrooms {

    public static final String MOD_ID = "backrooms";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        BackroomChunkGenerators.init();

    }

    public static ResourceLocation createLocation(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
