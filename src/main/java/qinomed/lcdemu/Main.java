package qinomed.lcdemu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("LCDEmu Test");
        LCDPanel display = new LCDPanel(window, 20, 9);
        window.add(display);

        drawInit(display);

        window.setSize(display.getSize());
        window.setUndecorated(true);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
    }

    private static void drawInit(LCDPanel display) {
        display.fillRect(0, 0, 7, 5, true);
        display.fillRect(8, 0, 5, 7, true);
    }
}