package dev.corgitaco.backrooms;

import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public interface RegistrationService {

    RegistrationService INSTANCE = Util.make(() -> {
        final var loader = ServiceLoader.load(RegistrationService.class);
        final var it = loader.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("No Registration Service was found on the classpath!");
        } else {
            final RegistrationService factory = it.next();
            if (it.hasNext()) {
                throw new RuntimeException("More than one Registration Service was found on the classpath!");
            }
            return factory;
        }
    });


    <T> Registry<T> createSimpleBuiltin(ResourceKey<Registry<T>> registryKey);


    <T> Supplier<T> register(Registry<T> registry, String name, Supplier<T> value);


    <T> void registerDatapackRegistry(ResourceKey<Registry<T>> key, Supplier<Codec<T>> codec);
}