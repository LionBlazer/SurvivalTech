package ru.whitewarrior.survivaltech.api.client.gui;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class GuiMainSplashManager {
    private static List<Pair<Object, Double>> splashes = Lists.newArrayList();

    public static void putSplash(String splash, Double change) {
        splashes.add(new ImmutablePair(splash, change));
    }

    public static void putDynamicSplash(ISplash splash, Double change) {
        splashes.add(new ImmutablePair(splash, change));
    }

    public static List<Pair<Object, Double>> getSplashes() {
        return splashes;
    }
}
