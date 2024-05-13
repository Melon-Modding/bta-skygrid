package watermelonmojito.skygrid;

import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;

public class ChunkGeneratorSkygrid extends ChunkGenerator {
	public ChunkGeneratorSkygrid(World world) {
		super(world, new ChunkDecoratorSkygrid(world));
	}

	protected ChunkGeneratorResult doBlockGeneration(Chunk chunk) {
		return new ChunkGeneratorResult();
	}
}
