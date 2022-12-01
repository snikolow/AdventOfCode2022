package challenge.day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            URL path = Main.class.getResource("Resources/input.txt");
            Scanner scanner = new Scanner(new File(Objects.requireNonNull(path).getFile()));

            int currentElfCalories = 0;
            int secondElfCalories = 0;
            int thirdElfCalories = 0;
            int maxCalories = 0;

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                
                if (currentLine.isBlank()) {
                    if (currentElfCalories > maxCalories) {
                        thirdElfCalories = secondElfCalories;
                        secondElfCalories = maxCalories;
                        maxCalories = currentElfCalories;
                    } else if (currentElfCalories > secondElfCalories) {
                        thirdElfCalories = secondElfCalories;
                        secondElfCalories = currentElfCalories;
                    } else if (currentElfCalories > thirdElfCalories) {
                        thirdElfCalories = currentElfCalories;
                    }

                    currentElfCalories = 0;

                    continue;
                }

                currentElfCalories += Integer.parseInt(currentLine.trim());
            }
            
            System.out.printf(
                    "Max Calories: %d. Second highest count is: %d and Third highest count is: %d for total count of: %d",
                    maxCalories,
                    secondElfCalories,
                    thirdElfCalories,
                    (maxCalories + secondElfCalories + thirdElfCalories)
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
