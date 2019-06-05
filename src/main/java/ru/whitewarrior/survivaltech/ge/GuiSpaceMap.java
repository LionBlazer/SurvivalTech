package ru.whitewarrior.survivaltech.ge;

import net.java.games.input.Keyboard;
import net.minecraft.client.gui.*;
import ru.whitewarrior.ge.ISpaceBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiSpaceMap extends GuiScreen {

    private List<ISpaceBody> bodies = new ArrayList<>();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }
}
