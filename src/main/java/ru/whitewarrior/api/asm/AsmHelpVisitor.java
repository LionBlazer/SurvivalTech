package ru.whitewarrior.api.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AsmHelpVisitor extends ClassVisitor {
    private Label label;
    private MethodVisitor methodVisitor;

    public AsmHelpVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    public void setMethodVisitor(MethodVisitor methodVisitor) {
        this.methodVisitor = methodVisitor;
    }
    /**
     * @param opcode  This opcode is either IFEQ, IFNE, IFLT, IFGE, IFGT, IFLE, IF_ICMPEQ, IF_ICMPNE, IF_ICMPLT, IF_ICMPGE, IF_ICMPGT, IF_ICMPLE, IF_ACMPEQ, IF_ACMPNE, GOTO, JSR, IFNULL or IFNONNULL.
     */
    protected void startBlock(int opcode){
        label = new Label();
        methodVisitor.visitJumpInsn(opcode, label);
    }

    protected void endBlock(){
        methodVisitor.visitLabel(label);
    }

    /**
     * @param opcode This opcode is either IRETURN, LRETURN, FRETURN, DRETURN, ARETURN, RETURN
     */
    protected void methodReturn(int opcode){
        methodVisitor.visitInsn(opcode);
    }

    private static String nameOf(String className) {
        if (className.contains("."))
            className = className.replaceAll("\\.", "/");
        return className;
    }

    protected void putFieldStatic(String className, String fieldName, String fieldDesc){
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, nameOf(className), fieldName, fieldDesc);
    }
}
