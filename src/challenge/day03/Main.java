package challenge.day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Path.of("src/challenge/day03/Resources/input.txt"));

            int firstPartScore = lines
                .stream()
                .map(Main::toListOfPairs)
                .mapToInt(Main::toCommonPriority)
                .sum();

            var counter = IntStream.range(0, lines.size()).iterator();
            int secondPartScore = lines
                .stream()
                .collect(Collectors.groupingBy(c -> counter.nextInt() / 3))
                .values()
                .stream()
                .mapToInt(Main::toCommonPriority)
                .sum();

            System.out.printf("Score for first part is: %d and score for second part is: %d", firstPartScore, secondPartScore);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> toListOfPairs(String line) {
        int len = line.length();

        return List.of(
            line.substring(0, len / 2),
            line.substring(len / 2, len)
        );
    }

    private static int toCommonPriority(List<String> list) {
        char c = list
            .stream()
            .reduce((a, b) -> a.chars()
                .filter(a1 -> b.chars().anyMatch(b1 -> a1 == b1))
                .distinct()
                .mapToObj(Character::toString)
                .collect(Collectors.joining())
            )
            .get()
            .charAt(0);

        return Character.isUpperCase(c)
            ? c - 'A' + 27
            : c - 'a' + 1;
    }

}
