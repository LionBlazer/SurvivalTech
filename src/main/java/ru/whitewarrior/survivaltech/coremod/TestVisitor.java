package ru.whitewarrior.survivaltech.coremod;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TestVisitor extends ClassVisitor {
    public TestVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        //Этот кусок кода служит для того чтобы коструктор не изменялся
        if (name.equals("<init>"))
            return super.visitMethod(access, name, desc, signature, exceptions);
        return new MethodVisitor(Opcodes.ASM5, super.visitMethod(access, name, desc, signature, exceptions)) {
            @Override
            public void visitCode() {

            }

            @Override
            public void visitLineNumber(int line, Label start) {
                if (line == 8) {
                    super.visitVarInsn(Opcodes.ILOAD, 0);
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, "ru/whitewarrior/survivaltech/Core", "hookInMethod", "(I)V", false);
                }
                super.visitLineNumber(line, start);
            }
        };
    }

}
