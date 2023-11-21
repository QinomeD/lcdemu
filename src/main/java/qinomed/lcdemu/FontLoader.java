package qinomed.lcdemu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FontLoader {
    public static Map<Character, int[]> font = new HashMap<>();

    public static void loadFont(String name) {
        try {
            InputStream is = FontLoader.class.getResourceAsStream("/font/" + name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            List<String> lines = reader.lines().toList();
            if (lines.size() % 2 != 0) {
                System.err.println("Error: Incorrect structure!");
            }

            for (int i = 0; i < lines.size()/2; i+=2) {
                font.put(lines.get(i).charAt(0), Arrays.stream(lines.get(i + 1).split(",")).mapToInt(Integer::parseInt).toArray());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
