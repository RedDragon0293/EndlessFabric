package cn.asone.endless

import cn.asone.endless.script.AbstractModule
import cn.asone.endless.script.ScriptManager
import net.ccbluex.liquidbounce.utils.TinyRemapper
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.FishingBobberEntity
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color

object Endless : ClientModInitializer {
    val logger: Logger = LoggerFactory.getLogger("Endless")
    private val key = KeyBinding("key.toggleFish", GLFW.GLFW_KEY_END, "key.categories.endless")
    lateinit var instance: Array<AbstractModule>
    var enabled = false
    private var prev = false

    override fun onInitializeClient() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        logger.info("==================================")
        logger.info("Hello fabric world!")
        logger.info("==================================")
        KeyBindingHelper.registerKeyBinding(key)
        //KeyBindingHelper.registerKeyBinding(fKey)
    }

    fun startClient() {
        logger.info("==================================")
        logger.info("Hello endless world!")
        logger.info("==================================")

        TinyRemapper.load()
        instance = ScriptManager.loadScript()

        HudRenderCallback.EVENT.register { context, tickDeltaManager ->
            if (enabled)
                instance.forEach { it.onDraw2D(context, tickDeltaManager) }
        }
    }

    fun tick() {
        if (key.isPressed && !prev) {
            enabled = !enabled
            MinecraftClient.getInstance().inGameHud.chatHud.addMessage(
                Text.literal("enable: $enabled").withColor(if (enabled) Color.GREEN.rgb else Color.YELLOW.rgb)
            )
            if (enabled)
                instance.forEach { it.clear() }
        }
        prev = key.isPressed

        if (enabled)
            instance.forEach { it.tick() }
    }

    fun onChatPacket(packet: GameMessageS2CPacket): Boolean {
        if (!enabled)
            return false

        var result = false
        instance.forEach {
            if (it.onChatPacket(packet))
                result = true
        }
        return result
    }

    fun onReceivePacket(packet: Packet<*>) {
        if (enabled)
            instance.forEach { it.onReceivePacket(packet) }
    }

    fun onReload() {
        if (I18n.languageCode.isEmpty())
            I18n.onReload("zh_cn")
    }

    fun shouldBeShown(entity: Entity): Boolean {
        if (entity is FishingBobberEntity)
            return true
        //if (entity is ArmorStandEntity) return true
        if (entity !is PlayerEntity) return false

        for (it in MinecraftClient.getInstance().player!!.networkHandler.listedPlayerListEntries) {
            if (it.profile.name == entity.gameProfile.name) {
                return true
            }
        }
        return false
    }
}