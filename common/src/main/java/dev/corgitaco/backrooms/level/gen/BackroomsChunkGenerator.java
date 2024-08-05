package dev.corgitaco.backrooms.level.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class BackroomsChunkGenerator extends ChunkGenerator {
    public static final Codec<BackroomsChunkGenerator> CODEC = RecordCodecBuilder.create(($$0) -> $$0.group(RegistryOps.retrieveElement(Biomes.THE_VOID)).apply($$0, $$0.stable(BackroomsChunkGenerator::new)));

    public BackroomsChunkGenerator(Holder<Biome> biomeSource) {
        super(new FixedBiomeSource(biomeSource));
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(WorldGenRegion worldGenRegion, long l, RandomState randomState, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunkAccess, GenerationStep.Carving carving) {

    }

    @Override
    public void buildSurface(WorldGenRegion worldGenRegion, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess) {

    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion worldGenRegion) {

    }


    @Override
    public int getGenDepth() {
        return 0;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunkAccess) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        ChunkPos pos = chunkAccess.getPos();
        int startY = getMinY();

        int ceilingHeight = 8;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                mutableBlockPos.set(pos.getBlockX(x), startY + 1, pos.getBlockZ(z));
                chunkAccess.setBlockState(mutableBlockPos, Blocks.SANDSTONE.defaultBlockState(), false);
                mutableBlockPos.setY(startY + ceilingHeight);

                BlockState blockState = mutableBlockPos.getX() % 7 == 0 && mutableBlockPos.getZ() % 7 == 0 ? Blocks.OCHRE_FROGLIGHT.defaultBlockState() : Blocks.SANDSTONE.defaultBlockState();


                chunkAccess.setBlockState(mutableBlockPos, blockState, false);

                chunkAccess.setBlockState(mutableBlockPos.move(Direction.UP), Blocks.BEDROCK.defaultBlockState(), false);

                boolean isWall = WallGetter.isWall(mutableBlockPos.getX(), mutableBlockPos.getZ());
                if (isWall) {
                    for (int y = 1; y < ceilingHeight; y++) {
                        mutableBlockPos.setY(startY + 1 + y);
                        BlockState blockState1 = Blocks.BAMBOO_MOSAIC.defaultBlockState();
                        chunkAccess.setBlockState(mutableBlockPos, blockState1, false);
                    }
                }
                chunkAccess.setBlockState(mutableBlockPos.atY(startY), Blocks.BEDROCK.defaultBlockState(), false);

            }
        }

        return CompletableFuture.supplyAsync(() -> chunkAccess, executor);
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinY() {
        return -64;
    }

    @Override
    public int getBaseHeight(int i, int i1, Heightmap.Types types, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        return 0;
    }

    @Override
    public NoiseColumn getBaseColumn(int i, int i1, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        return new NoiseColumn(0, new BlockState[]{});
    }

    @Override
    public void addDebugScreenInfo(List<String> list, RandomState randomState, BlockPos blockPos) {

    }
}