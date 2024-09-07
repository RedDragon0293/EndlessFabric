package cn.asone.endless

import net.minecraft.client.MinecraftClient
import net.minecraft.resource.Resource
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.Language
import java.awt.Color

object I18n {
    private val map: MutableMap<String, String> = mutableMapOf()
    var languageCode: String = ""
        private set
    @JvmStatic
    fun format(key: String): String {
        if (map[key] != null)
            return map[key]!!

        return key
    }

    @JvmStatic
    fun onReload(key: String) {
        if (key == languageCode)
            return

        Endless.logger.info("[I18n] Loading for $key")
        val mc = MinecraftClient.getInstance()
        languageCode = key
        map.clear()
        val identifier = Identifier.of("endless", "lang/$key.json")
        var resourceOptional = mc.resourceManager.getResource(identifier)
        if (resourceOptional.isEmpty) {
            resourceOptional = mc.resourceManager.getResource(Identifier.of("endless", "lang/zh_cn.json"))
        }
        val resource = resourceOptional.get()

        try {
            val inputStream = resource.inputStream
            Language.load(inputStream, map::put)
            inputStream.close()
            mc.player?.sendMessage(Text.literal("[Endless] Running on $languageCode.").withColor(Color.PINK.rgb))
        } catch (e: Exception) {
            Endless.logger.error("Failed to load translation for $languageCode!")
        }
    }
}