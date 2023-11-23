package qinomed.lcdemu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("LCDEmu Test");
        LCDPanel display = new LCDPanel(window, 40, 18);
        window.add(display);

        FontLoader.loadFont("font.txt");
        drawInit(display);

        window.setSize(display.getSize());
        window.setUndecorated(true);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
    }

    private static void drawInit(LCDPanel display) {
        display.drawPixelArray(FontLoader.font.get('5'), 0, 0, 5);
    }
}
