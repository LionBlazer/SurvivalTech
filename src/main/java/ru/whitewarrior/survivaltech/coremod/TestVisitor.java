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
        if(name.equals("<init>") || name.equals("<clinit>")) return super.visitMethod(access,name,desc,signature,exceptions);
        return new MethodVisitor(Opcodes.ASM5, super.visitMethod(access, name, desc, signature, exceptions)) {
            @Override
            public void visitCode() {
                super.visitFieldInsn(Opcodes.GETSTATIC, "ru/whitewarrior/survivaltech/coremod/Acessor", "staticVar", "I");
                super.visitMethodInsn(Opcodes.INVOKESTATIC, "ru/whitewarrior/survivaltech/Core", "hookInMethod", "(I)Z", false);
                Label label = new Label();
                super.visitJumpInsn(Opcodes.IFNE, label);
                super.visitIntInsn(Opcodes.BIPUSH, 100);
                super.visitInsn(Opcodes.IRETURN);
                super.visitLabel(label);
            }
        };
    }

}
