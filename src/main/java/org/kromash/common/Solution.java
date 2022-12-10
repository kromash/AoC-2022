package org.kromash.common;

import java.util.List;

public abstract class Solution implements SolutionI {
    int day;
    String testData;
    InputReader inputReader;

    public Solution(int day) {
        this(day, null);
    }

    public Solution(int day, String testData) {
        this.day = day;
        this.testData = testData;
        inputReader = new InputReader();
    }

    public abstract String partOne();

    public abstract String partTwo();

    public void solve() {
        System.out.printf("Day %d part one answer:\n%s\n", day, partOne());
        System.out.printf("Day %d part two answer:\n%s\n", day, partTwo());
    }

    public List<String> readInputLines() {
        if (testData != null) {
            return List.of(testData.split("\n"));
        }
        return inputReader.readInputLines(day);

    }
}
