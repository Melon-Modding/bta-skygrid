package watermelonmojito.skygrid.mixins;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class, remap = false)
public class EntityPlayerMixin extends EntityLiving {
	public EntityPlayerMixin(World world) {
		super(world);
	}

	@Inject(method = "onLivingUpdate", at = @At(value = "TAIL"))
	private void loop(CallbackInfo callbackInfo){
		if(y < 0){
			setPos(x, y+256, z);
		}
	}


}
