package ru.whitewarrior.survivaltech.coremod;


import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import javax.annotation.Nullable;
import java.util.Map;

public class HooksPlugin implements IFMLLoadingPlugin , IClassTransformer
{

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {getClass().getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        switch (transformedName) {
            case "ru.whitewarrior.survivaltech.coremod.Acessor": {
                ClassReader cr = new ClassReader(basicClass);
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                TestVisitor cv = new TestVisitor(cw);
                cr.accept(cv, ClassReader.SKIP_FRAMES);
                return cw.toByteArray();
            }
        }
        return basicClass;
    }
}
