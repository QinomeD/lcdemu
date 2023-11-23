package qinomed.lcdemu;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiFunction;

public class LCDPanel extends MotionPanel {
    private final int spacing = 5;
    private final Color AMBER = new Color(0xff8503);
    private final Color INACTIVE = new Color(0x1a1a1a);
    private boolean[][] screen;
    public final int pixelWidth, pixeHeight;

    public LCDPanel(JFrame parent, int pixelWidth, int pixelHeight) {
        super(parent);
        this.pixelWidth = pixelWidth;
        this.pixeHeight = pixelHeight;

        this.setSize(pixelWidth*spacing, pixelHeight*spacing);
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        screen = new boolean[pixelHeight][pixelWidth];
        for (int y = 0; y < pixelHeight; y++) {
            for (int x = 0; x < pixelWidth; x++) {
                screen[y][x] = false;
            }
        }
    }

    /*
    *
    *  Cool API-ish functions!
    *
    * */

    public void setPixel(int x, int y, boolean active) {
        try {
            screen[y][x] = active;
        } catch (ArrayIndexOutOfBoundsException ignored) {}
    }

    public void togglePixel(int x, int y) {
        screen[y][x] = !screen[y][x];
    }

    public void drawStraightLine(int x0, int y0, int x1, int y1, boolean active) {
        int sx = Integer.compare(x0, x1) * -1;
        int sy = Integer.compare(y0, y1) * -1;

        do {
            screen[y0][x0] = active;
            x0 += sx;
            y0 += sy;
        } while (x0 != x1 + sx || y0 != y1 + sy);
    }

    public void drawLine(int x0, int y0, int x1, int y1, boolean active) {
        int dx = Math.abs(x1 - x0);
        int sx = x0 < x1 ? 1 : -1;
        int dy = -Math.abs(y1 - y0);
        int sy = y0 < y1 ? 1 : -1;
        int error = dx + dy;

        while (true) {
            this.setPixel(x0, y0, active);
            if (x0 == x1 && y0 == y1)
                break;

            int doubleError = 2 * error;
            if (doubleError >= dy) {
                if (x0 == x1) break;
                error += dy;
                x0 += sx;
            }
            if (doubleError <= dx) {
                if (y0 == y1) break;
                error += dx;
                y0 += sy;
            }
        }
    }

    public void drawPixelArray(boolean[] pixels, int x, int y, int width) {
        if (pixels.length % width != 0) {
            System.err.println("Error: Invalid structure!");
            return;
        }

        for (int i = 0; i < pixels.length/width; i++) {
            for (int j = 0; j < width; j++) {
                this.setPixel(x + j, y + i, pixels[i*width + j]);
            }
        }
    }

    public void drawPixelArray(byte[] pixels, int x, int y, int width) {
        if (pixels.length % width != 0) {
            System.err.println("Error: Invalid structure!");
            return;
        }

        for (int i = 0; i < pixels.length/width; i++) {
            for (int j = 0; j < width; j++) {
                this.setPixel(x + j, y + i, pixels[i*width + j] == 1);
            }
        }
    }
    
    public void drawLineSequence(int[] lines, int x, int y) {
        if (lines.length % 4 != 0) {
            System.err.println("Error: Incorrect line sequence!");
            return;
        }

        for (int i = 0; i < lines.length / 4; i++) {
            this.drawLine(lines[i * 4] + x, lines[i * 4 + 1] + y, lines[i * 4 + 2] + x, lines[i * 4 + 3] + y, true);
        }
    }

    public void drawRect(int x, int y, int width, int height, boolean active) {
        int rightMostX = x + width-1;
        int bottomMostY = y + height-1;
        this.drawStraightLine(x, y, rightMostX, y, active);
        this.drawStraightLine(x, y, x, bottomMostY, active);
        this.drawStraightLine(x, bottomMostY, rightMostX, bottomMostY, active);
        this.drawStraightLine(rightMostX, y, rightMostX, height-1, active);
    }

    public void fillRect(int x, int y, int width, int height, boolean active) {
        for (int y1 = y; y1 < y + height; y1++) {
            for (int x1 = x; x1 < x + width; x1++) {
                screen[y1][x1] = active;
            }
        }
    }

    public void forEachPixel(BiFunction<Integer, Integer, Boolean> function) {
        for (int y = 0; y < pixeHeight; y++) {
            for (int x = 0; x < pixelWidth; x++) {
                screen[y][x] = function.apply(x, y);
            }
        }
    }

    // the end

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int y = 0; y < this.getHeight(); y += spacing) {
            for (int x = 0; x < this.getWidth(); x += spacing) {
                g.setColor(screen[y/spacing][x/spacing] ? AMBER : INACTIVE);
                g.drawRect(x+1, y+1, 1, 1);
            }
        }
    }
}
