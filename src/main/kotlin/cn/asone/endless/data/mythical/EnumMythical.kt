package cn.asone.endless.data.mythical

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ProfileComponent
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import java.util.*

enum class EnumMythical(@JvmField var skin: String) {
    Helios(
        "ewogICJ0aW1lc3RhbXAiIDogMTY4ODM5NTI2NjI1NSwKICAicHJvZmlsZUlkIiA6ICI2ZTIyNjYxZmNlMTI0MGE0YWE4OTA0NDA0NTFiYjBiNSIsCiAgInByb2ZpbGVOYW1lIiA6ICJncnZleWFyZCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jMDEwMmJlNjc1NjI3NDcxOWI3ZjYyNTgzMGVhN2VmNTA1MWM3ZDk1ZGMwMWZlODM1OWI0MTg2Mzc4YTBjMjYzIgogICAgfQogIH0KfQ=="
    ),
    Selene(
        "ewogICJ0aW1lc3RhbXAiIDogMTY4ODM5NTIzNDU4MCwKICAicHJvZmlsZUlkIiA6ICJkYmQ4MDQ2M2EwMzY0Y2FjYjI3OGNhODBhMDBkZGIxMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJ4bG9nMjEiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjRhMWZkOWRmOGFkMWQwZTIxNmFjMzQ3YTM5YjQ3ZTc5N2Y0YTNkZTdkZTRkZjA3M2IwNjVjYjY5ZjcwNWJhZiIKICAgIH0KICB9Cn0="
    ),
    Nyx(
        "ewogICJ0aW1lc3RhbXAiIDogMTY4ODM5NTI1MjY0MSwKICAicHJvZmlsZUlkIiA6ICJhNzdkNmQ2YmFjOWE0NzY3YTFhNzU1NjYxOTllYmY5MiIsCiAgInByb2ZpbGVOYW1lIiA6ICIwOEJFRDUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDU2MTIzYjMzNGM1YzE4YTRkZjljMWQ2YWZmMjUwNDZmNWUwNmE3ZWE4ZjYwYjgwYjkxYWU0OGFjN2Y5ODMwZCIKICAgIH0KICB9Cn0="
    ),
    Aphrodite(
        "ewogICJ0aW1lc3RhbXAiIDogMTY4ODM5NTIwNTEzNSwKICAicHJvZmlsZUlkIiA6ICIwZDZjODU0ODA3ZGQ0NWZkYmMxZDEyMzY2OGY1ZWQwZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJXcWxmZnhJcmt0IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2ZjMDg0NzY1YzYyYzAzZjM0NzllNzU5MjA4Y2ExZTdmYTk5ZjY3NGQwYzhiZTc4YTNmMTBmNWIxZTg2NmNhMjQiCiAgICB9CiAgfQp9"
    ),
    Zeus(
        "ewogICJ0aW1lc3RhbXAiIDogMTY4ODM5NTE4NzE1MiwKICAicHJvZmlsZUlkIiA6ICI2NjI3NjU4MzhkMWQ0YmU0YjU1NmRkOTQwMmE1MDU0NiIsCiAgInByb2ZpbGVOYW1lIiA6ICJfTWF0dEd6ejI4OV8iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmI0MmRiMTgyNDcxZGEwNWJjMmUzZDA0ZWEwOGI3MDY5MDA0ZjVjMDY2YzBhYWNjYTFmMThjNDBlZTMwNDljZiIKICAgIH0KICB9Cn0="
    ),
    Daedalus(
        "ewogICJ0aW1lc3RhbXAiIDogMTY4ODM5NTIyMDYwOCwKICAicHJvZmlsZUlkIiA6ICI2NmI0ZDRlMTFlNmE0YjhjYTFkN2Q5YzliZTBhNjQ5OSIsCiAgInByb2ZpbGVOYW1lIiA6ICJBcmFzdG9vWXNmIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2E5MmRjYTFlODIxOGIxOGIwNzU5ZmM1YmFlZGM3ZTA1NDA2N2IxZWMyZjk3YjEwYzhjM2ZjYThmOTIzYTBhNmEiCiAgICB9CiAgfQp9"
    ),
    Hades(
        "ewogICJ0aW1lc3RhbXAiIDogMTY4ODM5NTE3MDE1NSwKICAicHJvZmlsZUlkIiA6ICIxYmY4ZjBiZDRkZjc0Njg1ODQwNjU2NDc2ZGU0NmNmMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJDcnV6YWRhMjIiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQ2ZmEyYzU0OTI3MjJiZDUxMGNmNTQ2Y2RlMWI2YjZjNjg5ZTc2NDBhOTk2MDZlZDQ5OTMwZmU1NGRlZjBkIgogICAgfQogIH0KfQ=="
    );

    val stack: ItemStack

    init {
            val stack1 = ItemStack(Items.PLAYER_HEAD)
            val g = GameProfile(UUID.randomUUID(), name)
            g.properties.put("textures", Property("textures", skin))
            g.properties.put("signature", null)
            stack1.set(DataComponentTypes.PROFILE, ProfileComponent(g))
            stack = stack1
    }
}