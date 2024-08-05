package dev.corgitaco.backrooms.forge.data.providers;

import dev.corgitaco.backrooms.Backrooms;
import dev.corgitaco.backrooms.data.BackroomsWorldPresets;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.WorldPresetTags;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BackroomsWorldPresetTagsProvider extends TagsProvider<WorldPreset> {
    public BackroomsWorldPresetTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(packOutput, Registries.WORLD_PRESET, completableFuture, Backrooms.MOD_ID, existingFileHelper);
    }

    protected void addTags(HolderLookup.Provider provider) {
        this.tag(WorldPresetTags.NORMAL).add(BackroomsWorldPresets.BACKROOMS);
    }
}
