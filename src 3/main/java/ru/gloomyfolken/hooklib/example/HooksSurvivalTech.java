package ru.gloomyfolken.hooklib.example;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import ru.gloomyfolken.hooklib.asm.Hook;
import ru.gloomyfolken.hooklib.asm.ReturnCondition;

public class HooksSurvivalTech {
    @Hook(returnCondition = ReturnCondition.ALWAYS, isMandatory = true)
    public static void registerBlock(Block block, int id, ResourceLocation res, Block blockToRegister) {
//        if(id == 17 /* јйди камн€ */){
//            Block.REGISTRY.register(id, res, BlockVanilaRegister.stonefake);
//        }
//        else
            Block.REGISTRY.register(id, res, blockToRegister);
    }

    @Hook(returnCondition = ReturnCondition.ON_NOT_NULL, isMandatory = true)
    public static Block getRegisteredBlock(Blocks inst, String name) {
//        if(name.equals("log")){
//            return BlockVanilaRegister.stonefake;
//        }
        return null;
    }
}
