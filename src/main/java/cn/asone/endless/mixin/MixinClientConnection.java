package cn.asone.endless.mixin;

import cn.asone.endless.Endless;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class MixinClientConnection {
    /**
     * Handle receiving packets
     */
    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true, require = 1)
    private static void hookReceivingPacket(Packet<?> packet, PacketListener listener, CallbackInfo ci) {
        if (packet instanceof GameMessageS2CPacket) {
            if (Endless.INSTANCE.onChatPacket((GameMessageS2CPacket) packet)) {
                ci.cancel();
                Endless.INSTANCE.getLogger().info(((GameMessageS2CPacket) packet).content().getString());
            }
        } else Endless.INSTANCE.onReceivePacket(packet);
    }
}
