package dev.corgitaco.backrooms.fabric;

import com.google.auto.service.AutoService;
import com.mojang.serialization.Codec;
import dev.corgitaco.backrooms.Backrooms;
import dev.corgitaco.backrooms.RegistrationService;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

@AutoService(RegistrationService.class)
public class FabricRegistrationService implements RegistrationService {


    @Override
    public <T> Registry<T> createSimpleBuiltin(ResourceKey<Registry<T>> registryKey) {
        MappedRegistry<T> registry = FabricRegistryBuilder.createSimple(registryKey).buildAndRegister();
        return registry;
    }

    @Override
    public <T> Supplier<T> register(Registry<T> registry, String name, Supplier<T> value) {
        Registry.register(registry, Backrooms.createLocation(name), value.get());
        return value;
    }

    @Override
    public <T> void registerDatapackRegistry(ResourceKey<Registry<T>> key, Supplier<Codec<T>> codec) {
        DynamicRegistries.registerSynced(key, codec.get());
    }
}