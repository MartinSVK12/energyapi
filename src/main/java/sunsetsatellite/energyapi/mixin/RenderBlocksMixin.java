package sunsetsatellite.energyapi.mixin;


import net.minecraft.client.render.RenderBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.WorldSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.util.RenderWire;

@Mixin(
        value={RenderBlocks.class},
        remap = false
)

public class RenderBlocksMixin {
    @Shadow
    private WorldSource blockAccess;

    @Inject(
            method = "renderBlockByRenderType",
            at = @At("HEAD"),
            cancellable = true
    )
    void renderBlockByRenderType(Block block, int renderType, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if(EnergyAPI.wire != null && block.id == EnergyAPI.wire.id){
            cir.setReturnValue(RenderWire.render((RenderBlocks) ((Object)this),this.blockAccess,x,y,z,block,0));
        }
    }
}
