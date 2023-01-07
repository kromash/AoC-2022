package org.kromash.common;

import java.util.Iterator;
import java.util.List;

public abstract class Solution implements SolutionI {
    int day;
    InputReader inputReader;

    public Solution(int day) {
        this.day = day;
        inputReader = new InputReader();
    }

    public abstract String partOne();

    public abstract String partTwo();

    public void solve() {
        System.out.printf("Day %d part one answer:\n%s\n", day, partOne());
        System.out.printf("Day %d part two answer:\n%s\n", day, partTwo());
    }

    public List<String> readInputLines() {
        return inputReader.readInputLines(day);
    }

    public Iterator<String> getInputStream() {
        return readInputLines().stream().iterator();
    }

}
