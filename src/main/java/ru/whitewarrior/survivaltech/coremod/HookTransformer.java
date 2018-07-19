package ru.whitewarrior.survivaltech.coremod;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import ru.whitewarrior.survivaltech.coremod.packet.NetworkPacket;

@SuppressWarnings("all")
public class HookTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if(transformedName.equals("com.google.gson.annotations.JsonAdapter") || transformedName.equals("ru.whitewarrior.survivaltech.coremod.packet.NetworkPacket")) return basicClass;
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);
        boolean isConstructorExists = false;
        boolean isAnnotated = false;
        if(classNode.visibleAnnotations != null)
            for (AnnotationNode node : classNode.visibleAnnotations) {
                if (node != null && node.desc != null && node.desc.equals(Type.getDescriptor(NetworkPacket.class))) {
                    isAnnotated = true;
                }
            }
        if (!isAnnotated)
            return basicClass;

        for (MethodNode methodNode : classNode.methods)
            if (methodNode.name.equals("<init>") && methodNode.desc.equals("()V")) {
                isConstructorExists = true;
                break;
            }


        if (!isConstructorExists) {
            MethodNode methodNode = new MethodNode(Opcodes.ACC_PUBLIC, "<init>", "()V", null, new String[]{});
            InsnList nodesList = new InsnList();
            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
            nodesList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, classNode.superName, "<init>", "()V"));
            nodesList.add(new InsnNode(Opcodes.RETURN));
            methodNode.instructions = nodesList;
            classNode.methods.add(methodNode);
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}