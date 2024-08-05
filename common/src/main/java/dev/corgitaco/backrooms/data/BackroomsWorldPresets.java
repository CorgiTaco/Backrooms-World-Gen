package dev.corgitaco.backrooms.data;

import dev.corgitaco.backrooms.Backrooms;
import dev.corgitaco.backrooms.level.gen.BackroomsChunkGenerator;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.presets.WorldPreset;

import java.util.Map;

public class BackroomsWorldPresets {
    public static final Map<ResourceKey<WorldPreset>, WorldPresetFactory> WORLD_PRESET_FACTORIES = new Reference2ObjectOpenHashMap<>();

    public static final ResourceKey<WorldPreset> BACKROOMS = register("backrooms", worldPresetBootstapContext -> {

        Map<ResourceKey<LevelStem>, LevelStem> stemMap = new Reference2ObjectOpenHashMap<>();

        HolderGetter<DimensionType> dimensionTypeLookup = worldPresetBootstapContext.lookup(Registries.DIMENSION_TYPE);


        HolderGetter<NoiseGeneratorSettings> noiseGeneratorSettingsHolderGetter = worldPresetBootstapContext.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<Biome> biomesLookup = worldPresetBootstapContext.lookup(Registries.BIOME);

        LevelStem overworldStem = new LevelStem(
                dimensionTypeLookup.getOrThrow(BuiltinDimensionTypes.OVERWORLD),
                new BackroomsChunkGenerator(biomesLookup.getOrThrow(Biomes.THE_VOID))
        );
        stemMap.put(LevelStem.OVERWORLD, overworldStem);


        LevelStem netherStem = new LevelStem(
                dimensionTypeLookup.getOrThrow(BuiltinDimensionTypes.NETHER),
                new NoiseBasedChunkGenerator(
                        MultiNoiseBiomeSource.createFromPreset(worldPresetBootstapContext.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST).getOrThrow(MultiNoiseBiomeSourceParameterLists.NETHER)),
                        noiseGeneratorSettingsHolderGetter.getOrThrow(NoiseGeneratorSettings.NETHER)
                )
        );
        stemMap.put(LevelStem.NETHER, netherStem);


        LevelStem endStem = new LevelStem(
                dimensionTypeLookup.getOrThrow(BuiltinDimensionTypes.END),
                new NoiseBasedChunkGenerator(TheEndBiomeSource.create(biomesLookup),
                        noiseGeneratorSettingsHolderGetter.getOrThrow(NoiseGeneratorSettings.END)
                )
        );
        stemMap.put(LevelStem.END, endStem);

        return new WorldPreset(stemMap);
    });


    private static ResourceKey<WorldPreset> register(String id, WorldPresetFactory factory) {
        ResourceKey<WorldPreset> worldPresetResourceKey = ResourceKey.create(Registries.WORLD_PRESET, Backrooms.createLocation(id));
        WORLD_PRESET_FACTORIES.put(worldPresetResourceKey, factory);
        return worldPresetResourceKey;
    }

    public static void init() {
    }

    @FunctionalInterface
    public interface WorldPresetFactory {

        WorldPreset generate(BootstapContext<WorldPreset> worldPresetBootstapContext);
    }
}
