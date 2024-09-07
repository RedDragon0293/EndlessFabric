package cn.asone.endless.remap.items

import net.ccbluex.liquidbounce.utils.TinyRemapper
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class InstructionsRemapper(classVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM9, classVisitor) {
    override fun visitMethod(
        access: Int,
        name: String,
        descriptor: String,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        return MyMethodVisitor(super.visitMethod(access, name, descriptor, signature, exceptions))
    }
}
private class MyMethodVisitor(methodVisitor: MethodVisitor) : MethodVisitor(Opcodes.ASM9, methodVisitor) {
    override fun visitMethodInsn(
        opcode: Int,
        owner: String,
        name: String,
        descriptor: String,
        isInterface: Boolean
    ) {
        super.visitMethodInsn(opcode, owner, TinyRemapper.remapMethod(owner, name, descriptor, true), descriptor, isInterface)
    }

    override fun visitFieldInsn(opcode: Int, owner: String, name: String, descriptor: String) {
        super.visitFieldInsn(opcode, owner, TinyRemapper.remapField(owner, name, descriptor, true), descriptor)
    }
}