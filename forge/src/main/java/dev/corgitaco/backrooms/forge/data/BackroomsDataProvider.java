package dev.corgitaco.backrooms.forge.data;

import dev.corgitaco.backrooms.Backrooms;
import dev.corgitaco.backrooms.data.BackroomsWorldPresets;
import dev.corgitaco.backrooms.forge.data.providers.BackroomsWorldPresetTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Backrooms.MOD_ID)
public class BackroomsDataProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.WORLD_PRESET, context -> {
                BackroomsWorldPresets.WORLD_PRESET_FACTORIES.forEach((key, factory) -> {
                    context.register(key, factory.generate(context));

                });
            });

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        final var gen = event.getGenerator();

        DatapackBuiltinEntriesProvider datapackProvider = new DatapackBuiltinEntriesProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), BUILDER, Set.of(Backrooms.MOD_ID));

        CompletableFuture<HolderLookup.Provider> datapackRegistryProvider = datapackProvider.getRegistryProvider();

        gen.addProvider(event.includeServer(), datapackProvider);
        gen.addProvider(event.includeServer(), new BackroomsWorldPresetTagsProvider(gen.getPackOutput(), datapackRegistryProvider, event.getExistingFileHelper()));
    }
}
