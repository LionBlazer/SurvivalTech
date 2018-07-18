package ru.gloomyfolken.hooklib.example;

import ru.gloomyfolken.hooklib.minecraft.HookLoader;
import ru.gloomyfolken.hooklib.minecraft.PrimaryClassTransformer;

public class ExampleHookLoader extends HookLoader {
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{PrimaryClassTransformer.class.getName()};
    }

    @Override
    public void registerHooks() {
        registerHookContainer("ru.gloomyfolken.hooklib.example.HooksSurvivalTech");
    }
}
