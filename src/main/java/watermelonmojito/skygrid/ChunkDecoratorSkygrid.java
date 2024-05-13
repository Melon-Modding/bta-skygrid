package watermelonmojito.skygrid;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFlower;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.gamerule.GameRules;
import net.minecraft.core.item.block.ItemBlockPainted;
import net.minecraft.core.item.block.ItemBlockSlabPainted;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.chunk.ChunkSection;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import watermelonmojito.skygrid.mixins.ItemBlockPaintedAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ChunkDecoratorSkygrid implements ChunkDecorator {

	public List<Integer> blockIDs;
	public World world;


	public ChunkDecoratorSkygrid(World world) {
		this.world = world;

		blockIDs = new ArrayList<>();



		world.getLevelData().getGameRules().setValue(GameRules.ALLOW_SPRINTING, true);

		for(Block block : Block.blocksList){
			if(block != null && !block.hasTag(BlockTags.NOT_IN_CREATIVE_MENU)){
				blockIDs.add(block.id);
			}
		}
		blockIDs.remove(Block.ladderOak.id);
		blockIDs.remove(Block.bedrock.id);
		blockIDs.add(Block.fluidLavaStill.id);
		blockIDs.add(Block.fluidWaterStill.id);
		blockIDs.add(Block.sugarcane.id);
		blockIDs.add(Block.repeaterIdle.id);
		blockIDs.add(Block.wireRedstone.id);
	}


	public void decorate(Chunk chunk) {
		Random rand = new Random(world.getRandomSeed());
		long l1 = (rand.nextLong() / 2L) * 2L + 1L;
		long l2 = (rand.nextLong() / 2L) * 2L + 1L;
		rand.setSeed((long) chunk.xPosition * l1 + (long) chunk.zPosition * l2 ^ world.getRandomSeed());


        for(int x=0; x<Chunk.CHUNK_SIZE_X; x+=4){
			for(int y=0; y<Chunk.CHUNK_SECTIONS * ChunkSection.SECTION_SIZE_Y; y+=4){
				for(int z=0; z<Chunk.CHUNK_SIZE_Z; z+=4){
					int id = blockIDs.get(rand.nextInt(blockIDs.size()));
					Block block = Block.blocksList[id];
					if(block.canPlaceBlockAt(world, x+chunk.xPosition*Chunk.CHUNK_SIZE_X, y, z+chunk.zPosition*Chunk.CHUNK_SIZE_Z)){
						chunk.setBlockIDRaw(x, y, z, id);
						if(block.asItem() instanceof ItemBlockPainted) {
							chunk.setBlockMetadata(x, y, z, rand.nextInt(16) << (((ItemBlockPaintedAccessor)block.asItem()).getUpperMetadata() ? 4 : 0));
							continue;
						}
						if(block.asItem() instanceof ItemBlockSlabPainted) {
							chunk.setBlockMetadata(x, y, z, rand.nextInt(16) << 4);
							continue;
						}
						continue;
					}
					if(block instanceof BlockFlower) {
						chunk.setBlockIDRaw(x, y, z, Block.grass.id);
						chunk.setBlockIDRaw(x, y+1, z, id);
					} else if (block == Block.cactus) {
						chunk.setBlockIDRaw(x, y, z, Block.mudBaked.id);
						chunk.setBlockIDRaw(x, y+1, z, id);
					} else if (block == Block.leverCobbleStone) {
						chunk.setBlockIDRaw(x, y, z, Block.stonePolished.id);
						chunk.setBlockIDWithMetadata(x, y+1, z, id, 6);
					} else if (block == Block.buttonStone) {
						chunk.setBlockIDRaw(x, y, z, Block.stonePolished.id);
						chunk.setBlockIDWithMetadata(x, y+1, z, id, 6);
					} else if (block == Block.algae) {
						chunk.setBlockIDRaw(x, y, z, Block.fluidWaterStill.id);
						chunk.setBlockIDRaw(x, y+1, z, id);
					} else if (block == Block.wireRedstone) {
						chunk.setBlockIDRaw(x, y, z, Block.stonePolished.id);
						chunk.setBlockIDRaw(x, y+1, z, id);
					} else if (block == Block.sugarcane) {
						chunk.setBlockIDRaw(x, y, z, Block.sand.id);
						chunk.setBlockIDRaw(x, y+1, z, id);
					} else if (block == Block.repeaterIdle) {
						chunk.setBlockIDRaw(x, y, z, Block.stonePolished.id);
						chunk.setBlockIDRaw(x, y + 1, z, id);
					}

					else{
						chunk.setBlockIDRaw(x, y, z, Block.glass.id);
						chunk.setBlockIDRaw(x, y+1, z, id);
					}

				}
			}
		}
		//Spawn Platform
		int offsetX = chunk.xPosition*16;
		int offsetZ = chunk.zPosition*16;
		int platformBlock = Block.grass.id;
		if(chunk.xPosition == 0 && chunk.zPosition == 0 || chunk.xPosition == -1 && chunk.zPosition == 0 || chunk.xPosition == 0 && chunk.zPosition == -1 || chunk.xPosition == -1 && chunk.zPosition == -1){
			chunk.setBlockIDRaw(-1-offsetX, 124, 0-offsetZ, platformBlock);
			chunk.setBlockIDRaw(-1-offsetX, 124, -1-offsetZ, platformBlock);
			chunk.setBlockIDRaw(-1-offsetX, 124, 1-offsetZ, platformBlock);
			chunk.setBlockIDRaw(0-offsetX, 124, 0-offsetZ,  platformBlock);
			chunk.setBlockIDRaw(0-offsetX, 124, -1-offsetZ, platformBlock);
			chunk.setBlockIDRaw(0-offsetX, 124, 1-offsetZ,  platformBlock);
			chunk.setBlockIDRaw(1-offsetX, 124, 0-offsetZ,  platformBlock);
			chunk.setBlockIDRaw(1-offsetX, 124, -1-offsetZ, platformBlock);
			chunk.setBlockIDRaw(1-offsetX, 124, 1-offsetZ,  platformBlock);
		}
	}
}
