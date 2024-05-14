package watermelonmojito.skygrid;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockChest;
import net.minecraft.core.block.BlockFlower;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.gamerule.GameRules;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
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
	private final WeightedRandomBag<Object> chestLoot;


	public ChunkDecoratorSkygrid(World world) {
		this.world = world;
		this.chestLoot = new WeightedRandomBag<>();
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.saddle.getDefaultStack()), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.ingotIron.getDefaultStack(), 1, 4), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.foodBread.getDefaultStack()), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.wheat.getDefaultStack(), 1, 4), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.sulphur.getDefaultStack(), 1, 4), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.string.getDefaultStack(), 1, 4), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.bucket.getDefaultStack()), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.foodAppleGold.getDefaultStack()), 1.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.dustRedstone.getDefaultStack(), 1, 4), 50.0);
		for(int i = 0; i < 9; ++i) {
			this.chestLoot.addEntry(new WeightedRandomLootObject(new ItemStack(Item.itemsList[Item.record13.id + i]), 1), 1.0);
		}
		this.chestLoot.addEntry(new WeightedRandomLootObject(Block.saplingCacao.getDefaultStack()), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Block.spongeDry.getDefaultStack(), 1, 4), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.bone.getDefaultStack(), 1, 4), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.foodApple.getDefaultStack()), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.ingotIron.getDefaultStack(), 1, 6), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.ingotGold.getDefaultStack(), 1, 4), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.sulphur.getDefaultStack(), 3, 8), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.diamond.getDefaultStack(), 1, 4), 2.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.foodAppleGold.getDefaultStack()), 1.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.dustRedstone.getDefaultStack(), 1, 4), 100.0);
		for(int i = 0; i < 9; ++i) {
			this.chestLoot.addEntry(new WeightedRandomLootObject(new ItemStack(Item.itemsList[Item.record13.id + i])), 1.0);
		}
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.foodApple.getDefaultStack()), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Block.spongeDry.getDefaultStack(), 1, 4), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.handcannonLoaded.getDefaultStack()), 0.5);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.handcannonUnloaded.getDefaultStack()), 4.5);
		this.chestLoot.addEntry((new WeightedRandomLootObject(Item.armorHelmetChainmail.getDefaultStack())).setRandomMetadata(Item.armorHelmetChainmail.getMaxDamage() / 2, Item.armorHelmetChainmail.getMaxDamage()), 20.0);
		this.chestLoot.addEntry((new WeightedRandomLootObject(Item.armorChestplateChainmail.getDefaultStack())).setRandomMetadata(Item.armorChestplateChainmail.getMaxDamage() / 2, Item.armorChestplateChainmail.getMaxDamage()), 20.0);
		this.chestLoot.addEntry((new WeightedRandomLootObject(Item.armorLeggingsChainmail.getDefaultStack())).setRandomMetadata(Item.armorLeggingsChainmail.getMaxDamage() / 2, Item.armorLeggingsChainmail.getMaxDamage()), 20.0);
		this.chestLoot.addEntry((new WeightedRandomLootObject(Item.armorBootsChainmail.getDefaultStack())).setRandomMetadata(Item.armorBootsChainmail.getMaxDamage() / 2, Item.armorBootsChainmail.getMaxDamage()), 20.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.ingotSteelCrude.getDefaultStack()), 10.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(null), 600.0);

		blockIDs = new ArrayList<>();

		world.getLevelData().getGameRules().setValue(GameRules.ALLOW_SPRINTING, true);

		for(Block block : Block.blocksList){
			if(block != null && !block.hasTag(BlockTags.NOT_IN_CREATIVE_MENU)){
				blockIDs.add(block.id);
			}
		}
		blockIDs.remove(Integer.valueOf(Block.ladderOak.id));
		blockIDs.remove(Integer.valueOf(Block.bedrock.id));
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
						if(block instanceof BlockChest){
							for(int i=0; i<8; i++){
								ItemStack itemstack = this.pickCheckLootItem(rand);
								if (itemstack != null) {
									((TileEntityChest)chunk.getTileEntity(x,y,z)).setInventorySlotContents(rand.nextInt(((TileEntityChest)chunk.getTileEntity(x,y,z)).getSizeInventory()), itemstack);
								}
							}
						}
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
			chunk.setBlockIDRaw(-1-offsetX, 124, -offsetZ, platformBlock);
			chunk.setBlockIDRaw(-1-offsetX, 124, -1-offsetZ, platformBlock);
			chunk.setBlockIDRaw(-1-offsetX, 124, 1-offsetZ, platformBlock);
			chunk.setBlockIDRaw(-offsetX, 124, -offsetZ,  platformBlock);
			chunk.setBlockIDRaw(-offsetX, 124, -1-offsetZ, platformBlock);
			chunk.setBlockIDRaw(-offsetX, 124, 1-offsetZ,  platformBlock);
			chunk.setBlockIDRaw(1-offsetX, 124, -offsetZ,  platformBlock);
			chunk.setBlockIDRaw(1-offsetX, 124, -1-offsetZ, platformBlock);
			chunk.setBlockIDRaw(1-offsetX, 124, 1-offsetZ,  platformBlock);
		}
	}
	private ItemStack pickCheckLootItem(Random random) {
		return ((WeightedRandomLootObject)this.chestLoot.getRandom(random)).getItemStack(random);
	}
}
