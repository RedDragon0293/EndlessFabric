package cn.asone.endless.mixin;

import cn.asone.endless.Endless;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;onResolutionChanged()V"))
    private void startClient(CallbackInfo callback) {
        Endless.INSTANCE.startClient();
    }

    @Inject(method = "hasOutline(Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void injectOutline(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (Endless.INSTANCE.getEnabled() && Endless.INSTANCE.shouldBeShown(entity))
            cir.setReturnValue(true);
    }
}
