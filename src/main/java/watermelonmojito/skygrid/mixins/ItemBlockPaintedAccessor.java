package watermelonmojito.skygrid.mixins;

import net.minecraft.core.item.block.ItemBlockPainted;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ItemBlockPainted.class, remap = false)
public interface ItemBlockPaintedAccessor {
	@Accessor
	boolean getUpperMetadata();
}
