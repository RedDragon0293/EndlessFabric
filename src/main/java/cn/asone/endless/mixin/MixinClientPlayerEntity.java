package cn.asone.endless.mixin;

import cn.asone.endless.Endless;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    @Inject(method = "tick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V",
            shift = At.Shift.BEFORE,
            ordinal = 0)
    )
    private void hookTickEvent(CallbackInfo ci) {
        try {
            if ((ClientPlayerEntity) ((Object) this) != MinecraftClient.getInstance().player) {
                return;
            }
            Endless.INSTANCE.tick();
        } catch (Exception e) {
            Endless.INSTANCE.getLogger().error("Error while ticking!", e);
        }
    }
}