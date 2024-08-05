package dev.corgitaco.backrooms.core;

import com.mojang.serialization.Codec;
import dev.corgitaco.backrooms.RegistrationService;
import dev.corgitaco.backrooms.level.gen.BackroomsChunkGenerator;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.chunk.ChunkGenerator;

import java.util.function.Supplier;

public class BackroomChunkGenerators {


    public static final Supplier<Codec<BackroomsChunkGenerator>> BACKROOMS_CODEC = register("backrooms", () -> BackroomsChunkGenerator.CODEC);

    public static <CG extends ChunkGenerator, C extends Codec<CG>> Supplier<C> register(String path, Supplier<C> codec) {
        return RegistrationService.INSTANCE.register((Registry<C>) BuiltInRegistries.CHUNK_GENERATOR, path, codec);
    }

    public static void init() {
    }
}
