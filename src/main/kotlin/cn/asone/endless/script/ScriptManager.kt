package cn.asone.endless.script

import cn.asone.endless.Endless
import cn.asone.endless.remap.MyRemapper
import net.minecraft.client.MinecraftClient
import java.io.File
import com.itranswarp.compiler.MemoryClassLoader

object ScriptManager {
    private val mc = MinecraftClient.getInstance()
    private val folder = File(mc.runDirectory, "EndlessFabric").apply {
        if (!exists())
            mkdir()
    }

    @JvmStatic
    @Suppress("unchecked_cast")
    fun loadScript(): Array<AbstractModule> {
        val result = arrayListOf<AbstractModule>()
        val classes = arrayListOf<Class<*>>()
        val classLoader = MemoryClassLoader(mutableMapOf())
        for (it in folder.listFiles()!!) {
            if (it.extension != "class")
                continue

            try {
                val origin = it.readBytes()
                val bin = MyRemapper.remap(origin)
                //val classLoader = MemoryClassLoader(mapOf(it.nameWithoutExtension to bin))
                classLoader.classBytes[it.nameWithoutExtension] = bin

                val clazz = classLoader.loadClass(it.nameWithoutExtension)
                if (clazz != null) {
                    classes.add(clazz)
                    Endless.logger.info("成功加载外部类 {}.", it.name)
                }
            } catch (e: Exception) {
                Endless.logger.error("无法加载外部类 " + it.name + "!", e)
            }
        }

        for (it in classes) {
            if (it.superclass != null && it.superclass == AbstractModule::class.java) {
                Endless.logger.info("成功加载外部模块 {}.", it.simpleName)
                result.add((it as Class<AbstractModule>).getDeclaredConstructor().newInstance())
            } else
                Endless.logger.error("模块 {} 格式有误!", it.name)
        }
        return result.toTypedArray()
    }
}