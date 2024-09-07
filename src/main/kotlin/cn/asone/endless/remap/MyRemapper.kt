package cn.asone.endless.remap

import cn.asone.endless.remap.items.InstructionsRemapper
import cn.asone.endless.remap.items.ClassNameRemapper
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

object MyRemapper {
    fun remap(bin: ByteArray): ByteArray {
        val classReader = ClassReader(bin)
        val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS or ClassWriter.COMPUTE_FRAMES)
        val classRemapper = ClassNameRemapper(classWriter)
        val instructionsRemapper = InstructionsRemapper(classRemapper)
        //val classNode = ClassNode()
/*
        classNode.methods.forEach { i ->
            for (it in i.instructions) {
                if (it is MethodInsnNode) {

                }
            }
        }
*/

        classReader.accept(instructionsRemapper, 0)
        return classWriter.toByteArray()
    }
}