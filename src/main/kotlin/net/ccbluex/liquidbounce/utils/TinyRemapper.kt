/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 *
 * Copyright (c) 2015 - 2024 CCBlueX
 *
 * LiquidBounce is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LiquidBounce is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LiquidBounce. If not, see <https://www.gnu.org/licenses/>.
 */
package net.ccbluex.liquidbounce.utils

import cn.asone.endless.Endless.logger
import com.google.common.cache.CacheBuilder
import net.fabricmc.mapping.tree.TinyMappingFactory
import net.fabricmc.mapping.tree.TinyTree

/**
 * Tiny mappings
 *
 * These are fabric mappings which are being exported when the jar is being built.
 * It allows you to remap readable names into obfuscated environments.
 */
object TinyRemapper {
    private var mappings: TinyTree? = null
    private var environment: String? = null
    private val cache = CacheBuilder.newBuilder().build<String, String>()

    fun load() {
        runCatching {
            mappings = TinyMappingFactory.load(TinyRemapper::class.java.getResourceAsStream("/mappings/mappings.tiny")
                ?.bufferedReader() ?: throw IllegalArgumentException("Resource not found"))
        }.onFailure {
            logger.error("Unable to load mappings. Ignore this if you are using a development environment.", it)
        }

        // Probe environment
        runCatching {
            probeEnvironment()
        }.onFailure {
            logger.error("Unable to probe environment. Please make sure you are using a valid environment.", it)
        }
    }

    private fun probeEnvironment() {
        /*val minecraftEntry = mappings?.classEntries?.find {
            it?.get("named") == "net/minecraft/client/MinecraftClient"
        }*/
        val minecraftEntry = mappings?.classes?.find {
            it.getName("named") == "net/minecraft/client/MinecraftClient"
        }

        if (minecraftEntry == null) {
            logger.error("Unable to probe environment. Please make sure you are using a valid mapping.")
            return
        }

        val officialName = minecraftEntry.getName("official")?.replace('/', '.')
        val intermediaryName = minecraftEntry.getName("intermediary")?.replace('/', '.')

        logger.info("Probing environment... (official: $officialName, intermediary: $intermediaryName)")

        try {
            Class.forName("net.minecraft.client.MinecraftClient")
            this.environment = "named"
            logger.warn("Development environment!!!")
            return
        } catch (_: ClassNotFoundException) {}

        try {
            Class.forName(officialName)
            this.environment = "official"
            logger.info("Official environment detected.")
        } catch (_: ClassNotFoundException) {
            try {
                Class.forName(intermediaryName)
                this.environment = "intermediary"
                logger.info("Intermediary environment detected.")

                return
            } catch (_: ClassNotFoundException) {
                logger.error("No matching environment detected. Please make sure you are using a valid environment.")
                return
            }
        }
    }

    fun remapClassName(clazz: String): String {
        if (environment == null || mappings == null) {
            return clazz
        }

        val className = clazz.replace('.', '/')

        val c = cache.getIfPresent(className)
        if (c != null)
            return c

        val f = mappings!!.classes.find {
            it?.getName("named") == className
        }?.getName(environment) ?: clazz
        cache.put(className, f)
        return f
    }

    fun remapField(clazz: String, name: String, descriptor: String, superClasses: Boolean): String {
        if (environment == null || mappings == null) {
            return name
        }

        val current: Class<*>
        try {
            current = Class.forName(remapClassName(clazz).replace('/', '.'))
        } catch (e: Exception) {
            return name
        }
        val classes = mutableSetOf(current)

        if (superClasses) {
            process(classes, current)
        }

        val defs = classes.mapNotNull { i ->
            mappings!!.classes.find { it.getName(environment).replace('/', '.') == i.name }
        }

        /*return mappings?.f?.find {
            val intern = it?.get(environment) ?: return@find false
            val named = it.get("named") ?: return@find false

            classes.contains(intern.owner) && named.name == name
        }?.get(environment)?.name ?: name*/
        for (it in defs) {
            for (i in it.fields) {
                if (i.getName("named") == name && i.getDescriptor("named") == descriptor)
                    return i.getName(environment)
            }
        }
        return name
    }

    fun remapMethod(clazz: String, name: String, descriptor: String, superClasses: Boolean): String {
        if (environment == null || mappings == null) {
            return name
        }

        val current: Class<*>
        try {
            current = Class.forName(remapClassName(clazz).replace('/', '.'))
        } catch (e: Exception) {
            return name
        }
        val classes = mutableSetOf(current)

        if (superClasses) {
            process(classes, current)
        }

        val defs = classes.mapNotNull { i ->
            mappings!!.classes.find { it.getName(environment).replace('/', '.') == i.name }
        }

        for (it in defs) {
            for (i in it.methods) {
                if (i.getName("named") == name && i.getDescriptor("named") == descriptor)
                    return i.getName(environment)
            }
        }
        return name
    }

    private fun process(classes: MutableSet<Class<*>>, clazz: Class<*>) {
        for (it in clazz.interfaces) {
            classes.add(it)
            process(classes, it)
        }

        if (clazz.superclass != null && clazz.superclass.name != "java.lang.Object") {
            classes.add(clazz.superclass)
            process(classes, clazz.superclass)
        }
    }
}
