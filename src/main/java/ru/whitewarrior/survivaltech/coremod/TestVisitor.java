package ru.whitewarrior.survivaltech.coremod;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import ru.whitewarrior.api.asm.AsmHelpVisitor;

import java.util.HashMap;
import java.util.Map;

public class TestVisitor extends AsmHelpVisitor {
    private Map<String, String> fields = new HashMap<>();
    public TestVisitor(ClassVisitor cv) {
        super(cv);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        System.out.println(fields);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        fields.clear();
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        fields.put(name, desc);
        return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        //Этот кусок кода служит для того чтобы коструктор не изменялся
        if(name.equals("<init>") || name.equals("<clinit>")) return super.visitMethod(access,name,desc,signature,exceptions);
        return new MethodVisitor(Opcodes.ASM5, super.visitMethod(access, name, desc, signature, exceptions)) {
            @Override
            public void visitCode() {
                setMethodVisitor(this);

                putFieldStatic("ru.whitewarrior.survivaltech.coremod.Acessor", "staticVar", "I");
                super.visitMethodInsn(Opcodes.INVOKESTATIC, "ru/whitewarrior/survivaltech/Core", "hookInMethod", "(I)Z", false);

                startBlock(Opcodes.IFNE);
                super.visitIntInsn(Opcodes.BIPUSH, 100);
                methodReturn(Opcodes.IRETURN);
                endBlock();
            }
        };
    }

}
