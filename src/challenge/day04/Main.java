package challenge.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Path.of("src/challenge/day04/Resources/input.txt"));

            long firstPartCount = lines
                .stream()
                .filter(line -> {
                    Map<String, List<Integer>> map = getMapOfListPairs(line);

                    return new HashSet<>(map.get("first")).containsAll(map.get("second"))
                        || new HashSet<>(map.get("second")).containsAll(map.get("first"));
                })
                .count();

            long secondPartCount = lines
                .stream()
                .filter(line -> {
                    Map<String, List<Integer>> map = getMapOfListPairs(line);

                    return map.get("first").stream().anyMatch(map.get("second")::contains)
                        || map.get("second").stream().anyMatch(map.get("first")::contains);
                })
                .count();

            System.out.printf("Fully contain count is: %d and ranges overlap count is: %d", firstPartCount, secondPartCount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, List<Integer>> getMapOfListPairs(String line) {
        String[] pairs = line.split(",");

        int[] left = Arrays.stream(pairs[0].split("-")).mapToInt(Integer::parseInt).toArray();
        int[] right = Arrays.stream(pairs[1].split("-")).mapToInt(Integer::parseInt).toArray();

        List<Integer> firstPair = IntStream.range(left[0], left[1] + 1).boxed().toList();
        List<Integer> secondPair = IntStream.range(right[0], right[1] + 1).boxed().toList();

        Map<String, List<Integer>> map = new HashMap<>();

        map.put("first", firstPair);
        map.put("second", secondPair);

        return map;
    }

}
