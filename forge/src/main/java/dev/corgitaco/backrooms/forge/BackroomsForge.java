package dev.corgitaco.backrooms.forge;

import dev.corgitaco.backrooms.Backrooms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

/**
 * Main class for the mod on the Forge platform.
 */
@Mod(Backrooms.MOD_ID)
public class BackroomsForge {
    public BackroomsForge() {
        Backrooms.init();
        ForgeRegistrationService.CACHED.forEach((resourceKey, deferredRegister) -> deferredRegister.register(FMLJavaModLoadingContext.get().getModEventBus()));
    }
}
