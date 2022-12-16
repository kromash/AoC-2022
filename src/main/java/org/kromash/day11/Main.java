package org.kromash.day11;

import org.kromash.common.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main extends Solution {
    public Main() {
        super(11);
    }

    public static void main(String[] args) {
        new Main().solve();
    }

    Map<Integer, Monkey> readMonkeys() {
        Map<Integer, Monkey> monkeys = new HashMap<>();

        Iterator<String> iterator = readInputLines().iterator();
        Pattern monkeyPattern = Pattern.compile("Monkey (\\d+):");
        Pattern itemsPattern = Pattern.compile("Starting items: (.*)");
        Pattern operationPattern = Pattern.compile("Operation: new = (.*)");//old * 19")
        Pattern divisiblePattern = Pattern.compile("Test: divisible by (\\d+)");
        Pattern truePattern = Pattern.compile(" If true: throw to monkey (\\d+)");
        Pattern falsePattern = Pattern.compile(" If false: throw to monkey (\\d+)");
        while (iterator.hasNext()) {
            String line = iterator.next();
            Matcher monkeyMatcher = monkeyPattern.matcher(line);
            monkeyMatcher.find();
            int monkeyNumber = Integer.parseInt(monkeyMatcher.group(1));

            line = iterator.next();
            Matcher itemsMatcher = itemsPattern.matcher(line);
            itemsMatcher.find();
            String items = itemsMatcher.group(1);

            line = iterator.next();
            Matcher operationMatcher = operationPattern.matcher(line);
            operationMatcher.find();
            String operation = operationMatcher.group(1);

            line = iterator.next();
            Matcher divisibleMatcher = divisiblePattern.matcher(line);
            divisibleMatcher.find();
            int divisible = Integer.parseInt(divisibleMatcher.group(1));

            line = iterator.next();
            Matcher trueMatcher = truePattern.matcher(line);
            trueMatcher.find();
            int trueMonkey = Integer.parseInt(trueMatcher.group(1));

            line = iterator.next();
            Matcher falseMatcher = falsePattern.matcher(line);
            falseMatcher.find();
            int falseMonkey = Integer.parseInt(falseMatcher.group(1));

            LinkedList<Long> itemsList = new LinkedList<>(
                Arrays.asList(items.split(",")).stream().map(e -> Long.parseLong(e.trim())).collect(
                    Collectors.toList()));

            Monkey monkey = new Monkey(monkeyNumber, itemsList, divisible, operation, trueMonkey, falseMonkey);
            monkeys.put(monkeyNumber, monkey);

            if (iterator.hasNext()) {
                iterator.next();
            }
        }
        return monkeys;
    }

    public String partOne() {
        Map<Integer, Monkey> monkeys = readMonkeys();

        Map<Integer, Integer> monkeyInspections = new HashMap<>();

        for (Monkey monkey : monkeys.values()) {
            monkeyInspections.put(monkey.number, 0);
        }

        int round = 0;
        while (true) {
            ++round;
            for (Monkey monkey : monkeys.values()) {

                while (!monkey.items.isEmpty()) {
                    Long item = monkey.items.poll();

                    Long newValue = monkey.doOperation(item) / 3;
                    monkeyInspections.put(monkey.number, monkeyInspections.get(monkey.number) + 1);

                    if (newValue % monkey.testValue == 0) {
                        monkeys.get(monkey.trueMonkey).items.add(newValue);
                    } else {
                        monkeys.get(monkey.falseMonkey).items.add(newValue);
                    }
                }

            }

            if (round >= 20) {
                break;
            }

        }

        ArrayList<Integer> inspections = new ArrayList<>(monkeyInspections.values());
        inspections.sort(Collections.reverseOrder());

        return String.valueOf(inspections.get(0) * inspections.get(1));
    }

    public String partTwo() {

        Map<Integer, Monkey> monkeys = readMonkeys();
        Map<Integer, Long> monkeyInspections = new HashMap<>();

        long divisor = 1L;
        for (Monkey monkey : monkeys.values()) {
            monkeyInspections.put(monkey.number, 0L);
            divisor *= monkey.testValue;
        }

        int round = 0;
        while (true) {
            ++round;
            for (Monkey monkey : monkeys.values()) {

                while (!monkey.items.isEmpty()) {
                    Long item = monkey.items.poll();

                    Long newValue = monkey.doOperation(item) % divisor;
                    monkeyInspections.put(monkey.number, monkeyInspections.get(monkey.number) + 1);

                    if (newValue % monkey.testValue == 0) {
                        monkeys.get(monkey.trueMonkey).items.add(newValue);
                    } else {
                        monkeys.get(monkey.falseMonkey).items.add(newValue);
                    }
                }

            }

            if (round >= 10000) {
                break;
            }

        }

        ArrayList<Long> inspections = new ArrayList<>(monkeyInspections.values());
        inspections.sort(Collections.reverseOrder());

        return String.valueOf(inspections.get(0) * inspections.get(1));
    }

    record Monkey(int number, LinkedList<Long> items, int testValue, String operation, int trueMonkey,
                  int falseMonkey) {

        public Long doOperation(Long old) {
            String[] o = operation.split(" ");
            long first, second;

            if (o[0].equals("old")) {
                first = old;
            } else {
                first = Integer.parseInt(o[0]);
            }

            if (o[2].equals("old")) {
                second = old;
            } else {
                second = Integer.parseInt(o[2]);
            }

            if (o[1].equals("+")) {
                return first + second;
            } else {
                return first * second;
            }

        }

    }

}
