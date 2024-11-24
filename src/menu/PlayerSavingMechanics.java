package menu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerSavingMechanics {
    public static List<PlayerResult> readResults() {

        List<PlayerResult> results = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Pac-Man/assets/Ranking.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 2) {
                    String userName = parts[0];
                    int points = Integer.parseInt(parts[1]);
                    results.add(new PlayerResult(userName, points));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

    public static void saveResult(PlayerResult result) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Pac-Man/assets/Ranking.txt", true))) {
            bw.write(result.getUserName() + "," + result.getPoints());
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
