package ru.whitewarrior.survivaltech.coremod;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.util.Map;

public class HooksPlugin implements IFMLLoadingPlugin //,IClassTransformer
{

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{HookTransformer.class.getName()};
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

    //    @Override
    //    public byte[] transform(String name, String transformedName, byte[] basicClass) {
    //        switch (transformedName) {
    //            case "ru.whitewarrior.survivaltech.coremod.Acessor": {
    //                ClassReader cr = new ClassReader(basicClass);
    //                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    //                TestVisitor cv = new TestVisitor(cw);
    //                cr.accept(cv, ClassReader.SKIP_FRAMES);
    //                return cw.toByteArray();
    //            }
    //        }
    //        return basicClass;
    //    }
}
