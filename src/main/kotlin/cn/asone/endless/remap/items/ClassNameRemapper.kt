package cn.asone.endless.remap.items

import net.ccbluex.liquidbounce.utils.TinyRemapper
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.commons.ClassRemapper
import org.objectweb.asm.commons.Remapper

class ClassNameRemapper(classVisitor: ClassVisitor) : ClassRemapper(classVisitor, object : Remapper() {
    override fun map(internalName: String): String {
        return TinyRemapper.remapClassName(internalName)
    }
})