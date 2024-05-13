package watermelonmojito.skygrid;

import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.biome.provider.BiomeProvider;
import net.minecraft.core.world.biome.provider.BiomeProviderSingleBiome;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.wind.WindManagerGeneric;

public class WorldTypeSkygrid extends WorldType {
	public WorldTypeSkygrid(String languageKey) {
		super(languageKey, Weather.overworldClear, new WindManagerGeneric(), false, createLightRamp(), SeasonConfig.builder().withSingleSeason(Seasons.NULL).build());
	}

	private static float[] createLightRamp() {
		float[] brightnessRamp = new float[32];
		float f = 0.05F;

		for(int i = 0; i <= 31; ++i) {
			float f1 = 1.0F - (float)i / 15.0F;
			if (i > 15) {
				f1 = 0.0F;
			}

			brightnessRamp[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
		}

		return brightnessRamp;
	}

	public int getMinY() {
		return 0;
	}

	public int getMaxY() {
		return 255;
	}

	public int getOceanY() {
		return 0;
	}

	public int getOceanBlock() {
		return 0;
	}

	public int getFillerBlock() {
		return 0;
	}

	public BiomeProvider createBiomeProvider(World world) {
		return new BiomeProviderSingleBiome(Biomes.OVERWORLD_PLAINS, 1.0, 1.0, 1.0);
	}

	public ChunkGenerator createChunkGenerator(World world) {
		return new ChunkGeneratorSkygrid(world);
	}

	public boolean isValidSpawn(World world, int x, int y, int z) {
		return true;
	}

	public void getInitialSpawnLocation(World world) {
		world.getLevelData().setSpawn(0, 125, 0);
	}

	public void getRespawnLocation(World world) {
	}

	public int getDayNightCycleLengthTicks() {
		return 24000;
	}

	public float getCelestialAngle(World world, long tick, float partialTick) {
		return this.getTimeOfDay(world, tick, partialTick);
	}

	public float[] getSunriseColor(float timeOfDay, float partialTick) {
		float[] sunriseCol = new float[4];
		float f2 = 0.4F;
		float f3 = MathHelper.cos(timeOfDay * 3.141593F * 2.0F) - 0.0F;
		float f4 = -0.0F;
		if (f3 >= f4 - f2 && f3 <= f4 + f2) {
			float c = (f3 - f4) / f2 * 0.5F + 0.5F;
			float a = 1.0F - (1.0F - MathHelper.sin(c * 3.141593F)) * 0.99F;
			a *= a;
			sunriseCol[0] = c * 0.3F + 0.7F;
			sunriseCol[1] = c * c * 0.7F + 0.2F;
			sunriseCol[2] = c * c * 0.0F + 0.2F;
			sunriseCol[3] = a;
			return sunriseCol;
		} else {
			return null;
		}
	}

	public int getSkyDarken(World world, long tick, float partialTick) {
		float f1 = this.getCelestialAngle(world, tick, partialTick);
		float f2 = 1.0F - (MathHelper.cos(f1 * 3.141593F * 2.0F) * 2.0F + 0.5F);
		if (f2 < 0.0F) {
			f2 = 0.0F;
		}

		if (f2 > 1.0F) {
			f2 = 1.0F;
		}

		float weatherOffset = 0.0F;
		Weather currentWeather = world.getCurrentWeather();
		if (currentWeather != null) {
			weatherOffset = (float)currentWeather.subtractLightLevel * world.weatherManager.getWeatherIntensity() * world.weatherManager.getWeatherPower();
		}

		return (int)(f2 * (11.0F - weatherOffset) + weatherOffset);
	}

	public Vec3d getFogColor(World world, double x, double y, double z, float celestialAngle, float partialTick) {
		float f2 = MathHelper.cos(celestialAngle * 3.141593F * 2.0F) * 2.0F + 0.5F;
		if (f2 < 0.0F) {
			f2 = 0.0F;
		}

		if (f2 > 1.0F) {
			f2 = 1.0F;
		}

		float r = 0.7529412F;
		float g = 0.8470588F;
		float b = 1.0F;
		r *= f2 * 0.94F + 0.06F;
		g *= f2 * 0.94F + 0.06F;
		b *= f2 * 0.91F + 0.09F;
		return Vec3d.createVector((double)r, (double)g, (double)b);
	}

	public boolean mayRespawn() {
		return true;
	}

	public float getCloudHeight() {
		return 108.0F;
	}

	public boolean hasGround() {
		return false;
	}
}
