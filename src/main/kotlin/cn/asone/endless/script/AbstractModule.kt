package cn.asone.endless.script

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.RenderTickCounter
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket

abstract class AbstractModule {
    @JvmField
    protected val mc: MinecraftClient = MinecraftClient.getInstance()

    open fun tick() {}
    open fun onChatPacket(packet: GameMessageS2CPacket): Boolean { return false; }
    open fun onReceivePacket(packet: Packet<*>) {}
    open fun onDraw2D(context: DrawContext, tickDelta: RenderTickCounter) {}
    open fun clear() {}
}