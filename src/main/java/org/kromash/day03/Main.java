package org.kromash.day03;

import org.kromash.common.Solution;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Main extends Solution {

    public Main() {
        super(3);
    }

    public static void main(String[] args) {
        solve(Main.class);
    }

    private int getPriority(char c) {
        if (c >= 'a') {
            return c - 'a' + 1;
        }
        return c - 'A' + 27;

    }

    public String partOne() {
        int score = 0;

        for (var line : readInputLines()) {
            Set<Character> items = line.chars().mapToObj(c -> (char) c).limit(line.length() / 2)
                    .collect(Collectors.toSet());
            for (int i = line.length() / 2; i < line.length(); ++i) {
                char c = line.charAt(i);
                if (items.contains(c)) {
                    score += getPriority(c);
                    break;
                }
            }
        }

        return String.valueOf(score);
    }

    private Set<Character> stringToCharsSet(String string) {
        return string.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
    }

    public String partTwo() {
        int score = 0;
        Iterator<String> iterator = readInputLines().stream().iterator();

        while (iterator.hasNext()) {

            Iterable<String> iterable = () -> iterator;
            Map<Character, Long> counted = StreamSupport.stream(iterable.spliterator(), false).limit(3)
                    .map(this::stringToCharsSet).flatMap(Collection::stream)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            Optional<Map.Entry<Character, Long>> maxEntry = counted.entrySet().stream()
                    .max(Map.Entry.comparingByValue());

            if (maxEntry.isPresent()) {
                score += getPriority(maxEntry.get().getKey());
            }

        }

        return String.valueOf(score);
    }

}
