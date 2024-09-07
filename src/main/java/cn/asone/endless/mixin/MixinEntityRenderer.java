package cn.asone.endless.mixin;

import cn.asone.endless.Endless;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer<T extends Entity> {
    @ModifyExpressionValue(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSneaky()Z"))
    protected boolean modify(boolean original) {
        if (Endless.INSTANCE.getEnabled())
            return false;

        return original;
    }
}
