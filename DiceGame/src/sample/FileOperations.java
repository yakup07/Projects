package sample;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperations {

    public ArrayList<Integer> readFile() {
        ArrayList<Integer> scores = new ArrayList<>();
        Scanner scanner = null;

        try {
            scanner = new Scanner(new FileReader("highScores.txt"));

            while (scanner.hasNextLine()) {
                int i = Integer.parseInt(scanner.nextLine());
                scores.add(i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return scores;
    }

    public void writeFile(ArrayList<Integer> integers){
        try {
            FileWriter file = new FileWriter("highScores.txt");

            for(Integer i : integers){
                file.write(i + "\n");
            }

            file.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
