package ru.whitewarrior.survivaltech.coremod;

public class Acessor {
    public static int staticVar = 1;

    public static int doGet() {
        staticVar *= 2;
        int localVariable = staticVar * 10 - 1;
        return localVariable;
    }

}
